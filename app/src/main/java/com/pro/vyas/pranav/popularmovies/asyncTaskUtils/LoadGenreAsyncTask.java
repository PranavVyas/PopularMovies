package com.pro.vyas.pranav.popularmovies.asyncTaskUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.error.ANError;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.models.GenreModel;
import com.pro.vyas.pranav.popularmovies.models.MainGenreModel;
import com.pro.vyas.pranav.popularmovies.R;
import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlGenre;

public class LoadGenreAsyncTask extends AsyncTask<Void,Void,List<String>> {
    private static final String TAG = "LoadGenreAsyncTask";
    Context context;
    private List<String> genreIds;
    FlowLayout flowLayout;
    private List<String> names = new ArrayList<>();
    private List<String> ids = new ArrayList<>();
    private List<String> Genres = new ArrayList<>();

    public LoadGenreAsyncTask(Context context, List<String> genreIds, FlowLayout flowLayout){
        this.context = context;
        this.genreIds = genreIds;
        Log.d(TAG, "Iniatlized Now "+Genres.size());
        this.flowLayout = flowLayout;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        Log.d(TAG, "Starting Execuation "+Genres.size());
        AndroidNetworking.initialize(context);
        ANRequest request = AndroidNetworking.get(baseUrlGenre)
                .addQueryParameter("api_key", context.getResources().getString(R.string.API_KEY_TMDB))
                .build();
        ANResponse response = request.executeForObject(MainGenreModel.class);
        if(response.isSuccess()){
            MainGenreModel mainGenre = (MainGenreModel) response.getResult();
            List<GenreModel> requestedGenre = mainGenre.getGenres();
            for (GenreModel genre : requestedGenre){
                names.add(genre.getName());
                ids.add(genre.getId());
            }
            for (int i = 0; i < genreIds.size(); i++){
                int index = ids.indexOf(genreIds.get(i));
                Genres.add(names.get(index));
            }
        }else{
            ANError error = response.getError();
            Toast.makeText(context, "Error is "+error.getErrorDetail(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Error Detail is "+error.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "Convert: Sending Genres Size As "+Genres.size());
        return Genres;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        for (String x: strings){
            Chip chip = new Chip(context);
            chip.setChipText(x);
            chip.changeBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            chip.setStrokeColor(context.getResources().getColor(R.color.colorBlue));
            chip.setStrokeSize(3);
            flowLayout.addView(chip);
        }
    }
}
