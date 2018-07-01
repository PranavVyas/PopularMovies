package com.pro.vyas.pranav.popularmovies.ExtraUtils;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.pro.vyas.pranav.popularmovies.Models.GenreModel;
import com.pro.vyas.pranav.popularmovies.Models.MainGenreModel;
import com.pro.vyas.pranav.popularmovies.R;
import com.robertlevonyan.views.chip.Chip;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pro.vyas.pranav.popularmovies.ConstantUtils.Constants.baseUrlGenre;

public class Converter {

    private static final String TAG = "Converter";

    static List<String> names = new ArrayList<>();
    static List<String> ids = new ArrayList<>();
    static Context ct;
    static String result = "";

    public Converter(Context ct) {
        this.ct = ct;
    }

    public static List<String> Convert(Context context,final String[] genreIds,final TextView tvData) {
        final List<String> Genres = new ArrayList<>();
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(baseUrlGenre)
                .addQueryParameter("api_key", ct.getResources().getString(R.string.API_KEY_TMDB))
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
                            result = result + names.get(index) + ",";
                            Genres.add(names.get(index));
                            tvData.setText(result);
                        }
                        int index = ids.indexOf(genreIds[i]);
                        result = result + names.get(index);
                        Genres.add((names.get(index)));
                        tvData.setText(result);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: Could not Retrive Genres");
                    }
                });
        return Genres;
    }

    public void setGenre(String[] genreIds,TextView tv){
        int i = 0;
        for (i = 0; i < (genreIds.length - 1); i++) {
            Log.d(TAG, "genreIdToName: Checking for Genre ID " + genreIds[i]);
            int index = ids.indexOf(genreIds[i]);
            result = result + names.get(index) + ",";
            }
        int index = ids.indexOf(genreIds[i]);
        result = result + names.get(index);
        Toast.makeText(ct, "Final String is " + result, Toast.LENGTH_SHORT).show();
        tv.setText(result);
    }

    public List<String> convertStringToComponants(String string){
        List<String> resultComponants = new ArrayList<>();
        for(int i = 0; i < string.length(); i++){
            while(string.charAt(i) != ','){
                String str = new String();
                str = str+string.charAt(i);
            }
        }
        return resultComponants;
    }
}
