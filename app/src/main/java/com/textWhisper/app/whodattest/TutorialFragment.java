package com.textWhisper.app.whodattest;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TutorialFragment extends Fragment {
    public static String TAG = "TUTORIALFRAGMENT";
    public static Button audioCheck;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View tutorialView = inflater.inflate(R.layout.fragment_tutorial,container,false);
        return tutorialView;
    }

}
