package com.aafanasiev.moboxapplication.ui.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aafanasiev.moboxapplication.MoboxApplicationApp;
import com.aafanasiev.moboxapplication.databinding.NewsItemBinding;
import com.aafanasiev.moboxapplication.model.Article;
import com.aafanasiev.moboxapplication.ui.activities.DetailActivity;
import com.aafanasiev.moboxapplication.ui.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    List<Article> articleList = new ArrayList<>();
    Context context;

    public NewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(inflater,parent,false);
        return new NewsViewHolder(binding.getRoot());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.binding.setNews(article);

        holder.binding.getRoot().setOnClickListener(view -> {
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("Image", articleList.get(position).getUrlToImage());
            detailIntent.putExtra("Description", articleList.get(position).getDescription());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.binding.imageNews, "news_image");
            context.startActivity(detailIntent, options.toBundle());
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void upDate(List<Article> aList){
        this.articleList = aList;
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        articleList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position,articleList.size());
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder{

        NewsItemBinding binding;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public NewsViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView view, String s){
        Picasso.with(view.getContext()).load(s).into(view);
    }

}
