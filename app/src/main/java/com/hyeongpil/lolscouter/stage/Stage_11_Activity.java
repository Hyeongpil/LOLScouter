package com.hyeongpil.lolscouter.stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.hyeongpil.lolscouter.BaseActivity;
import com.hyeongpil.lolscouter.R;
import com.hyeongpil.lolscouter.model.Score;
import com.hyeongpil.lolscouter.util.GlobalApplication;

import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedadhelper.TedAdHelper;
import gun0912.tedadhelper.front.OnFrontAdListener;
import gun0912.tedadhelper.front.TedAdFront;

/**
 * Created by hyeongpil on 2017-01-02.
 */

public class Stage_11_Activity extends BaseActivity {
    final static String TAG = Stage_11_Activity.class.getSimpleName();
    private String tear;
    private double clearTime = 0;
    private boolean isClicked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container.setLayoutResource(R.layout.activity_stage11);
        View containView = container.inflate();
        ButterKnife.bind(this);
        isClicked = false;
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
        addScore(20);
        nextStage();
    }

    @OnClick(R.id.tv_four)
    void fourClick(){
        addScore(25);
        nextStage();
    }

    @OnClick(R.id.tv_five)
    void fiveClick(){
        addScore(30);
        nextStage();
    }

    @OnClick(R.id.iv_teemo11)
    void teemoClick(){
        ImageView iv_teemo = (ImageView)findViewById(R.id.iv_teemo11);
        iv_teemo.setVisibility(View.INVISIBLE);
        GlobalApplication.getInstance().setTeemoCnt(GlobalApplication.getInstance().getTeemoCnt()+1);
        nextStage();
    }

    @OnClick(R.id.iv_back)
    void backClick(){
        startActivity(new Intent(this,Stage_10_Activity.class));
        backClicked();
    }


    private void nextStage(){
        int score = Score.getInstance().getTotalScore();

        if(score >= 310){
            //챌린저
            tear = "challenger";
        }else if(score < 310 && score >= 290){
            //마스터
            tear = "master";
        }else if(score < 290 && score >= 250){
            //다이아
            tear = "diamond";
        }else if (score < 250 && score >= 200){
            //플레
            tear = "platinum";
        }else if(score < 200 && score >= 150){
            //골드
            tear = "gold";
        }else if (score < 150 && score >= 100){
            //실버
            tear = "silver";
        }else if(score < 100){
            //브론즈
            tear = "bronze";
        }
        clearTime = (System.currentTimeMillis() - GlobalApplication.getInstance().getStartTime()) / 1000.0;
        Log.e(TAG,"시간차이 :"+clearTime);
        if(clearTime < 13){
            tear = "ekko"; // 다 찍은경우
        }
        if(GlobalApplication.getInstance().getTeemoCnt() == 2){
            tear = "teemo"; // 티모 버섯 2개 다 찾은경우
        }

        Log.e(TAG,"tear :"+tear);

        showInterstitial();
    }

    private void showInterstitial() {
        if(!isClicked){
            TedAdFront.showFrontAD(this, getString(R.string.facebook_id), getString(R.string.reword_ad_unit_id), TedAdHelper.AD_FACEBOOK, new OnFrontAdListener() {
                @Override
                public void onDismissed(int adType) {
                    tearIntent();
                }
                @Override
                public void onError(String errorMessage) {
                    Log.e(TAG,"ad error : "+errorMessage);
                    FirebaseCrash.report(new Exception(errorMessage));
                    tearIntent();
                }
                @Override
                public void onLoaded(int adType) {}
                @Override
                public void onAdClicked(int adType) {}
                @Override
                public void onFacebookAdCreated(com.facebook.ads.InterstitialAd facebookFrontAD) {}
            });
        }else{
            Toast.makeText(this, "측정중입니다. 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
        }
        isClicked = true;
    }


    private void tearIntent(){
        Intent intent = new Intent(Stage_11_Activity.this,TearActivity.class);
        intent.putExtra("tear",tear);
        startActivity(intent);
        finish();
        nextAnim();
    }

}
