package com.demos.henrique.waldophotos.UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demos.henrique.waldophotos.Listeners.AuthenticationListener;
import com.demos.henrique.waldophotos.Listeners.OnAlbumTitleReceivedListener;
import com.demos.henrique.waldophotos.Listeners.ResultListener;
import com.demos.henrique.waldophotos.Model.Album;
import com.demos.henrique.waldophotos.Model.PhotoRecord;
import com.demos.henrique.waldophotos.Networking.NetworkRequester;
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
public class PhotoFragment extends Fragment implements AuthenticationListener, ResultListener{

    // Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // Customize parameters
    private int mColumnCount = 3;
    private String authCookie;
    private String albumPayload;
    NetworkRequester mNetworkRequester;
    RecyclerView recyclerView;

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

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
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

        mNetworkRequester = NetworkRequester.getInstance();
        mNetworkRequester.authenticateNetworkRequester(this);

        //photoItems = PokemonPhoto.dummyGenerator(200);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;


            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                //recyclerView.setLayoutManager(new StaggeredGridLayoutManager( mColumnCount, StaggeredGridLayoutManager.VERTICAL));
            }


        }
        return view;
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

        mNetworkRequester.getAlbum("xxxxxxxx", this, authCookie);
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

            recyclerView.setAdapter(new MyPhotoRecyclerViewAdapter(mAlbum, mListener, getActivity()));
            ((OnAlbumTitleReceivedListener)mListener).albumTitleUpdate(mAlbum.getName());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPhotoListFragmentInteraction(PhotoRecord item);
    }
}
