package com.demos.henrique.waldophotos.UI.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demos.henrique.waldophotos.Listeners.OnAlbumTitleReceivedListener;
import com.demos.henrique.waldophotos.Listeners.OnMyTabSelectedListener;
import com.demos.henrique.waldophotos.Model.PhotoRecord;
import com.demos.henrique.waldophotos.R;
import com.demos.henrique.waldophotos.UI.Adapters.SimplePagerAdapter;
import com.demos.henrique.waldophotos.UI.Fragments.BlankFragment;
import com.demos.henrique.waldophotos.UI.Fragments.PhotoFragment;

import java.util.ArrayList;
import java.util.List;

public class WaldoActivity extends AppCompatActivity implements
        PhotoFragment.OnListFragmentInteractionListener,
        BlankFragment.OnFragmentInteractionListener,
        OnAlbumTitleReceivedListener{

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SimplePagerAdapter simplePagerAdapter;
    private TextView albumTitleTV;
    private TextView albumTitleHashtagTV;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ImageView addUserImgV;

    private List<Fragment> mFrags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waldo);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Toolbar toolbarBottom = (Toolbar) findViewById(R.id.toolbar_bottom);
        //setSupportActionBar(toolbarBottom);

        mFrags = new ArrayList<>();
        initFragments(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(), mFrags);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(simplePagerAdapter);


        //tab init
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        TextView tab1TV = (TextView)getLayoutInflater().inflate(R.layout.single_tab_view, null);
        TextView tab2TV = (TextView)getLayoutInflater().inflate(R.layout.single_tab_view, null);


        tabLayout.setTabTextColors(Color.LTGRAY, Color.DKGRAY);

        tab1TV.setText(R.string.matches_tab_title);
        tab2TV.setText(R.string.all_photos_tab_title);


        tabLayout.addTab(tabLayout.newTab().setCustomView(tab1TV));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab2TV));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new OnMyTabSelectedListener(mViewPager));



        //View toolbarRelLayout = getLayoutInflater().inflate(R.layout.album_title_toolbar, null);
        View toolbarRelLayout = toolbarBottom.findViewById(R.id.toolbar_bottom);

        albumTitleTV = (TextView) toolbarRelLayout.findViewById(R.id.album_title_tv);
        albumTitleHashtagTV = (TextView)toolbarRelLayout.findViewById(R.id.album_title_hashtag);
        addUserImgV = (ImageView)toolbarRelLayout.findViewById(R.id.add_user_icon);
        addUserImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hire me!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }


    private void initFragments(PhotoFragment.OnListFragmentInteractionListener hostActiv)
    {
        for(int i = 0; i<3; i++)
            mFrags.add(generateFragment(i, hostActiv));
    }

    private Fragment generateFragment(int position, PhotoFragment.OnListFragmentInteractionListener hostActiv)
    {
        switch (position) {
            case 0:
                return PhotoFragment.newInstance(3, hostActiv);


            case 1:
                return BlankFragment.newInstance((BlankFragment.OnFragmentInteractionListener)hostActiv);


            /*case 2:
                return PhotoFragment.newInstance(1, hostActiv);*/


            default:
                return PhotoFragment.newInstance(3, hostActiv);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waldo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onPhotoListFragmentInteraction(PhotoRecord item) {

    }




    @Override
    public void onDummyFragmentInteraction() {

    }

    @Override
    public void albumTitleUpdate(String title) {
        albumTitleTV.setText(title);
        albumTitleTV.setTypeface(Typeface.SANS_SERIF);
        albumTitleTV.setTextColor(Color.BLACK);
        albumTitleHashtagTV.setText("#"+title.replace (" ", ""));
        albumTitleHashtagTV.setTextColor(Color.LTGRAY);
    }
}
