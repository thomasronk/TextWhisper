package com.textWhisper.app.whodattest;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class PortListenerService extends Service {
    String TAG="PORTLISTENERSERVICE";
    public static boolean isHeadSetConnected=false;
    public AudioManager aMgr;
    public notificationListener notificationListenerReceiver;
    public PortListenerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // Log.d(TAG,"onServiceCreate");
        notificationListenerReceiver = new notificationListener();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        aMgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        registerReceiver(notificationListenerReceiver, intentFilter);

        //Log.d(TAG, "Created service");
        //Log.d(TAG,"Checking audio hardware");

        if(aMgr.isSpeakerphoneOn()) {
            // Log.d(TAG, "On Speaker");
        }
        else if(aMgr.isWiredHeadsetOn())
        {
           // Log.d(TAG,"On Earphones");
            isHeadSetConnected=true;
        }
        else
        {
            //Log.d(TAG,"Nothing connected yet");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationListenerReceiver);
    }

    /*@Override
    public void onCreate() {
        super.onCreate();

    }*/

    public class notificationListener extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().toString().equals("android.intent.action.HEADSET_PLUG")) {
                //Log.d(TAG,"Something Happened to the audio port!");

                //0 for unplugged, 1 for plugged.
                int state = intent.getIntExtra("state", -1);

                //Headset type, human readable string
                String name = intent.getStringExtra("name");

                // - 1 if headset has a microphone, 0 otherwise, 1 mean h2w
                int microPhone = intent.getIntExtra("microphone", -1);

                //Log.d(TAG, "State,Name,microphone=" + state + " " + name + " " + microPhone);

                switch (state) {
                    case -1:
                        //Log.d(TAG, "Default Connection State");
                        break;
                    case 0:
                        //Log.d(TAG, "Accessory unplugged");
                        isHeadSetConnected = false;
                        break;
                    case 1:
                        //Log.d(TAG, "Accessory Plugged in");
                        isHeadSetConnected = true;
                        break;
                }
            }

        }
    }
}


