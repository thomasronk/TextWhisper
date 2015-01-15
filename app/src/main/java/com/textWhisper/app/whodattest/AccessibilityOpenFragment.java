package com.textWhisper.app.whodattest;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AccessibilityOpenFragment extends Fragment {
    public static String TAG = "ACCESSIBILITYOPENFRAGMENT";
    public static Button switchAccess;
    public Intent accessibilityServiceIntent;


    private LayoutInflater mInflater;
    private ViewGroup mContainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d(TAG,"Entered Accessibility");
        mInflater = inflater;
        mContainer = container;

        View accessibilityView = inflater.inflate(R.layout.fragment_accessibility_open,container,false);
        switchAccess = (Button)accessibilityView.findViewById(R.id.switchAccessibility);

        switchAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessibilityServiceIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(accessibilityServiceIntent);
            }
        });

        return accessibilityView;
    }



}
