package com.example.usphuong.thenewyorktimes.articles.view;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usphuong.thenewyorktimes.Detail_Activity;
import com.example.usphuong.thenewyorktimes.R;
import com.example.usphuong.thenewyorktimes.adapter.RecyclerViewAdapter;
import com.example.usphuong.thenewyorktimes.articles.presenter.ListArticlePresenter;
import com.example.usphuong.thenewyorktimes.articles.presenter.ListArticlePresenterImpl;
import com.example.usphuong.thenewyorktimes.articles.respository.ArticleRepository;
import com.example.usphuong.thenewyorktimes.articles.respository.ArticleRepositoryImpl;
import com.example.usphuong.thenewyorktimes.fragments.SearchFilterFragment;
import com.example.usphuong.thenewyorktimes.models.Doc;
import com.example.usphuong.thenewyorktimes.models.SearchFilters;
import com.example.usphuong.thenewyorktimes.utils.EndlessRecyclerViewScrollListener;
import com.example.usphuong.thenewyorktimes.utils.ItemClickSupport;
import com.example.usphuong.thenewyorktimes.utils.NetWorkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListArticleActivity extends AppCompatActivity implements ListArticleView, SearchFilterFragment.OnFragmentInteractionListener {
    @BindView(R.id.rvArticles)
    RecyclerView rvArticles;
    @BindView(R.id.swipe_refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;

    ListArticlePresenter presenter;
    RecyclerViewAdapter adapter;

    EndlessRecyclerViewScrollListener scrollListener;

    SearchFilters searchFilters;



    ItemClickSupport.OnItemClickListener articleClickListener = new ItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            Doc article = adapter.getItem(position);
            if (article != null) {
                Intent displayArticleIntent = new Intent(getApplicationContext(), Detail_Activity.class);
                displayArticleIntent.putExtra("url", article.getWebUrl());
                startActivity(displayArticleIntent);
            } else {
                Snackbar.make(v, "Internal error. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NetWorkUtil.setContext(this);

        searchFilters = new SearchFilters();

        // Create repository
        ArticleRepository repository = new ArticleRepositoryImpl();
        presenter = new ListArticlePresenterImpl(this, repository);

        setupView();

    }


    private void setupView() {
        adapter = new RecyclerViewAdapter(this );
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        rvArticles.setLayoutManager(layoutManager);
        presenter.searchArticles(0,searchFilters);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.searchArticles(page,searchFilters);
            }
        };
        rvArticles.addOnScrollListener(scrollListener);

        ItemClickSupport.addTo(rvArticles).setOnItemClickListener(articleClickListener);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.white,
                R.color.colorPrimary,
                android.R.color.white);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.searchArticles(0,searchFilters);
            swipeRefreshLayout.setRefreshing(false);
        });
        rvArticles.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = searchView.findViewById(searchEditId);
        et.setHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                scrollListener.resetState();
                searchFilters.setQuery(s);
                presenter.searchArticles(0,searchFilters);
                searchView.clearFocus();
                return true;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                // SearchView is closing
                scrollListener.resetState();
                searchFilters.resetQuery();
                presenter.searchArticles(0,searchFilters);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                item.expandActionView();
                searchView.requestFocus();
                return true;

            case R.id.action_filter:
                showFilterFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterFragment() {
        FragmentManager fm = getSupportFragmentManager();
        SearchFilterFragment filterDialogFragment = SearchFilterFragment.newInstance(searchFilters);
        filterDialogFragment.show(fm, "fragment_filter");
    }

    @Override
    public void onFinishDialog(SearchFilters filters) {
        // update search filters;
        searchFilters = filters;

        // search articles using updated filters;
        presenter.searchArticles(0,searchFilters);

    }

//////////////////////////////////////////////////////

    @Override
    public void showListArticle(List<Doc> docs) {
        adapter.setData(docs);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "May be have error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorNetwork() {
        Snackbar snackbar = Snackbar.make(rvArticles, "Network Error. Please connect to Internet and try again", Snackbar.LENGTH_INDEFINITE)
                .setAction("Wi-Fi Settings", v -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)));
        snackbar.show();
    }


    }
