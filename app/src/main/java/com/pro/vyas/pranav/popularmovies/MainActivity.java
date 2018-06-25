package com.pro.vyas.pranav.popularmovies;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.pro.vyas.pranav.popularmovies.Models.MainModel;
import com.pro.vyas.pranav.popularmovies.Models.MovieModel;
import com.pro.vyas.pranav.popularmovies.RecyclerUtils.MovieAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    public String base_url = "https://api.themoviedb.org/3/discover/movie";

    RecyclerView rvMain;
    WaveSwipeRefreshLayout mLayout;

    List<MovieModel> movie = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

        configureSwipeRefresh();
        rvMain = findViewById(R.id.rv_main);
        fetchDataFromUrl("popularity.desc");
    }

    public void fetchDataFromUrl(String sortBy){
        String KEY_SORT_BY = "sort_by";
        String KEY_API_KEY = "api_key";
        AndroidNetworking.post(base_url)
                .addQueryParameter(KEY_SORT_BY,sortBy)
                .addQueryParameter(KEY_API_KEY,getResources().getString(R.string.API_KEY_TMDB))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        MainModel model = gson.fromJson(response.toString(),MainModel.class);
                        movie = model.getResults();
                        attachWithRecyclerView(movie,rvMain);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                        Toast.makeText(MainActivity.this, "Something Went Wrong Try Agin..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void configureSwipeRefresh(){
        mLayout = findViewById(R.id.main_swipe);
        mLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mLayout.setColorSchemeColors(Color.RED,Color.GREEN);

        mLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Task().execute();
            }

            class Task extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    fetchDataFromUrl("vote_count.desc");
                    return null;
                }
                @Override protected void onPostExecute(Void result) {
                    mLayout.setRefreshing(false);
                    super.onPostExecute(result);
                }
            }
        });
    }

    public void attachWithRecyclerView(List<MovieModel> movieResult,RecyclerView recyclerView){
        MovieAdapter adapter = new MovieAdapter(MainActivity.this, movieResult);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }
}
