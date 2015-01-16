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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

public class AccessibilityOpenFragment extends Fragment {
    public static String TAG = "ACCESSIBILITYOPENFRAGMENT";
    public static boolean readOutVariableState = false;
    public static Button switchAccess;
    public Intent accessibilityServiceIntent;
    public Switch readSwitch;

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
        readSwitch = (Switch)accessibilityView.findViewById(R.id.readSwitch);


        switchAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessibilityServiceIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(accessibilityServiceIntent);
            }
        });

        readSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //Log.d(TAG,"Read Out Switch is ON");
                    readOutVariableState = true;
                }
                else
                {
                    //Log.d(TAG,"Read Out Switch is OFF");
                    readOutVariableState = false;
                }
            }
        });

        return accessibilityView;
    }



}
