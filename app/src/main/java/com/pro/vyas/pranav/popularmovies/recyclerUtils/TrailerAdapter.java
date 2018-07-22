package com.pro.vyas.pranav.popularmovies.recyclerUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.pro.vyas.pranav.popularmovies.R;
import com.pro.vyas.pranav.popularmovies.extraUtils.AlwaysMarqueeTextView;
import com.pro.vyas.pranav.popularmovies.models.VideosModel;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.youtubeBaseUrl;
import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.youtubeThumbnailUrl;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.trailerHolder> {

    private Context context;
    private List<VideosModel> trailerList;

    public TrailerAdapter(Context context) {
        this.context = context;
    }

    public void setTrailerList(List<VideosModel> list) {
        this.trailerList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public trailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_single_adapter, parent, false);
        return new trailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull trailerHolder holder, int position) {
        if (trailerList == null) {
            holder.loadingView.smoothToShow();
            holder.tvTitle.setText(R.string.PLEASE_WAIT);
            holder.ivTrailerThumbnail.setVisibility(View.INVISIBLE);
        } else {
            holder.loadingView.hide();
            holder.ivTrailerThumbnail.setVisibility(View.VISIBLE);
            final String url = youtubeBaseUrl + trailerList.get(position).getKey();
            final String imageUrl = youtubeThumbnailUrl + trailerList.get(position).getKey() + "/0.jpg";
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.loading_new)
                    .error(R.drawable.ic_loading)
                    .into(holder.ivTrailerThumbnail);
            holder.tvTitle.setText(trailerList.get(position).getName());

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(url);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            };

            holder.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_TEXT, url);
                        context.startActivity(Intent.createChooser(intent, "choose one"));
                    } catch(Exception e) {
                        //e.toString();
                        Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.tvTitle.setOnClickListener(onClickListener);
            holder.ivTrailerThumbnail.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 1;
        } else {
            return trailerList.size();
        }
    }

    class trailerHolder extends RecyclerView.ViewHolder {
        ImageView ivTrailerThumbnail;
        AlwaysMarqueeTextView tvTitle;
        AVLoadingIndicatorView loadingView;
        ImageView ivShare;

        trailerHolder(View itemView) {
            super(itemView);
            ivTrailerThumbnail = itemView.findViewById(R.id.recycler_image_trailer);
            tvTitle = itemView.findViewById(R.id.recycler_title_trailer);
            tvTitle.setAlwaysMarquee(true);
            loadingView = itemView.findViewById(R.id.loading_indicator_trailer);
            ivShare = itemView.findViewById(R.id.image_share_reycler_trailer);
        }
    }
}
