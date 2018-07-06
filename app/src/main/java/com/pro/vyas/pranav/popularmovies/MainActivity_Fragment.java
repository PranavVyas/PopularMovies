package com.pro.vyas.pranav.popularmovies;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pro.vyas.pranav.popularmovies.recyclerUtils.MovieAdapterNew;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Fragment extends Fragment{

    private static final String TAG = "MainActivity_Fragment";
    int color;
    public MainActivity_Fragment() {
    }

    @SuppressLint("ValidFragment")
    public MainActivity_Fragment(int color) {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        final RecyclerView rvFragment = view.findViewById(R.id.rv_fragment);
        FrameLayout framBackgroung = view.findViewById(R.id.frag_background);
        framBackgroung.setBackgroundColor(color);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rvFragment.setLayoutManager(linearLayoutManager);
        rvFragment.setHasFixedSize(true);
        List<String> titles = new ArrayList<>();
        titles.add("First");
        titles.add("Second");
        titles.add("Third");titles.add("First");
        titles.add("Second");
        titles.add("Third");titles.add("First");
        titles.add("Second");
        titles.add("Third");titles.add("First");
        titles.add("Second");
        titles.add("Third");titles.add("First");
        titles.add("Second");
        titles.add("Third");titles.add("First");
        titles.add("Second");
        titles.add("Third");titles.add("First");
        titles.add("Second");
        titles.add("Third");titles.add("First");
        titles.add("Second");
        titles.add("Third");
        MovieAdapterNew mAdapter = new MovieAdapterNew(getContext(),titles);
        rvFragment.setAdapter(mAdapter);

        return view;
    }
}

