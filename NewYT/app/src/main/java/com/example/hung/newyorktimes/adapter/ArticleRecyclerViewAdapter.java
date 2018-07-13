package com.example.hung.newyorktimes.adapter;

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

    private ArrayList<Article> mArticlesNYTimes;

    private final int ARTICLE_IMAGE = 0, ARTICLE_TEXTONLY = 1;

    public interface OnArticleRecyclerViewAdapterListener {
        void selectArticle(Article article);
    }


    private OnArticleRecyclerViewAdapterListener mRecyclerViewAdapterListener;

    public ArticleRecyclerViewAdapter(ArrayList<Article> articles, OnArticleRecyclerViewAdapterListener listener) {
        this.mArticlesNYTimes = articles;
        this.mRecyclerViewAdapterListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case ARTICLE_IMAGE:
                View viewPopularMovie = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_thumbnail, parent, false);
                viewHolder = new ArticleImageViewHolder(viewPopularMovie);
                break;
            case ARTICLE_TEXTONLY:
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case ARTICLE_IMAGE:
                ((ArticleImageViewHolder) holder).setupArticleView(mArticlesNYTimes.get(position));
                break;
            case ARTICLE_TEXTONLY:
                ((ArticleTextOnlyViewHolder) holder).setupArticleView(mArticlesNYTimes.get(position));
                break;
            default:
                ((ArticleImageViewHolder) holder).setupArticleView(mArticlesNYTimes.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.mArticlesNYTimes != null ? this.mArticlesNYTimes.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        Article article = mArticlesNYTimes.get(position);
        Log.d("DEBUG", "has image=" + article.hasImages());
        if (article.hasImages()) {
            return ARTICLE_IMAGE;
        } else {
            return ARTICLE_TEXTONLY;
        }
        //return mArticlesNYTimes.get(position).getmArticleType().ordinal();
    }

    public void notifyDataSetChanged(ArrayList<Article> articles) {
        this.mArticlesNYTimes = articles;
        notifyDataSetChanged();
    }


    class ArticleImageViewHolder extends RecyclerView.ViewHolder {

        private Article article;

        ImageView ivImageView;
        TextView tvTitle;


        public ArticleImageViewHolder(View itemView) {
            super(itemView);

            ivImageView= itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);


            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mRecyclerViewAdapterListener != null)
                        mRecyclerViewAdapterListener.selectArticle(article);
                }
            });
        }

        public void setupArticleView(Article article) {
            this.article = article;
            if (article != null) {
                if (this.ivImageView != null) {
                    ivImageView.setImageDrawable(null);
                    String thumbnail = article.getFirstImageFromMultimedia().getUrl();
                    if (!TextUtils.isEmpty(thumbnail)) {
                        //Picasso.with(itemView.getContext()).load(thumbnail).into(ivImage);
                        Glide.with(itemView.getContext())
                                .load(article.getFirstImageFromMultimedia().getUrl())
                                .into(ivImageView);
                    }
                }
                this.tvTitle.setText(article.getHeadline().getTitle());
            }
        }
    }

    class ArticleTextOnlyViewHolder extends RecyclerView.ViewHolder {


        private Article article;

        TextView tvTitle;
        TextView tvDate;
        TextView tvAuthor;

        public ArticleTextOnlyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvAuthor = itemView.findViewById(R.id.tv_author);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerViewAdapterListener != null)
                        mRecyclerViewAdapterListener.selectArticle(article);
                }
            });
        }

        public void setupArticleView(Article article) {
            this.article = article;
            if (article != null) {

                tvTitle.setText(article.getHeadline().getTitle());

                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd yyyy");
                final Calendar c = Calendar.getInstance();


                if (article.getPubDate() != null&&article.getByLine() != null) {
                    Log.d("DEBUG", "article.getPubDate()"+article.getPubDate());
                    Log.d("DEBUG", "format"+dateFormatter.format(article.getPubDate()));
                    c.setTime(article.getPubDate());
                    Date d = new Date(c.get(c.YEAR)-1900, c.get(c.MONTH), c.get(c.DAY_OF_MONTH));
                    String strDate = dateFormatter.format(d);
                    tvDate.setText(strDate);
                    tvAuthor.setText(article.getByLine().getOriginal());}
            }

        }

    }
    public void clearAdapter() {
        if (!mArticlesNYTimes.isEmpty()) {mArticlesNYTimes.clear();}
        notifyDataSetChanged();
    }
}