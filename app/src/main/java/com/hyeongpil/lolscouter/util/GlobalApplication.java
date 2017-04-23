package com.hyeongpil.lolscouter.util;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hyeongpil.lolscouter.R;

import java.util.GregorianCalendar;


/**
 * Created by Hyeongpil on 2017-01-13.
 */
public class GlobalApplication extends Application {
    final static String TAG = GlobalApplication.class.getSimpleName();
    private static volatile GlobalApplication instance = null;
    private InterstitialAd mInterstitialAd;
    public GoogleApiClient mGoogleApiClient = null;
    private long startTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setAd();
    }

    private void setAd() {
        MobileAds.initialize(this, this.getResources().getString(R.string.add_mop_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getResources().getString(R.string.reword_ad_unit_id));
        loadAd();
    }


    public void loadAd(){
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder()
                    .setBirthday(new GregorianCalendar(1997, 1, 1).getTime())
                    .setGender(AdRequest.GENDER_MALE)
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    /**
     * singleton 애플리케이션 객체를 얻는다.
     * @return singleton 애플리케이션 객체
     */
    public static GlobalApplication getInstance() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e(TAG,"onTerminate 진입");
        instance = null;
        if(mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public InterstitialAd getmInterstitialAd() {
        return mInterstitialAd;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public void setStartTime(long startTime) {
        Log.e(TAG,"startTime :"+startTime);
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }
}
