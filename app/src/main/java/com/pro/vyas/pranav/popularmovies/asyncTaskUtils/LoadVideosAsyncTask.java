package com.pro.vyas.pranav.popularmovies.asyncTaskUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.pro.vyas.pranav.popularmovies.R;
import com.pro.vyas.pranav.popularmovies.models.DetailMovieModel;
import com.pro.vyas.pranav.popularmovies.models.VideosModel;

import java.util.List;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.videoLoadBaseUrl;

public class LoadVideosAsyncTask extends AsyncTask<Void, Void, DetailMovieModel> {
    private static final String TAG = "LoadVideosAsyncTask";

    private Context ct;
    private String movieId;
    private DetailMovieModel movieForVideos;
    private LoadTrailerAsyncTaskCallback mCallback;

    public LoadVideosAsyncTask(Context ct, LoadTrailerAsyncTaskCallback callback) {
        this.ct = ct;
        this.mCallback = callback;
    }

    public void loadMovieId(String movieId) {
        this.movieId = movieId;
    }

    @Override
    protected DetailMovieModel doInBackground(Void... Void) {
        String KEY_API_KEY = "api_key";
        String url = videoLoadBaseUrl + movieId + "/videos";

        ANRequest requestMovie = AndroidNetworking.get(url)
                .addQueryParameter(KEY_API_KEY, ct.getResources().getString(R.string.API_KEY_TMDB))
                .build();
        Log.d(TAG, "doInBackground: URL IS :" + url);
        ANResponse response = requestMovie.executeForObject(DetailMovieModel.class);
        if (response.isSuccess()) {
            movieForVideos = (DetailMovieModel) response.getResult();
        } else {
            //Toast.makeText(ct.getApplicationContext(), "Did not Connect", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "doInBackground: Did not connect");
        }
        List<VideosModel> videos = movieForVideos.getResults();
        for (int i = 0; i < videos.size(); i++) {
            Log.d(TAG, "onPostExecute: Movie Trailer is " + videos.get(i).getKey());
        }
        return movieForVideos;
    }

    @Override
    protected void onPostExecute(DetailMovieModel detailMovieModel) {
        mCallback.onComplete(detailMovieModel);
    }

    public interface LoadTrailerAsyncTaskCallback {
        void onComplete(DetailMovieModel detailMovieModel);
    }

}
