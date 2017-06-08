package self.coding.challenge.twitsearch;


import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Interface for the main activity view
 */
interface MainView {

    /**
     * method used to display progressbar
     */
    void showProgress();

    /**
     * method used to dismiss progressbar
     */
    void hideProgress();

    /**
     * method used to set tweets to the adapter
     *
     * @param items List of tweets to set to the adapter
     */
    void setItems(List<Tweet> items);

    /**
     * method used to display snackbar on the screen
     */
    void showSnackbar();

    /**
     * method used to dismiss snackbar from the screen
     */
    void dismissSnackBar();

    /**
     * method used to setup the search bar view
     */
    void setupSearchBarView();

    /**
     * method used to show api error on the screen
     */
    void showAPIError();

    /**
     * method used to dismiss api string
     */
    void clearAPIError();
}