package com.pro.vyas.pranav.popularmovies.recyclerUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pro.vyas.pranav.popularmovies.R;

import java.util.List;

public class MovieAdapterNew extends RecyclerView.Adapter<MovieAdapterNew.MOvieHolder>{

    Context ct;
    List<String> titles;
    public MovieAdapterNew(Context ct,List<String> titles){
        this.titles = titles;
        this.ct = ct;
    }


    @NonNull
    @Override
    public MOvieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MOvieHolder mHolder;
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.movie_single_holder_new,parent,false);
        return new MOvieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MOvieHolder holder, int position) {
        holder.tv.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class MOvieHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public MOvieHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView);
        }
    }
}
