package com.projectsexception.ads.admobwrapper;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.google.android.gms.ads.AdRequest;

public class AdRequestWrapper {

    static class AdError {
        int viewId;
        Drawable icon;
        String text;
        Drawable background;
    }

    AdRequest adRequest;
    AdError adError;
    View.OnClickListener onClickListener;

    private AdRequestWrapper(AdRequest adRequest, AdError adError, View.OnClickListener onClickListener) {
        this.adRequest = adRequest;
        this.adError = adError;
        this.onClickListener = onClickListener;
    }

    public static class Builder {

        private AdRequest.Builder adRequestBuilder;
        private AdError adError;
        private View.OnClickListener onClickListener;

        public Builder() {
            this.adRequestBuilder = new AdRequest.Builder();
        }

        public Builder addTestDevice(String deviceId) {
            adRequestBuilder.addTestDevice(deviceId);
            return this;
        }

        public Builder error(Drawable icon, String text, Drawable background) {
            adError = new AdError();
            adError.icon = icon;
            adError.text = text;
            adError.background = background;
            return this;
        }

        public Builder error(int viewId) {
            adError = new AdError();
            adError.viewId = viewId;
            return this;
        }

        public Builder errorClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public AdRequestWrapper build() {
            return new AdRequestWrapper(adRequestBuilder.build(), adError, onClickListener);
        }
    }
}
