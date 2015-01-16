package com.textWhisper.app.whodattest;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

public class OpeningScreen extends ActionBarActivity {
    String TAG = "OPENINGSCREEN";

    Intent serviceIntent;
    ViewPager openingPgr;
    TutorialFragment tF;
    AccessibilityOpenFragment aF;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openingPgr = (ViewPager)findViewById(R.id.viewPager);

        serviceIntent = new Intent(this,PortListenerService.class);
        openingPgr.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        startService(serviceIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Log.d(TAG,"Settings selected");
            startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch(i)
            {
                /*case 0:return TutorialFragment.newInstance();
                case 1:return AccessibilityOpenFragment.newInstance();
                default:return TutorialFragment.newInstance();*/
                case 0: return new TutorialFragment();
                case 1: return new AccessibilityOpenFragment();
                default:return new TutorialFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Log.d(TAG,"Activity called");
    }




}
