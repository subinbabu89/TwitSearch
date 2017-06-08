package self.coding.challenge.twitsearch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast listener to listen to the network connectivity changes
 */
public class NetworkBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Boolean networkStatus = NetworkUtil.getConnectivityStatus(context);
        intent.putExtra(Intent.EXTRA_TEXT, networkStatus);
        NetworkObserver.getInstance().updateValue(intent);
    }
}
