package com.hyeongpil.lolscouter.stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hyeongpil.lolscouter.BaseActivity;
import com.hyeongpil.lolscouter.R;
import com.hyeongpil.lolscouter.util.GlobalApplication;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hyeongpil on 2017-01-02.
 */

public class Stage_1_Activity extends BaseActivity {
    final static String TAG = Stage_1_Activity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container.setLayoutResource(R.layout.activity_stage1);
        View containView = container.inflate();
        ButterKnife.bind(this);
        GlobalApplication.getInstance().setStartTime(System.currentTimeMillis()); // 시작 시간 측정
    }

    @OnClick(R.id.tv_one)
    void oneClick(){
        addScore(10);
        nextStage();
    }

    @OnClick(R.id.tv_two)
    void twoClick(){
        addScore(20);
        nextStage();
    }

    @OnClick(R.id.tv_three)
    void threeClick(){
        addScore(20);
        nextStage();
    }

    @OnClick(R.id.tv_four)
    void fourClick(){
        addScore(0);
        nextStage();
    }

    @OnClick(R.id.tv_five)
    void fiveClick(){
        addScore(30);
        nextStage();
    }

    @OnClick(R.id.iv_back)
    void backClick(){
        startActivity(new Intent(this,Stage_0_Activity.class));
        backClicked();
    }

    private void nextStage(){
        startActivity(new Intent(this,Stage_2_Activity.class));
        finish();
        nextAnim();
    }

}
