package com.pro.vyas.pranav.popularmovies.AsyncTaskUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.pro.vyas.pranav.popularmovies.MainActivity;
import com.pro.vyas.pranav.popularmovies.Models.MainModel;
import com.pro.vyas.pranav.popularmovies.Models.MovieModel;
import com.pro.vyas.pranav.popularmovies.R;
import com.pro.vyas.pranav.popularmovies.RecyclerUtils.MovieAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static com.pro.vyas.pranav.popularmovies.ConstantUtils.Constants.sortByPopularity;
import static com.pro.vyas.pranav.popularmovies.MainActivity.currPage;
import static com.pro.vyas.pranav.popularmovies.MainActivity.ivBackgroundProgress;
import static com.pro.vyas.pranav.popularmovies.MainActivity.ivNoConnection;
import static com.pro.vyas.pranav.popularmovies.MainActivity.loadingIndicatorView;
import static com.pro.vyas.pranav.popularmovies.MainActivity.rvMain;
import static com.pro.vyas.pranav.popularmovies.MainActivity.sortByFinal;
import static com.pro.vyas.pranav.popularmovies.MainActivity.tvData;
import static com.pro.vyas.pranav.popularmovies.MainActivity.tvProgress;

public class LoadMovieAsyncTask extends AsyncTask<String, Void, Void> {

    List<MovieModel> movie = new ArrayList<>();
    Context ct;

    public LoadMovieAsyncTask(Context ctx) {
        this.ct = ctx;
    }

    private static final String TAG = "LoadMovieAsyncTask";
    @Override
    protected Void doInBackground(String... strings) {
        String pageNo = strings[0];
        String KEY_API_KEY = "api_key";
        AndroidNetworking.post(sortByFinal)
                .addQueryParameter(KEY_API_KEY,ct.getResources().getString(R.string.API_KEY_TMDB))
                .addQueryParameter("page",pageNo)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        MainModel model = gson.fromJson(response.toString(),MainModel.class);
                        tvData.setText("+Total Result : "+model.getTotal_results()+
                                "    +Total Pages : "+model.getTotal_pages()+
                                "\n+Current Page : "+currPage+"    +Current Sort : "+ ((sortByFinal == sortByPopularity) ? "Popularity" : "IMDB Rating")
                        );
                        movie = model.getResults();
                        rvMain.setVisibility(View.VISIBLE);
                        attachWithRecyclerView(movie,rvMain,ct);
                        tvProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                        Snackbar sbar = Snackbar.make(MainActivity.tvData,"Network Unavailable",Snackbar.LENGTH_LONG);
                        sbar.show();
                        tvData.setText("No Internet Connection!");
                        loadingIndicatorView.smoothToHide();
                        ivNoConnection.setVisibility(View.VISIBLE);
                        ivBackgroundProgress.setVisibility(View.VISIBLE);
                        tvProgress.setVisibility(View.VISIBLE);
                        rvMain.setVisibility(View.INVISIBLE);
                    }
                });
        return null;
    }

    public void attachWithRecyclerView(List<MovieModel> movieResult, RecyclerView recyclerView, Context context){
        MovieAdapter adapter = new MovieAdapter(context, movieResult);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,context.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT ? 2 : 3);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        loadingIndicatorView.smoothToHide();
        ivBackgroundProgress.setVisibility(View.GONE);
        ivNoConnection.setVisibility(View.GONE);
    }

}


