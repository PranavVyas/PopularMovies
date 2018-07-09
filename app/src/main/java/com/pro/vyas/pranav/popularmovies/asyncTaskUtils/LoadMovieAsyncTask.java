package com.pro.vyas.pranav.popularmovies.asyncTaskUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.error.ANError;
import com.pro.vyas.pranav.popularmovies.MainActivity;
import com.pro.vyas.pranav.popularmovies.R;
import com.pro.vyas.pranav.popularmovies.models.MainModel;
import com.wang.avi.AVLoadingIndicatorView;

import static com.pro.vyas.pranav.popularmovies.MainActivity.sortByFinal;

public class LoadMovieAsyncTask extends AsyncTask<String, Void, MainModel> {

    private MainModel model;
    private Context ct;
    AVLoadingIndicatorView loadingIndicatorView;

    public LoadMovieAsyncTask(Context ctx,AVLoadingIndicatorView loadingIndicatorView) {
        this.ct = ctx;
        this.loadingIndicatorView = loadingIndicatorView;
    }

    private static final String TAG = "LoadMovieAsyncTask";
    @Override
    protected MainModel doInBackground(String... strings) {
        loadingIndicatorView.smoothToShow();
        String pageNo = strings[0];
        String KEY_API_KEY = "api_key";
        ANRequest requestMovie = AndroidNetworking.post(sortByFinal)
                .addQueryParameter(KEY_API_KEY,ct.getResources().getString(R.string.API_KEY_TMDB))
                .addQueryParameter("page",pageNo)
                .build();

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        ANResponse response = requestMovie.executeForObject(MainModel.class);
        if(response.isSuccess()){
            model = (MainModel) response.getResult();
            loadingIndicatorView.hide();
        }else{
            ANError error = response.getError();
            Log.d(TAG, "doInBackground: Error Occured \nTitle : "+error.getErrorDetail()+"\nDetail: "+error.getMessage());
        }
        //loadingIndicatorView.hide();
        return model;
    }

    @Override
    protected void onPostExecute(MainModel mainModel) {
        //super.onPostExecute(mainModel);
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        //loadingIndicatorView.show();
    }
}


