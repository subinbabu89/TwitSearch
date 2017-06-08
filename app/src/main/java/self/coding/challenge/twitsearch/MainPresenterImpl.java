package self.coding.challenge.twitsearch;

import android.content.Context;
import android.content.Intent;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import retrofit2.Call;

/**
 * Class implementation of the MainPresenter Interface
 */
class MainPresenterImpl implements MainPresenter, Observer {

    private MainView mainView;

    /**
     * Constructor for the class
     *
     * @param mainView instance of the MainView class
     */
    MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        mainView.setupSearchBarView();
        NetworkObserver.getInstance().addObserver(this);
    }

    @Override
    public void searchTweets(String searchStr) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        SearchService service = twitterApiClient.getSearchService();
        int MAX_RESULTS = 100;
        Call<Search> tweets = service.tweets(searchStr, null, null, null, null, MAX_RESULTS, null, null,
                null, true);
        tweets.enqueue(new Callback<Search>() {
            @Override
            public void success(Result<Search> searchResult) {
                final List<Tweet> tweets = searchResult.data.tweets;
                mainView.clearAPIError();
                mainView.setItems(tweets);
            }

            @Override
            public void failure(TwitterException error) {
                mainView.showAPIError();
            }
        });
    }

    @Override
    public void onResume() {
        if (NetworkUtil.getConnectivityStatus((Context) mainView)) {
            mainView.dismissSnackBar();
        } else {
            mainView.showSnackbar();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Intent intent = (Intent) arg;
        if (intent.getExtras() != null) {
            if (intent.getBooleanExtra(Intent.EXTRA_TEXT, false)) {
                mainView.showSnackbar();
            } else {
                mainView.dismissSnackBar();
            }
        }
    }
}
