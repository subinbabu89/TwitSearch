package self.coding.challenge.twitsearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Util class for network connectivity check
 */
class NetworkUtil {

    /**
     * Method used to find out if the device has internet connectivity;
     *
     * @param context calling context
     * @return boolean stating the connectivity status
     */
    static boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
