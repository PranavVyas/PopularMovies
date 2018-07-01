package com.pro.vyas.pranav.popularmovies.AsyncTaskUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.Models.GenreModel;
import com.pro.vyas.pranav.popularmovies.Models.MainGenreModel;
import com.pro.vyas.pranav.popularmovies.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pro.vyas.pranav.popularmovies.ConstantUtils.Constants.baseUrlGenre;

public class LoadGenreAsyncTask extends AsyncTask<Void,Void,String> {
    private static final String TAG = "LoadGenreAsyncTask";
    Context context;
    String[] genreIds;
    FlowLayout flowLayout;

    public LoadGenreAsyncTask(Context context, String[] genreIds, FlowLayout flowLayout){
        this.context = context;
        this.genreIds = genreIds;
        this.flowLayout = flowLayout;
    }

    @Override
    protected String doInBackground(Void... voids) {
        final String[] result = {new String()};
        final List<String> names = new ArrayList<>();
        final List<String> ids = new ArrayList<>();
        AndroidNetworking.get(baseUrlGenre)
                .addQueryParameter("api_key", context.getResources().getString(R.string.API_KEY_TMDB))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        MainGenreModel mainGenreList = gson.fromJson(response.toString(), MainGenreModel.class);
                        List<GenreModel> genres = mainGenreList.getGenres();
                        for (int i = 0; i < genres.size(); i++) {
                            Log.d(TAG, "onResponse: ADDING NAME AS " + genres.get(i).getName() + "ID AS " + genres.get(i).getId());
                            names.add(genres.get(i).getName());
                            ids.add(genres.get(i).getId());
                        }
                        int i = 0;
                        for (i = 0; i < (genreIds.length - 1); i++) {
                            Log.d(TAG, "genreIdToName: Checking for Genre ID " + genreIds[i]);
                            int index = ids.indexOf(genreIds[i]);
                            result[0] = result[0] + names.get(index) + ",";
                        }
                        int index = ids.indexOf(genreIds[i]);
                        result[0] = result[0] + names.get(index);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: Could not Retrive Genres");
                    }
                });
        return result[0];
    }

    @Override
    protected void onPostExecute(String s) {
        TextView txtview = new TextView(context);
        txtview.setText(s);
        Log.d(TAG, "onPostExecute: String Returned is "+s);
        super.onPostExecute(s);
    }
}
