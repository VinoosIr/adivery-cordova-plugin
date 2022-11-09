package miladesign.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adivery.sdk.Adivery;
import com.adivery.sdk.AdiveryAdListener;
import com.adivery.sdk.AdiveryBannerAdView;
import com.adivery.sdk.AdiveryListener;
import com.adivery.sdk.AdiveryNativeCallback;
import com.adivery.sdk.BannerSize;
import com.adivery.sdk.NativeAd;
import com.adivery.sdk.networks.adivery.AdiveryNativeAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import java.util.logging.Logger;

@SuppressLint("RtlHardcoded")
public class AdiveryPlugin extends CordovaPlugin {
    private static final String TAG = "AdiveryPlugin";
    private static Activity mActivity = null;
    public CordovaInterface cordova = null;
    private FrameLayout bannerLayout;
    private Application _app;
    private AdiveryNativeAd nativeAd;

    public static final int TOP_LEFT = 0;
    public static final int TOP_CENTER = 1;
    public static final int TOP_RIGHT = 2;
    public static final int LEFT = 3;
    public static final int CENTER = 4;
    public static final int RIGHT = 5;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM_CENTER = 7;
    public static final int BOTTOM_RIGHT = 8;

    @Override
    public void initialize(CordovaInterface initCordova, CordovaWebView webView) {
        Log.i(TAG, "initialize");
        cordova = initCordova;
        mActivity = cordova.getActivity();
        _app = cordova.getActivity().getApplication();
        super.initialize(cordova, webView);
    }


    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext CallbackContext) throws JSONException {
        if (action.equals("initialize")) {
            String appId = args.getString(0);
            init(appId);
            return true;
        }
        if (action.equals("setLoggingEnabled")){
            boolean logEnabled = args.getBoolean(0);
            Adivery.setLoggingEnabled(logEnabled);
            return true;
        }
        if (action.equals("createBanner")) {
            String zoneId = args.getString(0);
            int position = args.getInt(1);
            int size = args.getInt(2);
            BannerSize bannerType = BannerSize.SMART_BANNER;
            switch (size) {
                case 1:
                    bannerType = BannerSize.BANNER;
                    break;
                case 2:
                    bannerType = BannerSize.LARGE_BANNER;
                    break;
                case 3:
                    bannerType = BannerSize.MEDIUM_RECTANGLE;
                    break;
            }
            createBanner(zoneId, position, bannerType);
            return true;
        }
        if (action.equals("createBannerAtXY")) {
            String zoneId = args.getString(0);
            int x = args.getInt(1);
            int y = args.getInt(2);
            int size = args.getInt(3);
            BannerSize bannerType = BannerSize.SMART_BANNER;
            switch (size) {
                case 1:
                    bannerType = BannerSize.BANNER;
                    break;
                case 2:
                    bannerType = BannerSize.LARGE_BANNER;
                    break;
                case 3:
                    bannerType = BannerSize.MEDIUM_RECTANGLE;
                    break;
            }
            createBannerAtXY(zoneId, x, y, bannerType);
            return true;
        }
        if (action.equals("removeBanner")) {
            removeBanner();
            return true;
        }
        if (action.equals("showBanner")) {
            showBanner();
            return true;
        }
        if (action.equals("hideBanner")) {
            hideBanner();
            return true;
        }
        if (action.equals("prepareInterstitialAd")) {
            String zoneId = args.getString(0);
            prepareInterstitialAd(zoneId);
            return true;
        }
        if (action.equals("prepareRewardedAd")) {
            String zoneId = args.getString(0);
            prepareRewardedAd(zoneId);
            return true;
        }
        if (action.equals("prepareAppOpenAd")) {
            String zoneId = args.getString(0);
            prepareAppOpenAd(zoneId);
            return true;
        }
        if (action.equals("showAppOpenAd")) {
            String zoneId = args.getString(0);
            showAppOpenAd(zoneId);
            return true;
        }
        if (action.equals("requestNativeAd")) {
            String zoneId = args.getString(0);
            prepareNativeAd(zoneId);
            return true;
        }
        if (action.equals("recordNativeAdImpression")){
            recordNativeAdImpression();
            return true;
        }
        if (action.equals("recordNativeAdClick")){
            recordNativeAdClick();
            return true;
        }
        if (action.equals("showAd")) {
            String zoneId = args.getString(0);
            showAd(zoneId);
            return true;
        }
        return false;
    }

    private void recordNativeAdImpression() {
        if (this.nativeAd != null){
            this.nativeAd.recordImpression();
        }
    }

    private void recordNativeAdClick(){
        if (this.nativeAd != null){
            this.nativeAd.recordClick();
        }
    }

    private void init(String appId) {
        Adivery.configure(_app, appId);
        Adivery.addGlobalListener(new AdiveryListener() {
            @Override
            public void onAppOpenAdClicked(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onAppOpenAdClicked", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAppOpenAdClosed(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onAppOpenAdClosed", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAppOpenAdLoaded(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onAppOpenAdLoaded", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAppOpenAdShown(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onAppOpenAdShown", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onInterstitialAdClicked(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onInterstitialAdClicked", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onInterstitialAdClosed(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onInterstitialAdClosed", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onInterstitialAdLoaded(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onInterstitialAdLoaded", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onInterstitialAdShown(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onInterstitialAdShown", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRewardedAdClicked(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onRewardedAdClicked", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRewardedAdClosed(String placementId, boolean isRewarded) {
                try {
                    String json = new JSONObject().put("placement", placementId)
                    .put("isRewarded", isRewarded).toString();
                    fireEvent("adivery", "onRewardedAdClosed", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRewardedAdLoaded(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onRewardedAdLoaded", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRewardedAdShown(String placementId) {
                try {
                    String json = new JSONObject().put("placement", placementId).toString();
                    fireEvent("adivery", "onRewardedAdShown", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void log(String placementId, String message) {
                try {
                    String json = new JSONObject().put("placement", placementId)
                            .put("message", message).toString();
                    fireEvent("adivery", "onError", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createBanner(final String zoneId, final int position, final BannerSize bannerSize) {

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bannerLayout != null) {
                    _removeBanner();
                }
                bannerLayout = null;
                bannerLayout = new FrameLayout(mActivity);
                FrameLayout.LayoutParams params = createBannerLayoutParams(position);
                bannerLayout.setLayoutParams(params);
                mActivity.getWindow().addContentView(bannerLayout, params);
                AdiveryBannerAdView adView = new AdiveryBannerAdView(mActivity);
                adView.setBannerSize(bannerSize);
                adView.loadAd(zoneId);
                adView.setBannerAdListener(new AdiveryAdListener() {
                    @Override
                    public void onAdClicked() {
                        fireEvent("adivery", "onBannerAdClicked", "");
                    }

                    @Override
                    public void onAdLoaded() {
                        Log.i("Adivery", "Banner loaded");
                        fireEvent("adivery", "onBannerAdLoaded", "");
                        bannerLayout.addView(adView);
                    }

                    @Override
                    public void onAdShown() {
                        fireEvent("adivery", "onBannerAdShown", "");

                    }
                });
            }
        });
    }

    public FrameLayout.LayoutParams createBannerLayoutParams(final int position) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (position == TOP_LEFT) {
            layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        } else if (position == TOP_CENTER) {
            layoutParams.gravity = Gravity.TOP | Gravity.CENTER;
        } else if (position == TOP_RIGHT) {
            layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;
        } else if (position == LEFT) {
            layoutParams.gravity = (Gravity.CENTER_VERTICAL | Gravity.LEFT);
        } else if (position == CENTER) {
            layoutParams.gravity = Gravity.CENTER;
        } else if (position == RIGHT) {
            layoutParams.gravity = (Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else if (position == BOTTOM_LEFT) {
            layoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        } else if (position == BOTTOM_CENTER) {
            layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        } else if (position == BOTTOM_RIGHT) {
            layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        }
        return layoutParams;
    }

    private void createBannerAtXY(final String zoneId, final int x, final int y, final BannerSize bannerSize) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bannerLayout != null) {
                    _removeBanner();
                }
                bannerLayout = null;
                bannerLayout = new FrameLayout(mActivity);
                FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                fLayoutParams.leftMargin = x;
                fLayoutParams.topMargin = y;
                bannerLayout.setLayoutParams(fLayoutParams);
                mActivity.getWindow().addContentView(bannerLayout, null);
                AdiveryBannerAdView adView = new AdiveryBannerAdView(mActivity);
                adView.setBannerSize(bannerSize);
                adView.loadAd(zoneId);
                adView.setBannerAdListener(new AdiveryAdListener() {
                    @Override
                    public void onAdClicked() {
                        fireEvent("adivery", "onBannerAdClicked", "");
                    }

                    @Override
                    public void onAdLoaded() {
                        fireEvent("adivery", "onBannerAdLoaded", "");
                        bannerLayout.addView(adView);
                    }

                    @Override
                    public void onAdShown() {
                        fireEvent("adivery", "onBannerAdShown", "");

                    }
                });
            }
        });
    }

    private void removeBanner() {
        if (bannerLayout == null)
            return;
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    bannerLayout.removeAllViews();
                }
            });
        }
    }

    private void _removeBanner() {
        if (bannerLayout == null)
            return;
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    bannerLayout.removeAllViews();
                }
            });
        }
    }

    private void showBanner() {
        try {
            if (mActivity != null) {
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        bannerLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void hideBanner() {
        try {
            if (mActivity != null) {
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        bannerLayout.setVisibility(View.INVISIBLE);
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void prepareInterstitialAd(String zoneId) {
        if (zoneId == null || (zoneId.equalsIgnoreCase("null") || zoneId.equalsIgnoreCase(""))) {
            return;
        }

        Adivery.prepareInterstitialAd(mActivity, zoneId);

    }

    private void prepareRewardedAd(String zoneId) {
        if (zoneId == null || (zoneId.equalsIgnoreCase("null") || zoneId.equalsIgnoreCase(""))) {
            return;
        }

        Adivery.prepareRewardedAd(mActivity, zoneId);

    }

    private void prepareAppOpenAd(String zoneId) {
        if (zoneId != null && (zoneId.equalsIgnoreCase("null") || zoneId.equalsIgnoreCase(""))) {
            return;
        }

        Adivery.prepareAppOpenAd(mActivity, zoneId);
    }

    private void prepareNativeAd(String zoneId) {
        if (zoneId != null && (zoneId.equalsIgnoreCase("null") || zoneId.equalsIgnoreCase(""))) {
            return;
        }

        Adivery.requestNativeAd(mActivity, zoneId, new AdiveryNativeCallback() {
            @Override
            public void onAdClicked() {
                fireEvent("adivery", "nativeAdClicked", "");
            }

            @Override
            public void onAdLoadFailed(@NotNull String reason) {
                try {
                    String json = new JSONObject().put("message", reason).toString();
                    fireEvent("adivery", "nativeAdLoadFailed", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAdLoaded(@NotNull NativeAd ad) {
                JSONObject nativeObject = new JSONObject();
                AdiveryNativeAd nativeAd = (AdiveryNativeAd) ad;
                AdiveryPlugin.this.nativeAd = nativeAd;
                try {
                    nativeObject.put("headline", nativeAd.getHeadline());
                    nativeObject.put("description", nativeAd.getDescription());
                    nativeObject.put("advertiser", nativeAd.getAdvertiser());
                    nativeObject.put("callToAction", nativeAd.getCallToAction());
                    nativeObject.put("icon", nativeAd.getIconUrl());
                    nativeObject.put("image", nativeAd.getImageUrl());
                    String json = nativeObject.toString();
                    fireEvent("adivery", "nativeAdLoaded", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                    AdiveryPlugin.this.nativeAd = null;
                }
            }

            @Override
            public void onAdShowFailed(@NotNull String reason) {
                try {
                    String json = new JSONObject().put("message", reason).toString();
                    fireEvent("adivery", "nativeAdShowFailed", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAdShown() {
                fireEvent("adivery", "nativeAdShown", "");
            }
        });
    }

    private void showAd(String zoneId) {
        if (Adivery.isLoaded(zoneId)) {
            Adivery.showAd(zoneId);
        }
    }

    private void showAppOpenAd(String zoneId) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Adivery.isLoaded(zoneId)) {
                    Adivery.showAppOpenAd(mActivity, zoneId);
                }
            }
        });
    }

    public void fireEvent(String obj, String eventName, String jsonData) {
        String js;
        if ("window".equals(obj)) {
            js = "var evt=document.createEvent('UIEvents');evt.initUIEvent('" + eventName + "',true,false,window,0);window.dispatchEvent(evt);";
        } else {
            js = "javascript:cordova.fireDocumentEvent('" + eventName + "'";
            if (jsonData != null) {
                js += "," + jsonData;
            }
            js += ");";
        }
        webView.loadUrl(js);
    }
}