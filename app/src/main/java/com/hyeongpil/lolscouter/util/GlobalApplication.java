package com.hyeongpil.lolscouter.util;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hyeongpil.lolscouter.R;

/**
 * Created by Hyeongpil on 2017-01-13.
 */
public class GlobalApplication extends Application {
    final static String TAG = GlobalApplication.class.getSimpleName();
    private static volatile GlobalApplication instance = null;
    private long startTime = 0;
    private int teemoCnt = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

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
    }

    public void setStartTime(long startTime) {
        Log.e(TAG,"startTime :"+startTime);
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getTeemoCnt() {
        return teemoCnt;
    }

    public void setTeemoCnt(int teemoCnt) {
        this.teemoCnt = teemoCnt;
    }
}
