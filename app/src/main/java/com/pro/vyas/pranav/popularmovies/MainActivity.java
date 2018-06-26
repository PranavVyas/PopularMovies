package com.pro.vyas.pranav.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.pro.vyas.pranav.popularmovies.AsyncTaskUtils.LoadMovieAsyncTask;
import com.pro.vyas.pranav.popularmovies.Models.MainModel;
import com.pro.vyas.pranav.popularmovies.Models.MovieModel;
import com.pro.vyas.pranav.popularmovies.RecyclerUtils.MovieAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static String base_url = "https://api.themoviedb.org/3/discover/movie";
    public static String sortByPopularity = "popularity.desc";
    public static String sortByVotes = "vote_count.desc";
    public static String sortByFinal = sortByPopularity;
    public static int currPage = 1;

    public static RecyclerView rvMain;
    public static TextView tvData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

        tvData = findViewById(R.id.text_details_main);
        rvMain = findViewById(R.id.rv_main);
        currPage = 1;
        fetchDataFromUrl(sortByFinal,"1");
        attachFloatingActionMenu();
    }

    public void fetchDataFromUrl(String sortBy, final String pageNo){

        String[] array = {sortBy,pageNo};
        LoadMovieAsyncTask loadMovie = new LoadMovieAsyncTask(this);
        loadMovie.execute(array);
    }

    public void attachFloatingActionMenu(){
        ImageView mainBtnIcon = new ImageView(this);
        mainBtnIcon.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(mainBtnIcon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton sortBtn = addButton(getResources().getDrawable(R.drawable.ic_sort_by),this,itemBuilder);
        SubActionButton backBtn = addButton(getResources().getDrawable(R.drawable.ic_arrow_back),this,itemBuilder);
        SubActionButton refreshBtn = addButton(getResources().getDrawable(R.drawable.ic_loading),this,itemBuilder);
        SubActionButton nextBtn = addButton(getResources().getDrawable(R.drawable.ic_arrow_right),this,itemBuilder);


        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortOrder();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currPage > 1){
                    currPage--;
                    fetchDataFromUrl(sortByFinal,String.valueOf(currPage));
                }else{
                    Toast.makeText(MainActivity.this, "Already At Page 1", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataFromUrl(sortByFinal, (String.valueOf(currPage)));
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage++;
                fetchDataFromUrl(sortByFinal,String.valueOf(currPage));
            }
        });

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(sortBtn)
                .addSubActionView(backBtn)
                .addSubActionView(refreshBtn)
                .addSubActionView(nextBtn)
                .attachTo(actionButton)
                .build();
    }

    public SubActionButton addButton(Drawable drawable, final Context ct, SubActionButton.Builder builder){
        ImageView btnImage = new ImageView(ct);
        btnImage.setImageDrawable(drawable);
        SubActionButton btn = builder.setContentView(btnImage).build();
        return btn;
    }

    public static void attachWithRecyclerView(List<MovieModel> movieResult,RecyclerView recyclerView,Context context){
        MovieAdapter adapter = new MovieAdapter(context, movieResult);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.sort_by_menu:
                changeSortOrder();
                return true;

            default:
                return false;
        }
    }

    public void changeSortOrder(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("Popularity", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fetchDataFromUrl(sortByPopularity,"1");
                        sortByFinal = sortByPopularity;
                        currPage = 1;
                    }
                })
                .setNegativeButton("Votes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fetchDataFromUrl(sortByVotes,"1");
                        sortByFinal = sortByVotes;
                        currPage = 1;
                    }
                })
                .setTitle("Sort Movies By ")
                .create();
        dialog.setCancelable(true);
        dialog.show();
    }
}
