package com.example.hung.newyorktimes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hung.newyorktimes.R;
import com.example.hung.newyorktimes.models.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Article> ArticleNYT ;
    private final int ARTICLES_IMAGE = 0, ARTICLES_THUMBNAIL = 1;

    public interface OnArticleRecyclerViewAdapterListener{
        void selectArticle(Article article);
    }
    private OnArticleRecyclerViewAdapterListener onArticleRecyclerViewAdapterListener;

    public ArticleRecyclerViewAdapter(ArrayList<Article> articles, OnArticleRecyclerViewAdapterListener listener){
        this.onArticleRecyclerViewAdapterListener = listener;
        this.ArticleNYT = articles;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case ARTICLES_IMAGE:
                View viewPopularMovie = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_thumbnail, parent, false);
                viewHolder = new ArticleImageViewHolder(viewPopularMovie);
                break;
            case ARTICLES_THUMBNAIL:
                View viewLessPopularMovie = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_headline, parent, false);
                viewHolder = new ArticleTextOnlyViewHolder(viewLessPopularMovie);
                break;
            default:
                View viewLessPopularMovieDefault = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_thumbnail, parent, false);
                viewHolder = new ArticleImageViewHolder(viewLessPopularMovieDefault);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ARTICLES_IMAGE:
                ((ArticleImageViewHolder) holder).setupArticleView(ArticleNYT.get(position));
                break;
            case ARTICLES_THUMBNAIL:
                ((ArticleTextOnlyViewHolder) holder).setupArticleView(ArticleNYT.get(position));
                break;
            default:
                ((ArticleImageViewHolder)holder).setupArticleView(ArticleNYT.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.ArticleNYT != null ? this.ArticleNYT.size() : 0 ;
    }
    @Override
    public  int getItemViewType(int position){
        Article article = ArticleNYT.get(position);
        Log.d("DEBUG","has image" + article.hasImages());
        if(article.hasImages()){
            return ARTICLES_IMAGE;
        }else {
            return ARTICLES_THUMBNAIL;
        }

    }
    public void notifyDataSetChanged(ArrayList<Article> articles){
        this.ArticleNYT = articles;
        notifyDataSetChanged();
    }
    class ArticleImageViewHolder extends RecyclerView.ViewHolder{
        private Article article;
        ImageView ivImageView;
        TextView tvTitle;

        public ArticleImageViewHolder(View itemView ){
            super(itemView);
            ivImageView =  itemView.findViewById(R.id.ivImage);
            tvTitle =  itemView.findViewById(R.id.tvTitle);
            this.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onArticleRecyclerViewAdapterListener != null)
                        onArticleRecyclerViewAdapterListener.selectArticle(article);

                }
            });
        }
        public void setupArticleView(Article article){
            this.article = article;
            if(article != null){
                if(this.ivImageView != null){
                    ivImageView.setImageDrawable(null);
                    String thumbnail = article.getFirstImageFromMultimedia().getUrl();
                    if (!TextUtils.isEmpty(thumbnail)){
                        Glide.with(itemView.getContext())
                                .load(article.getFirstImageFromMultimedia().getUrl())
                                .into(ivImageView);
                    }
                }
                this.tvTitle.setText(article.getHeadline().getTitle());

            }
        }
    }
    class ArticleTextOnlyViewHolder extends RecyclerView.ViewHolder{

        private Article article;

        TextView tvtitle;
        TextView tvdate;
        TextView tvauthor;

        public ArticleTextOnlyViewHolder(View itemView){
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tv_title);
            tvdate = itemView.findViewById(R.id.tv_date);
            tvauthor = itemView.findViewById(R.id.tv_author);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onArticleRecyclerViewAdapterListener != null)
                        onArticleRecyclerViewAdapterListener.selectArticle(article);
                }
            });
        }
        public void setupArticleView(Article article){
            this.article = article;
            if(article != null){

                tvtitle.setText(article.getHeadline().getTitle());

                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd yyyy");
                final Calendar c = Calendar.getInstance();

                if (article.getPubDate() != null && article.getByline() != null){
                    Log.d("DEBUG", "article.getPubDate()" + article.getPubDate());
                    Log.d("DEBUG","format"+ dateFormatter.format(article.getPubDate()));
                    c.setTime(article.getPubDate());
                    Date d = new Date(c.get(c.YEAR) -1990, c.get(c.MONTH), c.get(c.DAY_OF_MONTH));
                    String strDate = dateFormatter.format(d);
                    tvdate.setText(strDate);
                    tvauthor.setText(article.getByline().getOriginal());

                }
            }
        }

    }
    public void clearAdapter() {

        if (!ArticleNYT.isEmpty()) {
            ArticleNYT.clear();
        }
        notifyDataSetChanged();
    }
}
