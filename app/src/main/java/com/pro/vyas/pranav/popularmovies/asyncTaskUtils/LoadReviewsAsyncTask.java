package com.pro.vyas.pranav.popularmovies.asyncTaskUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.pro.vyas.pranav.popularmovies.R;
import com.pro.vyas.pranav.popularmovies.models.DetailMovieReviewModel;

import java.util.List;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.videoLoadBaseUrl;

public class LoadReviewsAsyncTask extends AsyncTask<Void,Void,DetailMovieReviewModel> {
    private static final String TAG = "LoadReviewsAsyncTask";

    private LoadReviewsCallBack mCallBack;
    private Context context;
    private String movieId;
    private DetailMovieReviewModel movieReviewsModel;

    public void loadMovieId(String movieId) {
        this.movieId = movieId;
    }

    public LoadReviewsAsyncTask(LoadReviewsCallBack callBack, Context context) {
        this.mCallBack = callBack;
        this.context = context;
    }

    @Override
    protected DetailMovieReviewModel doInBackground(Void... voids) {
        String KEY_API_KEY = "api_key";
        String url = videoLoadBaseUrl + movieId + "/reviews";

        ANRequest requestMovie = AndroidNetworking.get(url)
                .addQueryParameter(KEY_API_KEY, context.getResources().getString(R.string.API_KEY_TMDB))
                .build();
        Log.d(TAG, "doInBackground: URL IS :" + url);
        ANResponse response = requestMovie.executeForObject(DetailMovieReviewModel.class);
        if (response.isSuccess()) {
            movieReviewsModel = (DetailMovieReviewModel) response.getResult();
        } else {
            //Toast.makeText(ct.getApplicationContext(), "Did not Connect", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "doInBackground: Did not connect");
        }
        return movieReviewsModel;
    }

    @Override
    protected void onPostExecute(DetailMovieReviewModel detailMovieReviewModels) {
        mCallBack.onCompleteReviews(detailMovieReviewModels);
    }

    public interface LoadReviewsCallBack{
        void onCompleteReviews(DetailMovieReviewModel reviewsModels);
    }
}
