package com.pro.vyas.pranav.popularmovies.ExtraUtils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.pro.vyas.pranav.popularmovies.DetailActivity;
import com.pro.vyas.pranav.popularmovies.Models.GenreModel;
import com.pro.vyas.pranav.popularmovies.Models.MainGenreModel;
import com.pro.vyas.pranav.popularmovies.R;

import org.json.JSONObject;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    private static final String TAG = "Converter";

    List<String> names = new ArrayList<>();
    List<String> ids = new ArrayList<>();
    Context ct;
    String result = "";

    private static final String baseUrlGenre = "https://api.themoviedb.org/3/genre/movie/list";

    public Converter() {
    }

    public Converter(List<String> names, List<String> ids) {
        this.names = names;
        this.ids = ids;
    }

    //    public void initialize(Context ctx){
//        this.ct = ctx;
//        AndroidNetworking.initialize(ct);
//        AndroidNetworking.get(baseUrlGenre)
//                .addQueryParameter("api_key",ct.getResources().getString(R.string.API_KEY_TMDB))
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Gson gson = new Gson();
//                        MainGenreModel mainGenreList = gson.fromJson(response.toString(), MainGenreModel.class);
//                        List<GenreModel> genres = mainGenreList.getGenres();
//                        for(int i = 0; i < genres.size(); i++){
//                            Log.d(TAG, "onResponse: ADDING NAME AS "+genres.get(i).getName() + "ID AS "+genres.get(i).getId());
//                            names.add(genres.get(i).getName());
//                            ids.add(genres.get(i).getId());
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Log.d(TAG, "onError: Could not Retrive Genres");
//                    }
//                });
//    }
//
//    public String genreIdToName(String[] genreIds){
//        String result = "";
//        int i;
//
//        for (int o = 0; o < names.size(); o++){
//            Log.d(TAG, "genreIdToName: Showing list of names with Ids"+"name : "+names.get(o)+" ID = "+ids.get(o));
//        }
//        for (i = 0 ; i < (genreIds.length - 1) ; i++){
//            Log.d(TAG, "genreIdToName: Checking for Genre ID "+genreIds[i]);
//            if(ids.contains(genreIds[i])){
//                Toast.makeText(ct, "This contains genre ID which is "+genreIds[i], Toast.LENGTH_SHORT).show();
//            }
//            result = result + genreIds[i] + ",";
//        }
//        result = result + genreIds[i];
//        return result;
//    }
    public void Convert(Context ctx, final String[] genreIds, final TextView tv) {
        this.ct = ctx;
        AndroidNetworking.initialize(ct);
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
                        }
                        int index = ids.indexOf(genreIds[i]);
                        result = result + names.get(index);
                        tv.setText(result);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: Could not Retrive Genres");
                    }
                });
    }


    public void Initialize(Context ctx) {
        this.ct = ctx;
        AndroidNetworking.initialize(ct);
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
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: Could not Retrive Genres");
                    }
                });
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
}
