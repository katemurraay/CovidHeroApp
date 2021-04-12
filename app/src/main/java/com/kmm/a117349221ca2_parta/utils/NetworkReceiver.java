package com.kmm.a117349221ca2_parta.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kmm.a117349221ca2_parta.R;

import static com.kmm.a117349221ca2_parta.utils.IConstants.isInternetConnected;
import static com.kmm.a117349221ca2_parta.utils.IConstants.isNotConnected;
public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (checkInternet(context)) {
            isInternetConnected =true;
        } else{
            isNotConnected = true;
            ShowToast toast = new ShowToast();
            toast.makeImageToast(context, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_SHORT);

        }
    }

    boolean checkInternet(Context context) {
        NetworkService serviceManager = new NetworkService(context);

        return serviceManager.isNetworkAvailable();
    }
}
