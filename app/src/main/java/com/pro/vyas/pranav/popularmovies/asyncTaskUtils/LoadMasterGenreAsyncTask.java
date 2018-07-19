package com.pro.vyas.pranav.popularmovies.asyncTaskUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.error.ANError;
import com.pro.vyas.pranav.popularmovies.R;
import com.pro.vyas.pranav.popularmovies.models.GenreModel;
import com.pro.vyas.pranav.popularmovies.models.MainGenreModel;

import java.util.List;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlGenre;

public class LoadMasterGenreAsyncTask extends AsyncTask<Void,Void,List<GenreModel>> {
    private List<GenreModel> masterGenre;
    private Context context;
    private MasterGenreCallBack mCallBack;

    public LoadMasterGenreAsyncTask(Context context,MasterGenreCallBack callback){
        this.context = context;
        this.mCallBack = callback;
    }

    @Override
    protected List<GenreModel> doInBackground(Void... voids) {
        AndroidNetworking.initialize(context);
        ANRequest request = AndroidNetworking.get(baseUrlGenre)
                .addQueryParameter("api_key", context.getResources().getString(R.string.API_KEY_TMDB))
                .build();
        ANResponse response = request.executeForObject(MainGenreModel.class);
        if(response.isSuccess()){
            MainGenreModel mainGenre = (MainGenreModel) response.getResult();
            masterGenre = mainGenre.getGenres();
        }else{
            ANError error = response.getError();
            Toast.makeText(context, "Error is "+error.getErrorDetail(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Error Detail is "+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return masterGenre;
    }

    @Override
    protected void onPostExecute(List<GenreModel> genreModels) {
        mCallBack.onCompleteMasterGenre(genreModels);
    }

    public interface MasterGenreCallBack{
        void onCompleteMasterGenre(List<GenreModel> genreModels);
    }
}
