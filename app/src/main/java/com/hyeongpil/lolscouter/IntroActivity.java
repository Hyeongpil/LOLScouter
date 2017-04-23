package com.hyeongpil.lolscouter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyeongpil.lolscouter.stage.Stage_0_Activity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {
    final static String TAG = IntroActivity.class.getSimpleName();

    final long	FINSH_INTERVAL_TIME    = 2000;
    long		backPressedTime        = 0;

    @Bind(R.id.tv_intro_start)
    TextView tv_start;
    @Bind(R.id.fl_intro_achieve_container)
    FrameLayout fl_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_intro_start)
    void startClick(){
        startActivity(new Intent(this,Stage_0_Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    public void onBackPressed() {
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
