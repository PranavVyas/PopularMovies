package com.pro.vyas.pranav.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.pro.vyas.pranav.popularmovies.AsyncTaskUtils.LoadMovieAsyncTask;
import com.pro.vyas.pranav.popularmovies.Models.MovieModel;
import com.pro.vyas.pranav.popularmovies.PrefencesUtils.SharedPrefenceUtils;
import com.pro.vyas.pranav.popularmovies.RecyclerUtils.MovieAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import static com.pro.vyas.pranav.popularmovies.ConstantUtils.Constants.*;

public class MainActivity extends AppCompatActivity {

    SharedPreferences mainPrefs;

    private static final String TAG = "MainActivity";
    public static String sortByFinal = sortByPopularity;
    public static int currPage = 1;

    public static RecyclerView rvMain;
    public static TextView tvData;
    public static AVLoadingIndicatorView loadingIndicatorView;
    public static ImageView ivBackgroundProgress;
    public static TextView tvProgress;
    public static ImageView ivNoConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

        mainPrefs = getSharedPreferences("firsttimePrefs",Context.MODE_PRIVATE);

        tvData = findViewById(R.id.text_details_main);
        rvMain = findViewById(R.id.rv_main);
        tvProgress = findViewById(R.id.text_progress);
        ivBackgroundProgress = findViewById(R.id.image_backgroundProgress_main);
        loadingIndicatorView = findViewById(R.id.LoadingIndicator);
        ivNoConnection = findViewById(R.id.image_no_connection);

        ivNoConnection.setVisibility(View.GONE);

        currPage = 1;
        fetchDataFromUrl("1");
        attachFloatingActionMenu();

    }

    public void fetchDataFromUrl(final String pageNo){
        loadingIndicatorView.smoothToShow();
        ivBackgroundProgress.setVisibility(View.VISIBLE);
        tvProgress.setVisibility(View.VISIBLE);
        String[] array = {pageNo};
        LoadMovieAsyncTask loadMovie = new LoadMovieAsyncTask(this);
        loadMovie.execute(array);
    }

    public void attachFloatingActionMenu(){
        ImageView mainBtnIcon = new ImageView(this);
        mainBtnIcon.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(mainBtnIcon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton sortBtn = addButton(getResources().getDrawable(R.drawable.ic_sort_by),this,itemBuilder);
        SubActionButton backBtn = addButton(getResources().getDrawable(R.drawable.ic_arrow_back),this,itemBuilder);
        SubActionButton refreshBtn = addButton(getResources().getDrawable(R.drawable.ic_loading),this,itemBuilder);
        SubActionButton nextBtn = addButton(getResources().getDrawable(R.drawable.ic_arrow_right),this,itemBuilder);
        SubActionButton chooseBtn = addButton(getResources().getDrawable(R.drawable.ic_page_no),this,itemBuilder);

        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortOrder();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currPage > 1){
                    currPage--;
                    fetchDataFromUrl(String.valueOf(currPage));
                }else{
                    Toast.makeText(MainActivity.this, "Already At Page 1", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataFromUrl((String.valueOf(currPage)));
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage++;
                fetchDataFromUrl(String.valueOf(currPage));
            }
        });
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePageNo();
            }
        });


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(MainActivity.this)
                .addSubActionView(sortBtn)
                .addSubActionView(backBtn)
                .addSubActionView(refreshBtn)
                .addSubActionView(nextBtn)
                .addSubActionView(chooseBtn)
                .attachTo(actionButton)
                .build();
        actionButton.showContextMenu();
        final SharedPrefenceUtils sharedPrefenceUtils = new SharedPrefenceUtils(mainPrefs);

        if(sharedPrefenceUtils.isFirstTimeRun()){
            Display display = getWindowManager().getDefaultDisplay();
            Drawable iconNext = this.getResources().getDrawable(R.drawable.ic_arrow_right);
            Rect rect1 = new Rect(0, 0, iconNext.getIntrinsicWidth() * 2, iconNext.getIntrinsicHeight() * 2);
            rect1.offset(display.getWidth() / 2, display.getHeight() / 2);
            TapTargetSequence sequence = new TapTargetSequence(MainActivity.this)
                    .targets(
                            TapTarget.forView(actionButton,INTRO_TITLE_ACTION_BUTTON,INTRO_DETAIL_ACTION_BUTTON),
                            TapTarget.forBounds(rect1,INTRO_TITLE_BACK_BUTTON,INTRO_DETAIL_BACK_BUTTON).icon(this.getResources().getDrawable(R.drawable.ic_arrow_back)).outerCircleColor(R.color.colorAccent),
                            TapTarget.forBounds(rect1,INTRO_TITLE_NEXT_BUTTON,INTRO_DETAIL_NEXT_BUTTON).icon(iconNext).outerCircleColor(R.color.colorYello).textColor(R.color.colorBlack),
                            TapTarget.forBounds(rect1,INTRO_TITLE_REFRESH_BUTTON,INTRO_DETAIL_REFRESH_BUTTON).icon(this.getResources().getDrawable(R.drawable.ic_loading)).outerCircleColor(R.color.colorGreen).textColor(R.color.colorBlack),
                            TapTarget.forBounds(rect1,INTRO_TITLE_SORT_BUTTON,INTRO_DETAIL_SORT_BUTTON).icon(this.getResources().getDrawable(R.drawable.ic_sort_by)).outerCircleColor(R.color.colorSkyBlue).textColor(R.color.colorBlack),
                            TapTarget.forBounds(rect1,INTRO_TITLE_PAGE_NO_BUTTON,INTRO_DETAIL_PAGE_NO_BUTTON).icon(this.getResources().getDrawable(R.drawable.ic_page_no)).outerCircleColor(R.color.colorRed).descriptionTextColor(R.color.colorBlack)
            ).listener(new TapTargetSequence.Listener() {
                @Override
                public void onSequenceFinish() {
                    sharedPrefenceUtils.setFirstTimeRun(false);
                    Toast.makeText(MainActivity.this, "You Just Completed Toturial...\nGive it a Try", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {
                    Toast.makeText(MainActivity.this, "Toturial is not completed...\nIt is Compulsory to complete it firsttime\nIt will show again at next Start", Toast.LENGTH_SHORT).show();
                }
            });
            sequence.start();
        }
    }

    public SubActionButton addButton(Drawable drawable, final Context ct, SubActionButton.Builder builder){
        ImageView btnImage = new ImageView(ct);
        btnImage.setImageDrawable(drawable);
        SubActionButton btn = builder.setContentView(btnImage).build();
        return btn;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.sort_by_menu:
                changeSortOrder();
                return true;

            default:
                return false;
        }
    }

    public void changeSortOrder(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("Popularity", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fetchDataFromUrl("1");
                        sortByFinal = sortByPopularity;
                        currPage = 1;
                    }
                })
                .setNegativeButton("IMDB Rating", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fetchDataFromUrl("1");
                        sortByFinal = sortByImdbRating;
                        currPage = 1;
                    }
                })
                .setTitle("Sort Movies By ")
                .create();
        dialog.setCancelable(true);
        dialog.show();
    }

    public void choosePageNo(){
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View chooseView = li.inflate(R.layout.choose_page_dialog, null);

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(chooseView);
        final EditText userInput = (EditText) chooseView
                .findViewById(R.id.edit_choose_page_dialog);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String result = userInput.getText().toString();
                                if(result.length() != 0 && result != null){
                                    currPage = Integer.valueOf(result);
                                    fetchDataFromUrl(result);
                                }else{
                                    Toast.makeText(MainActivity.this, "Page No is Invalid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
