package self.coding.challenge.twitsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;

import java.util.Collection;
import java.util.List;

/**
 * Created by subin on 6/6/2017.
 */

public class SearchTweetAdapter extends RecyclerView.Adapter<SearchTweetAdapter.ViewHolder> {

    private List<Tweet> tweets;
    private Context context;

    public SearchTweetAdapter(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LinearLayout tweetView = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_tweet_activity, parent, false);
        ViewHolder viewHolder =new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.linearLayout.addView(new CompactTweetView(context,tweets.get(position)));
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView;
        }
    }

    public void addAll(Collection<Tweet> items) {
        int currentItemCount = tweets.size();
        tweets.addAll(items);
        notifyItemRangeInserted(currentItemCount, items.size());
    }

    public void clear() {
        int itemCount = tweets.size();
        tweets.clear();
        notifyItemRangeRemoved(0, itemCount);
    }
}
