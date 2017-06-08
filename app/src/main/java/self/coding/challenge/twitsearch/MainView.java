package self.coding.challenge.twitsearch;


import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

interface MainView {

    void showProgress();

    void hideProgress();

    void setItems(List<Tweet> items);

    void showSnackbar();

    void dismissSnackBar();

    void setupSearchBarView();
}