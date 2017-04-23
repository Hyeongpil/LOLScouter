package com.hyeongpil.lolscouter.stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hyeongpil.lolscouter.R;
import com.hyeongpil.lolscouter.model.Score;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hyeongpil on 2017-01-02.
 * 소개
 */

public class Stage_0_Activity extends AppCompatActivity {
    final static String TAG = Stage_0_Activity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage0);
        ButterKnife.bind(this);
        Score.getInstance().setTotalScore(0);
    }

    @OnClick(R.id.tv_stage0_start)
    void startClick(){
        startActivity(new Intent(this,Stage_1_Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @OnClick(R.id.ll_stage0_sombra)
    void sombraClick(){
        Intent intent = new Intent(this,TearActivity.class);
        intent.putExtra("tear","sombra");
        startActivity(intent);
        finish();
    }
}
