package com.demos.henrique.waldophotos.UI.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demos.henrique.waldophotos.Listeners.AuthenticationListener;
import com.demos.henrique.waldophotos.Listeners.OnAlbumTitleReceivedListener;
import com.demos.henrique.waldophotos.Listeners.ResultListener;
import com.demos.henrique.waldophotos.Model.Album;
import com.demos.henrique.waldophotos.Model.PhotoRecord;
import com.demos.henrique.waldophotos.Networking.NetworkRequester;
import com.demos.henrique.waldophotos.Networking.NetworkStateCheckerTask;
import com.demos.henrique.waldophotos.Networking.Serialization.MyGsonTools;
import com.demos.henrique.waldophotos.R;
import com.demos.henrique.waldophotos.UI.Adapters.MyPhotoRecyclerViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PhotoFragment extends Fragment implements
        AuthenticationListener,
        ResultListener,
        NetworkStateCheckerTask.NetworkResponseListener{

    // Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // Customize parameters
    private int mColumnCount = 3;
    private String authCookie;
    private String albumPayload;
    NetworkRequester mNetworkRequester;
    RecyclerView recyclerView;
    SwipeRefreshLayout mRefreshLayout;

    public void setmListener(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    private OnListFragmentInteractionListener mListener;

    private Album mAlbum;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PhotoFragment() {
    }


    public static PhotoFragment newInstance(int columnCount, OnListFragmentInteractionListener hostActivity) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);

        fragment.setmListener(hostActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mAlbum = new Album();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_photo_list, container, false);

        RecyclerView view = (RecyclerView) mRefreshLayout.findViewById(R.id.list);


        // Set the adapter
        if (view  != null) {
            Context context = view.getContext();
            recyclerView = view;


            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                //recyclerView.setLayoutManager(new StaggeredGridLayoutManager( mColumnCount, StaggeredGridLayoutManager.VERTICAL));

            }


            recyclerView.setAdapter(new MyPhotoRecyclerViewAdapter(mAlbum, mListener, getActivity()));
            contentRefresh();
            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture.
             */
            mRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            Log.i("", "onRefresh called from SwipeRefreshLayout");

                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            contentRefresh();
                        }
                    }
            );



        }
        return mRefreshLayout;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean isAuthenticated(boolean authResult, String authCookie) {
        this.authCookie = authCookie;

        //query to get a specific album
        String graphQlQuery = "query{album(id:\"YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4\"){id,name,photos{records{id, urls{size_code, url}}}}}";

        mNetworkRequester.getAlbum(graphQlQuery, this, authCookie);
        return authResult;
    }

    @Override
    public void getResult(String data) {



        this.albumPayload = data;

        //gson
        //init field album
        // pass album to the photoadapter
        // onBind -> bind between view.picasso -> photo.url

        MyGsonTools<Album> myGsonTools = new MyGsonTools<>();

        try {
            JSONObject jsonObjectAlbum = new JSONObject(albumPayload);


            JSONObject argTmp  = jsonObjectAlbum.getJSONObject("data");
            String argTmp2  = argTmp.getString("album");

            Album rslt = myGsonTools.getModelFromJSON(argTmp2);


            mAlbum = rslt;
            ((MyPhotoRecyclerViewAdapter)recyclerView.getAdapter()).updateData(mAlbum);

            //------------->>>>>>recyclerView.setAdapter(new MyPhotoRecyclerViewAdapter(mAlbum, mListener, getActivity()));
            ((OnAlbumTitleReceivedListener)mListener).albumTitleUpdate(mAlbum.getName());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mRefreshLayout.isRefreshing())
            mRefreshLayout.setRefreshing(false);
    }



    private void contentRefresh() {

        if(!mRefreshLayout.isRefreshing())
        {
            mRefreshLayout.setRefreshing(true);
        }
        //checks fr network availability and starts data update flow
        new NetworkStateCheckerTask(this).execute();
    }


    @Override
    public void receivedIsOnline(boolean isConnected) {

        if(isConnected) {
            mNetworkRequester = NetworkRequester.getInstance();
            mNetworkRequester.authenticateNetworkRequester(this);

            recyclerView.setBackgroundResource(android.R.drawable.screen_background_light_transparent);
        }
        else {
            Toast.makeText(getActivity(), R.string.no_internet_user_notif, Toast.LENGTH_LONG).show();

            recyclerView.setBackgroundResource(R.drawable.refresh_512);

            mRefreshLayout.setRefreshing(false);
        }
    }


    public interface OnListFragmentInteractionListener {

        void onPhotoListFragmentInteraction(PhotoRecord item);
    }
}
