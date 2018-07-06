package com.pro.vyas.pranav.popularmovies;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pro.vyas.pranav.popularmovies.recyclerUtils.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityNew extends AppCompatActivity {

    private static final String TAG = "MainActivityNew";

    @BindView(R.id.htab_tabs)
    TabLayout tabLayout;
    @BindView(R.id.htab_viewpager)
    ViewPager viewPager;
    CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);

        collapsingToolbarLayout = findViewById(R.id.htab_collapse_toolbar);



        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager());
        adapter.addFrag(new MainActivity_Fragment(
                ContextCompat.getColor(this, R.color.colorPrimary)), "Priary");
        adapter.addFrag(new MainActivity_Fragment(
                ContextCompat.getColor(this, R.color.colorAccent)), "Accent");
        adapter.addFrag(new MainActivity_Fragment(
                ContextCompat.getColor(this, R.color.colorYello)), "Yellow");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(MainActivityNew.this, "Changed Tab to Position " + 0, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//
//        try {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_no_connection);
////            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
////                @SuppressWarnings("ResourceType")
////                @Override
////                public void onGenerated(Palette palette) {
////
////                    int vibrantColor = palette.getVibrantColor(R.color.primary_500);
////                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.primary_700);
////                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
////                    collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
////                }
////            });
//
//        } catch (Exception e) {
//            // if Bitmap fetch fails, fallback to primary colors
//            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
//            collapsingToolbarLayout.setContentScrimColor(
//                    ContextCompat.getColor(this, R.color.colorYello)
//            );
//            collapsingToolbarLayout.setStatusBarScrimColor(
//                    ContextCompat.getColor(this, R.color.colorAccent)
//            );
//        }
    }
}