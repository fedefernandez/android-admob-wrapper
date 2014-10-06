package com.projectsexception.ads.admobwrapper;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdViewWrapper extends AdListener {

    ViewGroup adLayout;
    AdView adView;
    AdRequestWrapper.AdError adError;
    View.OnClickListener onClickListener;

    private AdViewWrapper(Context context, ViewGroup adLayout, String adUnitId, AdSize adSize) {
        this.adView = new AdView(context);
        this.adView.setAdUnitId(adUnitId);
        this.adView.setAdSize(adSize);
        this.adView.setAdListener(this);
        this.adLayout = adLayout;
        this.adLayout.addView(adView);
    }

    public void loadAd(AdRequestWrapper adRequestWrapper) {
        this.adError = adRequestWrapper.adError;
        this.adView.loadAd(adRequestWrapper.adRequest);
        this.onClickListener = adRequestWrapper.onClickListener;
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        super.onAdFailedToLoad(errorCode);
        this.adLayout.removeAllViews();
        this.adView = null;
        if (adError != null) {
            View adErrorView = prepareView();
            if (onClickListener != null) {
                adErrorView.setOnClickListener(onClickListener);
            }
            adLayout.addView(adErrorView);
        }
    }

    private View prepareView() {
        LayoutInflater inflater = (LayoutInflater) adLayout.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (adError.viewId != 0) {
            return inflater.inflate(adError.viewId, adLayout, false);
        } else {
            TextView textView = (TextView) inflater.inflate(R.layout.view_ad_text_error, adLayout, false);
            textView.setCompoundDrawablesWithIntrinsicBounds(adError.icon, null, null, null);
            textView.setText(adError.text);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackgroundDrawable(adError.background);
            } else {
                textView.setBackground(adError.background);
            }
            return textView;
        }
    }

    public void resume() {
        if (adView != null) {
            adView.resume();
        }
    }

    public void pause() {
        if (adView != null) {
            adView.pause();
        }
    }

    public void destroy() {
        if (adView != null) {
            adView.destroy();
            adView = null;
        }
        if (adLayout != null) {
            this.adLayout.removeAllViews();
            this.adLayout = null;
        }
        this.adError = null;
        this.onClickListener = null;
    }

    public static class Builder {

        private Context context;
        private ViewGroup adLayout;
        private String adUnitId;
        private AdSize adSize;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setAdLayout(ViewGroup adLayout) {
            this.adLayout = adLayout;
            return this;
        }

        public Builder setAdUnitId(String adUnitId) {
            this.adUnitId = adUnitId;
            return this;
        }

        public Builder setAdSize(AdSize adSize) {
            this.adSize = adSize;
            return this;
        }

        public AdViewWrapper build() {
            if (adLayout == null) {
                throw new IllegalStateException("AdLayout not set");
            }
            if (adUnitId == null) {
                throw new IllegalStateException("AdUnitId not set");
            }
            if (adSize == null) {
                throw new IllegalStateException("AdSize not set");
            }
            return new AdViewWrapper(context, adLayout, adUnitId, adSize);
        }

    }

}
