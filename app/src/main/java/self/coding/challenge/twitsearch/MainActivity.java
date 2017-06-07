package self.coding.challenge.twitsearch;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;

import org.cryse.widget.persistentsearch.DefaultVoiceRecognizerDelegate;
import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.VoiceRecognitionDelegate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1023;
    private PersistentSearchView mSearchView;
    private Toolbar mToolbar;
    private MenuItem mSearchMenuItem;
    private View mSearchTintView;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SearchTweetAdapter mResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchView = (PersistentSearchView) findViewById(R.id.searchview);
        VoiceRecognitionDelegate delegate = new DefaultVoiceRecognizerDelegate(this, VOICE_RECOGNITION_REQUEST_CODE);
        if(delegate.isVoiceRecognitionAvailable()) {
            mSearchView.setVoiceRecognitionDelegate(delegate);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSearchTintView = findViewById(R.id.view_search_tint);
        this.setSupportActionBar(mToolbar);
        setUpSearchView();

//        searchLOLOL();
    }

    private void searchTweets(String searchString) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        final SearchService service = twitterApiClient.getSearchService();
        Call<Search> tweets = service.tweets(searchString, null, null, null, null, 100, null, null,
                null, true);
        tweets.enqueue(new Callback<Search>() {
            @Override
            public void success(Result<Search> searchResult) {
                Log.i("APP","HERE");
                final List<Tweet> tweets = searchResult.data.tweets;
                mResultAdapter.addAll(tweets);
                mRecyclerView.setAdapter(mResultAdapter);
            }

            @Override
            public void failure(TwitterException error) {
                Toast.makeText(MainActivity.this,
                        "API CALL FAILED",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUpSearchView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_search_result);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mResultAdapter = new SearchTweetAdapter(new ArrayList<Tweet>());
        mRecyclerView.setAdapter(mResultAdapter);
        mSearchTintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.cancelEditing();
            }
        });
        mSearchView.setHomeButtonListener(new PersistentSearchView.HomeButtonListener() {

            @Override
            public void onHomeButtonClick() {
                // Hamburger has been clicked
                Toast.makeText(MainActivity.this, "Menu click",
                        Toast.LENGTH_LONG).show();
            }

        });
        mSearchView.setSearchListener(new PersistentSearchView.SearchListener() {

            @Override
            public void onSearchEditOpened() {
                //Use this to tint the screen
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
                if(mSearchView.isEditing()) {
                    mSearchView.cancelEditing();
                    return true;
                }
                return false;
            }

            @Override
            public void onSearchExit() {
                mResultAdapter.clear();
                if (mRecyclerView.getVisibility() == View.VISIBLE) {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSearchTermChanged(String term) {

            }

            @Override
            public void onSearch(String string) {
                searchTweets(string);
//                Toast.makeText(MainActivity.this, string + " Searched", Toast.LENGTH_LONG).show();
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchCleared() {

            }

        });
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
                if(mSearchMenuItem != null) {
                    openSearch();
                    return true;
                } else {
                    return false;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void openSearch() {
        View menuItemView = findViewById(R.id.action_search);
        mSearchView.setStartPositionFromMenuItem(menuItemView);
        mSearchView.openSearch();
    }
}
