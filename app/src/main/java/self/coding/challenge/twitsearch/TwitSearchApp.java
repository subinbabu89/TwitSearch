package self.coding.challenge.twitsearch;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by subin on 6/6/2017.
 */

public class TwitSearchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("dqbVwiK3o0lmQ6SvKI07fxS9Y", "0iXnspMwl3vszIGxpnpfbOZcTbRENHWgJHGiur5M8WzXDfAsrB"))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
