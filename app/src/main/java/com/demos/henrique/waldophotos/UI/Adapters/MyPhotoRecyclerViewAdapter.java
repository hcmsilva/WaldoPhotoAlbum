package com.demos.henrique.waldophotos.UI.Adapters;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demos.henrique.waldophotos.Model.Album;
import com.demos.henrique.waldophotos.Model.PhotoRecord;
import com.demos.henrique.waldophotos.R;
import com.demos.henrique.waldophotos.UI.Fragments.PhotoFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a  and makes a call to the
 *
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPhotoRecyclerViewAdapter extends RecyclerView.Adapter<MyPhotoRecyclerViewAdapter.ViewHolder> {

    private Album mAlbum;
    private List<PhotoRecord> mRecords;
    private final PhotoFragment.OnListFragmentInteractionListener mListener;
    private DisplayMetrics mDMetrics = new DisplayMetrics();
    private Activity hostActivity;

    public MyPhotoRecyclerViewAdapter(Album album, PhotoFragment.OnListFragmentInteractionListener listener, Activity hostAct) {
        mAlbum = album;
        mListener = listener;
        //mRecords = mAlbum.getPhotos();
        mRecords = mAlbum.getPhotos();
        this.hostActivity = hostAct;


    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mRecords.get(position);

        /*holder.mIdView.setText(mAlbum.get(position).id);
        holder.mContentView.setText(mAlbum.get(position).content);*/

        //--------->>>>  LOAD IMAGE WITH PICASSO
        //Picasso.with(((Context) mListener)).load("http://i.imgur.com/DvpvklR.png").into(holder.mImageView);
        //Picasso.with(((Context) mListener)).load(((PhotoRecord)holder.mItem).getLargeImage().getUrl()).resize(350,350).centerInside().into(holder.mImageView);

        Picasso.with(((Context) mListener)).load(((PhotoRecord)holder.mItem).getLargeImage().getUrl()).resize(350,350).centerCrop().into(holder.mImageView);

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onPhotoListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView mImageView;
        public PhotoRecord mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.first_image_view);

            Display mDisplay;
            WindowManager mWM;
            if(mDMetrics == null) {
                //mWM = hostActivity.getWindowManager();
                mWM = (WindowManager) hostActivity
                        .getSystemService(Context.WINDOW_SERVICE);
                mDisplay = mWM.getDefaultDisplay();
                mDisplay.getMetrics(mDMetrics);
            }


            Point p = screenResolution();
            mImageView.setLayoutParams(new RelativeLayout.LayoutParams(p.x/3 -8, p.x/3 -12));
            //mImageView.setLayoutParams(new RelativeLayout.LayoutParams(mDMetrics.widthPixels*48, mDMetrics.widthPixels*48));
            //mImageView.setLayoutParams(new RelativeLayout.LayoutParams(250, 250));

        }

        // //To remove warning about using
        //getRealSize prior to API 17
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        private Point screenResolution() {
            WindowManager windowManager =
                    (WindowManager) hostActivity.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point screenResolution = new Point();

            if (Build.VERSION.SDK_INT < 14)
                throw new RuntimeException("Unsupported Android version.");
            display.getRealSize(screenResolution);

            return screenResolution;
        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }
}
