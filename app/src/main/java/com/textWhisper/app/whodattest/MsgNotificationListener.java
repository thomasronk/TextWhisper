package com.textWhisper.app.whodattest;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Vibrator;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MsgNotificationListener extends AccessibilityService implements TextToSpeech.OnInitListener{


    private final AccessibilityServiceInfo info = new AccessibilityServiceInfo();
    private static final String TAG = "MSGNOTIFICATIONLITENER";
    private Parcelable parcelable;
    private TextToSpeech textToSpeech;
    public static boolean accessibilityFound = false;
    public NotificationCompat.Builder mBuilder;
    int mNotificationID = 001;
    public NotificationManager nManager;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
     //Log.d(TAG,"onAccessibilityEvent"+AccessibilityOpenFragment.readOutVariableState);
        final int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            final String sourcePackageName = (String)event.getPackageName();
            String finalSpeechString = null;
            String[] msgParts;
            String[] txtmsgParts;
            parcelable = event.getParcelableData();

            if (parcelable instanceof Notification ) {
                // Statusbar Notification
                //Notification notification = (Notification) parcelable;
                //Log.e(TAG, "Notification -> notification.tickerText :: " + notification.tickerText);
                List<CharSequence> messages = event.getText();

                //Log.d(TAG,"Message content is "+ messages.get(0));
                if (messages.size() > 0) {
                    String notificationMsg = "" + messages.get(0);
                   // Log.v(TAG, "Captured notification message [" + notificationMsg + "] for source [" + sourcePackageName + "]");
                    msgParts = notificationMsg.split(":");
                    if (sourcePackageName.equals("com.whatsapp"))
                    {
                        //Log.d(TAG,"whatsapp message "+msgParts[0].split(" ")[2]);
                        txtmsgParts = msgParts[0].split(" ");
                        finalSpeechString = "Whatsapp Message from"+txtmsgParts[2];
                    }
                    else if(sourcePackageName.equals("com.google.android.apps.messaging"))
                    {

                        //Log.d(TAG,"messenger message from"+msgParts[0]);
                        finalSpeechString = "Google Messenger Message from"+msgParts[0];
                    }
                    else if(sourcePackageName.equals("com.android.mms"))
                    {
                        //Log.d(TAG,"txtmsg service message from "+ msgParts[0]);
                        finalSpeechString = "SMS Text Message from"+msgParts[0];
                    }
                    else if(sourcePackageName.equals("com.google.android.talk"))
                    {
                       //Log.d(TAG,"Google talk  message from "+msgParts[0]);
                        finalSpeechString = "Google Talk Message from"+msgParts[0];
                    }
                    else if(sourcePackageName.equals("com.google.android.gm"))
                    {
                        //Log.d(TAG,"Gmail message from "+msgParts[0]);
                        finalSpeechString = "Jeemail Message from"+msgParts[0];
                    }
                    else if(sourcePackageName.equals("com.facebook.orca"))
                    {
                        //Log.d(TAG,"Facebook message from "+msgParts[0]);
                    }
                    else if(sourcePackageName.equals("com.viber.voip"))
                    {
                        //Log.d(TAG,"Viber message from "+msgParts[0]);
                        finalSpeechString = "Viber Message from"+msgParts[0];
                    }
                    /*else if(sourcePackageName.equals("com.tencent.mm"))
                    {
                        //Log.d(TAG,"WeChat message from "+msgParts[0]);
                        finalSpeechString = "WeChat Message from"+msgParts[0];
                    }*/
                   // com.tencent.mm
                    if(AccessibilityOpenFragment.readOutVariableState)
                    {
                        textToSpeech.speak(finalSpeechString, TextToSpeech.QUEUE_FLUSH, null);
                       // Log.d(TAG,"Read out everywhere");
                    }
                    else if(!AccessibilityOpenFragment.readOutVariableState)
                    {
                        if (PortListenerService.isHeadSetConnected==true) {
                            //Log.d(TAG,"Read out through headphones");
                            textToSpeech.speak(finalSpeechString, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }

                } else {
                    //Log.e(TAG, "Notification Message is empty. Can not broadcast");
                }
            } else {
                // Something else, e.g. a Toast message
                // Read message and broadcast
                   // Log.e(TAG, "Some other kind of notification");
           }
        } else {
            //Log.v(TAG, "Got un-handled Event");
        }
    }


    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessibilityFound=false;
        //Log.d(TAG,"Destroyed");
        if (textToSpeech!=null) {

            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        nManager.cancel(mNotificationID);
        Toast.makeText(this,R.string.str_tw_stopped,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServiceConnected() {
        //Log.d(TAG,"onServiceConnected");
        mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_stat_tw).setContentTitle("TW Active").setContentText("Text Whisper Service is Running").setOngoing(true);
        nManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(mNotificationID,mBuilder.build());
        Toast.makeText(this,R.string.str_finish_setup,Toast.LENGTH_LONG).show();
        accessibilityFound=true;
        textToSpeech = new TextToSpeech(this, this);
        // Set the type of events that this service wants to listen to.
        //Others won't be passed to this service.
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;


        // If you only want this service to work with specific applications, set their
        // package names here.  Otherwise, when the service is activated, it will listen
        // to events from all applications.
        //info.packageNames = new String[]
        //{"com.appone.totest.accessibility", "com.apptwo.totest.accessibility"};

        // Set the type of feedback your service will provide.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        } else {
            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        }

        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated.  This service *is*
        // application-specific, so the flag isn't necessary.  If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

        // info.flags = AccessibilityServiceInfo.DEFAULT;

        info.notificationTimeout = 100;

        this.setServiceInfo(info);


    }

    @Override
    public void onInit(int status) {
        if(status==TextToSpeech.SUCCESS)
        {
            textToSpeech.setLanguage(Locale.getDefault());

        }
        else
        {
            textToSpeech=null;
            Toast.makeText(this,"Failed to initialize TTS engine.",Toast.LENGTH_SHORT).show();
        }
    }

    public static final class Constants {

        public static final String EXTRA_MESSAGE = "extra_message";
        public static final String EXTRA_PACKAGE = "extra_package";
        public static final String ACTION_CATCH_TOAST = "com.mytest.accessibility.CATCH_TOAST";
        public static final String ACTION_CATCH_NOTIFICATION = "com.mytest.accessibility.CATCH_NOTIFICATION";
    }

    /**
     * Check if Accessibility Service is enabled.
     *
     * @param mContext
     * @return <code>true</code> if Accessibility Service is ON, otherwise <code>false</code>
     */
    public static boolean isAccessibilitySettingsOn(Context mContext) {
       // Log.d(TAG,"isAccessibilitySettingsOn");
        int accessibilityEnabled = 0;
        final String service = "com.mytest.accessibility/com.mytest.accessibility.MyAccessibilityService";


        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
           // Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            //Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    //+ e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            accessibilityFound=true;
            //Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    //Log.v(TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
           // Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
            accessibilityFound=false;
        }

        return accessibilityFound;
    }
}
