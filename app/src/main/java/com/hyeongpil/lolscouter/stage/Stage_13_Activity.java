package com.hyeongpil.lolscouter.stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.hyeongpil.lolscouter.BaseActivity;
import com.hyeongpil.lolscouter.R;
import com.hyeongpil.lolscouter.model.Score;
import com.hyeongpil.lolscouter.util.GlobalApplication;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hyeongpil on 2017-01-02.
 */

public class Stage_13_Activity extends BaseActivity {
    final static String TAG = Stage_13_Activity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private String tear;
    private boolean isAdSkip = false;
    private double clearTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container.setLayoutResource(R.layout.activity_stage13);
        View containView = container.inflate();
        ButterKnife.bind(this);
//        setAd();
    }

    @OnClick(R.id.tv_one)
    void oneClick(){
        addScore(0);
        nextStage();
    }

    @OnClick(R.id.tv_two)
    void twoClick(){
        addScore(10);
        nextStage();
    }

    @OnClick(R.id.tv_three)
    void threeClick(){
        addScore(30);
        nextStage();
    }

    @OnClick(R.id.tv_four)
    void fourClick(){
        addScore(30);
        nextStage();
    }

    @OnClick(R.id.iv_back)
    void backClick(){
        startActivity(new Intent(this,Stage_11_Activity.class));
        backClicked();
    }

    private void nextStage(){
        int score = Score.getInstance().getTotalScore();

        if(score >= 375){
            //그마
            tear = "grand";
        }else if(score < 375 && score >= 355){
            //마스터
            tear = "master";
        }else if(score < 355 && score >= 325){
            //다이아
            tear = "diamond";
        }else if (score < 325 && score >= 275){
            //플레
            tear = "platinum";
        }else if(score < 275 && score >= 200){
            //골드
            tear = "gold";
        }else if (score < 200 && score >= 110){
            //실버
            tear = "silver";
        }else if(score < 110){
            //브론즈
            tear = "bronze";
        }
        clearTime = (System.currentTimeMillis() - GlobalApplication.getInstance().getStartTime()) / 1000.0;
        Log.e(TAG,"시간차이 :"+clearTime);
        if(clearTime < 13){
            tear = "tracer"; // 다 찍은경우
        }

        Log.e(TAG,"tear :"+tear);

        tearIntent();
//        showInterstitial();
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            if(!isAdSkip){
                Toast.makeText(this, "광고가 준비되지 않았습니다. 잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                GlobalApplication.getInstance().loadAd();
                isAdSkip = true;
            }else{ // 광고 인터넷 문제로 준비 안되면 그냥 바로 넘김
                tearIntent();
            }
        }
    }

    private void setAd(){
        mInterstitialAd = GlobalApplication.getInstance().getmInterstitialAd();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                GlobalApplication.getInstance().loadAd();
                tearIntent();
            }
        });
    }

    private void tearIntent(){
        Intent intent = new Intent(Stage_13_Activity.this,TearActivity.class);
        intent.putExtra("tear",tear);
        startActivity(intent);
        finish();
        nextAnim();
    }
}
