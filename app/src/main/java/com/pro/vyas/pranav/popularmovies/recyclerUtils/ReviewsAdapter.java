package com.pro.vyas.pranav.popularmovies.recyclerUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pro.vyas.pranav.popularmovies.R;
import com.pro.vyas.pranav.popularmovies.models.ReviewsModel;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>{

    private Context context;
    private List<ReviewsModel> reviews;
    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_single_holder,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        if(reviews == null){
            holder.tvContent.setText("\n\n\n\nPLease Wait Fetching Details\n\n\n\n");
        }else{
            holder.tvName.setText("-"+reviews.get(position).getAuthor());
            holder.tvContent.setText(reviews.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        if(reviews == null){return 1;}
        else{ return reviews.size();}
    }

    public void setReviews(List<ReviewsModel> reviews){
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewHolder extends RecyclerView.ViewHolder{

        TextView tvContent,tvName;
        ReviewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.text_detail_review);
            tvName = itemView.findViewById(R.id.text_auther_name_review);
        }
    }
}
