package com.hyeongpil.lolscouter.stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.hyeongpil.lolscouter.BaseActivity;
import com.hyeongpil.lolscouter.R;
import com.hyeongpil.lolscouter.util.GlobalApplication;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hyeongpil on 2017-01-02.
 */

public class Stage_2_Activity extends BaseActivity {
    final static String TAG = Stage_2_Activity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container.setLayoutResource(R.layout.activity_stage2);
        View containView = container.inflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_one)
    void oneClick(){
        addScore(20);
        nextStage();
    }

    @OnClick(R.id.tv_two)
    void twoClick(){
        addScore(0);
        nextStage();
    }

    @OnClick(R.id.tv_three)
    void threeClick(){
        addScore(30);
        nextStage();
    }

    @OnClick(R.id.tv_four)
    void fourClick(){
        addScore(10);
        nextStage();
    }

    @OnClick(R.id.tv_five)
    void fiveClick(){
        addScore(15);
        nextStage();
    }

    @OnClick(R.id.iv_teemo2)
    void teemoClick(){
        ImageView iv_teemo = (ImageView)findViewById(R.id.iv_teemo2);
        iv_teemo.setVisibility(View.INVISIBLE);
        GlobalApplication.getInstance().setTeemoCnt(GlobalApplication.getInstance().getTeemoCnt()+1);
    }

    @OnClick(R.id.iv_back)
    void backClick(){
        startActivity(new Intent(this,Stage_1_Activity.class));
        backClicked();
    }

    private void nextStage(){
        startActivity(new Intent(this,Stage_3_Activity.class));
        finish();
        nextAnim();
    }

}
