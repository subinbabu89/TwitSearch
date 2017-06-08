package self.coding.challenge.twitsearch;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.models.Tweet;

import org.cryse.widget.persistentsearch.PersistentSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActvity class that contains the view for the application
 *
 */
public class MainActivity extends AppCompatActivity implements MainView, MainPresenter.onTweetDataListener, MainPresenter.onSearchBarListener {
    private PersistentSearchView mSearchView;
    private MenuItem mSearchMenuItem;

    private RecyclerView mRecyclerView;
    private SearchTweetAdapter searchTweetAdapter;
    private View mSearchTintView;

    private MainPresenter presenter;
    private ProgressBar progressBar;
    private Snackbar snackbar;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        textView = (TextView)findViewById(R.id.txtv_message);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_search_result);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchTweetAdapter = new SearchTweetAdapter(new ArrayList<Tweet>());

        mSearchView = (PersistentSearchView) findViewById(R.id.search_view);
        mSearchTintView = findViewById(R.id.view_search_tint);

        presenter = new MainPresenterImpl(this);
    }

    @Override
    public void setupSearchBarView() {
        mSearchTintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.cancelEditing();
            }
        });
        mSearchView.setSearchListener(new PersistentSearchView.SearchListener() {

            @Override
            public void onSearchEditOpened() {
                mSearchTintView.setVisibility(View.VISIBLE);
                mSearchTintView
                        .animate()
                        .alpha(1.0f)
                        .setDuration(300)
                        .setListener(new SimpleAnimationListener())
                        .start();

            }

            @Override
            public void onSearchEditClosed() {
                mSearchTintView
                        .animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new SimpleAnimationListener() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mSearchTintView.setVisibility(View.GONE);
                            }
                        })
                        .start();
            }

            @Override
            public boolean onSearchEditBackPressed() {
                if (mSearchView.isEditing()) {
                    mSearchView.cancelEditing();
                    return true;
                }
                return false;
            }

            @Override
            public void onSearchExit() {
            }

            @Override
            public void onSearchTermChanged(String term) {

            }

            @Override
            public void onSearch(String string) {
                if (NetworkUtil.getConnectivityStatus(MainActivity.this)) {
                    onSearchText(string);
                } else {
                    Toast.makeText(MainActivity.this, R.string.str_internet_unreachable, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSearchCleared() {

            }

        });
    }

    @Override
    public void showAPIError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        textView.setText(R.string.str_api_error_message);
    }

    @Override
    public void clearAPIError(){
        textView.setText(R.string.search_results_placeholder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchview, menu);
        mSearchMenuItem = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                if (mSearchMenuItem != null) {
                    View menuItemView = findViewById(R.id.action_search);
                    mSearchView.setStartPositionFromMenuItem(menuItemView);
                    mSearchView.openSearch();
                    return true;
                } else {
                    return false;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setRecylerAdapter(SearchTweetAdapter searchTweetAdapter) {
        mRecyclerView.setAdapter(searchTweetAdapter);
    }

    @Override
    public void onSearchText(String string) {
        showProgress();
        presenter.searchTweets(string);
    }

    @Override
    public void showProgress() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setItems(List<Tweet> items) {
        searchTweetAdapter.clear();
        searchTweetAdapter.addAll(items);
        setRecylerAdapter(searchTweetAdapter);
        hideProgress();
    }

    @Override
    public void showSnackbar() {
        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(mRecyclerView, R.string.str_internet_unavailable, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getText(android.R.string.ok), null);
            snackbar.show();
        }
    }

    @Override
    public void dismissSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
