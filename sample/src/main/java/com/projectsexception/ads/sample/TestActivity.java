package com.projectsexception.ads.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.projectsexception.ads.admobwrapper.AdRequestWrapper;
import com.projectsexception.ads.admobwrapper.AdViewWrapper;

public class TestActivity extends Activity implements View.OnClickListener {

    AdViewWrapper adViewWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        adViewWrapper = new AdViewWrapper.Builder(this)
                .setAdLayout((ViewGroup) findViewById(R.id.ads))
                .setAdUnitId("xxxxx")
                .setAdSize(AdSize.SMART_BANNER)
                .build();

        adViewWrapper.loadAd(new AdRequestWrapper.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .error(R.layout.ad)
                .errorClickListener(this)
                .build());
    }

    @Override
     public void onPause() {
        adViewWrapper.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        adViewWrapper.resume();
    }

    @Override
    public void onDestroy() {
        adViewWrapper.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Error ad clicked!", Toast.LENGTH_SHORT).show();
    }
}