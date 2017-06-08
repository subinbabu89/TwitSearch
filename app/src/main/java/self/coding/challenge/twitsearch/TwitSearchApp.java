package self.coding.challenge.twitsearch;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Custom Application class needed to initialize the twitter API for the application
 */
public class TwitSearchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.consumer_key), getResources().getString(R.string.consumer_secret)))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
