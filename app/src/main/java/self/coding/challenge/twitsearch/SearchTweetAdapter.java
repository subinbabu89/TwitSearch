package self.coding.challenge.twitsearch;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;

import java.util.Collection;
import java.util.List;

/**
 * Adapter for the recyclerview in the main activity
 */
class SearchTweetAdapter extends RecyclerView.Adapter<SearchTweetAdapter.ViewHolder> {

    private List<Tweet> tweets;
    private Context context;

    /**
     * Constructor for the adapter
     *
     * @param tweets List of tweets received from the service
     */
    SearchTweetAdapter(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        CardView tweetView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardView.addView(new CompactTweetView(context, tweets.get(position)));
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    /**
     * Viewholder class for the adapter views
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        /**
         * Constructor for the viewholder
         *
         * @param itemView view to be used for the adapterviews
         */
        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
        }
    }

    /**
     * Method used to add new items to the list
     *
     * @param items List of tweets to be added
     */
    void addAll(Collection<Tweet> items) {
        int currentItemCount = tweets.size();
        tweets.addAll(items);
        notifyItemRangeInserted(currentItemCount, items.size());
    }

    /**
     * Method used to clear existing data for the adapter
     */
    void clear() {
        int itemCount = tweets.size();
        tweets.clear();
        notifyItemRangeRemoved(0, itemCount);
    }
}
