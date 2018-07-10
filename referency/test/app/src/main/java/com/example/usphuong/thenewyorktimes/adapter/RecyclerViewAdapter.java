package com.example.usphuong.thenewyorktimes.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.usphuong.thenewyorktimes.R;
import com.example.usphuong.thenewyorktimes.models.Article;
import com.example.usphuong.thenewyorktimes.models.Doc;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ArticleViewHolder> {

    private static final String TAG = "NY: " + RecyclerViewAdapter.class.getName();
    private List<Doc> articles;
    private Context context;;

    public void clearData() {
    }


    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;

        @BindView(R.id.tvSnippet)
        TextView tvSnippet;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



    public RecyclerViewAdapter(Context context) {
        articles =new ArrayList<>();
        this.context = context;
    }


    public void setData(List<Doc> data) {
        articles.clear();
        articles.addAll(data);
        notifyDataSetChanged();
    }



    public void replaceData(List<Doc> newArticles) {
        articles.clear();
        articles.addAll(newArticles);
        notifyDataSetChanged();
    }


    public Doc getItem(int position) {
        return articles.get(position);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_item_article, parent, false);

        // Return a new holder instance
        ArticleViewHolder viewHolder = new ArticleViewHolder(contactView);
        return viewHolder;

       // View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_article, null);
        //return new ArticleViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        Doc article = articles.get(position);

        holder.tvTitle.setText(article.getHeadline().getMain());
        holder.tvSnippet.setText(article.getSnippet());
        holder.ivThumbnail.setImageResource(0);
        String thumbnailUrl = article.getThumbnailUrl();

        if (thumbnailUrl != null) {
            Glide.with(holder.itemView.getContext())
                    .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.loading))
                    .load(thumbnailUrl)
                    .into(holder.ivThumbnail);
        } else {
            Glide.with(holder.itemView.getContext())
                    .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.loading))
                    .load(R.drawable.placeholder)
                    .into(holder.ivThumbnail);
        }
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }
}