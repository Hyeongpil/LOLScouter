package com.hyeongpil.lolscouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewStub;
import android.widget.Toast;

import com.hyeongpil.lolscouter.model.Score;


public class BaseActivity extends AppCompatActivity {
    private final String TAG = BaseActivity.class.getSimpleName();
    protected ViewStub container;
    private final long	FINSH_INTERVAL_TIME    = 2000;
    private long		backPressedTime        = 0;
    private Score t_score = Score.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        container = (ViewStub) findViewById(R.id.container);
    }

    protected void addScore(int score){
        t_score.setTotalScore(t_score.getTotalScore()+score);
        t_score.getBeforeScore().push(score);
        Log.e(TAG,"now total :"+t_score.getTotalScore());
    }


    protected void removeScore(int score){
        t_score.setTotalScore(t_score.getTotalScore()-score);
        Log.e(TAG,"now total :"+t_score.getTotalScore());
    }

    protected void nextAnim(){
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    protected void backClicked(){
        if(!t_score.getBeforeScore().isEmpty()){
            removeScore(t_score.getBeforeScore().pop());
        }
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(),"'뒤로'버튼을 한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
