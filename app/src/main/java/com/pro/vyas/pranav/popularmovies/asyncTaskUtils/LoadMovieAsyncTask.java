package com.pro.vyas.pranav.popularmovies.asyncTaskUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
    private AVLoadingIndicatorView loadingIndicatorView;
    private ImageView bakgProgress;
    private TextView textProgress;
    private LoadMovieAsynCallback mCallBack;
    private boolean flag = true;

    public LoadMovieAsyncTask(Context ctx,LoadMovieAsynCallback mCallback) {
        this.ct = ctx;
        this.mCallBack = mCallback;
    }

    private static final String TAG = "LoadMovieAsyncTask";
    @Override
    protected MainModel doInBackground(String... strings) {
        String pageNo = strings[0];
        String KEY_API_KEY = "api_key";
        ANRequest requestMovie = AndroidNetworking.post(sortByFinal)
                .addQueryParameter(KEY_API_KEY,ct.getResources().getString(R.string.API_KEY_TMDB))
                .addQueryParameter("page",pageNo)
                .build();
        ANResponse response = requestMovie.executeForObject(MainModel.class);
        flag = true;
            if(response.isSuccess()){
            model = (MainModel) response.getResult();
        }else{
            ANError error = response.getError();
            flag = false;
            Log.d(TAG, "doInBackground: Error Occured \nTitle : "+error.getErrorDetail()+"\nDetail: "+error.getMessage());
        }
        return model;
    }

    @Override
    protected void onPostExecute(MainModel mainModel) {
        stopProgress(flag);
        mCallBack.onComplete(model);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showPregress();
    }

    public void setProgressIndicatores(AVLoadingIndicatorView loadingIndicatorView, ImageView bakg, TextView text){
        this.loadingIndicatorView = loadingIndicatorView;
        this.textProgress = text;
        this.bakgProgress = bakg;
    }

    private void showPregress(){
        bakgProgress.setVisibility(View.VISIBLE);
        textProgress.setText("Please Wait...");
        textProgress.setVisibility(View.VISIBLE);
        loadingIndicatorView.smoothToShow();
    }

    private void stopProgress(boolean isSucess){
        if(isSucess){
            bakgProgress.setVisibility(View.GONE);
        }else{
            bakgProgress.setVisibility(View.VISIBLE);
        }
        textProgress.setVisibility(View.GONE);
        loadingIndicatorView.smoothToHide();
    }

    public interface LoadMovieAsynCallback{
        void onComplete(MainModel model);
    }
}


