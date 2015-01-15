package com.textWhisper.app.whodattest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ron on 1/3/15.
 */
public class BootUpListener extends BroadcastReceiver {
    private Intent serviceIntent;
    String TAG="BOOTUPLISTENER";
    @Override
    public void onReceive(Context context, Intent intent) {
       // Log.d(TAG,"Received Bootup Intent");
        serviceIntent = new Intent(context.getApplicationContext(),PortListenerService.class);
        context.startService(serviceIntent);
    }
}
