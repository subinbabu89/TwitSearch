package self.coding.challenge.twitsearch;

/**
 * Interface for the presenter for the mainactivity view
 */
interface MainPresenter {

    /**
     * method used to start search of tweets with matching text
     *
     * @param searchStr string to be searched
     */
    void searchTweets(String searchStr);

    /**
     * method to be performed during the onresume portion of the mainactivity
     */
    void onResume();

    /**
     * interface for the tweet data downloaded
     */
    interface onTweetDataListener {
        /**
         * assigns the adapter to the respective recyclerview
         *
         * @param searchTweetAdapter SearchTweetAdapter to be set to the recylerview
         */
        void setRecylerAdapter(SearchTweetAdapter searchTweetAdapter);
    }

    /**
     * interface for the searchbar actions
     */
    interface onSearchBarListener {
        /**
         * start search of string at the clicking of the start option
         *
         * @param string string to be searched in tweets
         */
        void onSearchText(String string);
    }
}
