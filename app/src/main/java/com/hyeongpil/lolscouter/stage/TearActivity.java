package com.hyeongpil.lolscouter.stage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.firebase.crash.FirebaseCrash;
import com.hyeongpil.lolscouter.R;
import com.hyeongpil.lolscouter.util.GlobalApplication;
import com.hyeongpil.lolscouter.util.SharedPreferences;
import com.kakao.kakaolink.AppActionBuilder;
import com.kakao.kakaolink.AppActionInfoBuilder;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedadhelper.backpress.OnBackPressListener;
import gun0912.tedadhelper.backpress.TedBackPressDialog;

/**
 * Created by Hyeongpil on 2017-01-13.
 */
public class TearActivity extends BaseGameActivity {
    final static String TAG = TearActivity.class.getSimpleName();
    private KakaoLink kakaoLink = null;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = null;
    private String tear = "";
    private SharedPreferences pref = null;
    private String kakaoStr = "";
    private String kakaoImgSrc = "";
    private boolean isGoogleConn = false;

    private static GoogleApiClient mGoogleApiClient = null;

    @Bind(R.id.iv_tear_tear)
    ImageView iv_tear;
    @Bind(R.id.tv_tear_tear)
    TextView tv_tear;
    @Bind(R.id.tv_tear_description)
    TextView tv_description;
    @Bind(R.id.rl_tear_container)
    RelativeLayout rl_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tear);
        ButterKnife.bind(this);
    }

    //mGoogleApiClient가 성공했을 때
    private void init(){
        getTearData();
        setKakaoLink();
        rl_container.setVisibility(View.VISIBLE);
    }

    private void getTearData() {
        pref = new SharedPreferences(this);
        // 각 티어별 이미지와 설명 세팅하기
        try {
            tear = getIntent().getStringExtra("tear");
        }catch (Exception e){}

        switch (tear){
            case "bronze":
                setTearData(ContextCompat.getDrawable(this, R.drawable.bronze), "브론즈", "정말 정확한 측정이었습니다! 게임을 즐기며 하는 당신은 분명 현실의 승리자입니다.");
                kakaoStr = "제 실력측정 결과는 브론즈입니다. 브론즈라고 무시하지 말고 한번 해보세요!";
                kakaoImgSrc = "http://i.imgur.com/EUm9yvU.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQAQ");
                break;
            case "silver":
                setTearData(ContextCompat.getDrawable(this,R.drawable.silver), "실버", "내 실력은 플레인데 왜 실버인지 모르겠다구요? 문제를 풀어보며 되돌아보세요!");
                kakaoStr = "제 실력측정 결과는 실버입니다. 정말 정확한 실력측정기입니다!";
                kakaoImgSrc = "http://i.imgur.com/dxg2lZr.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQAg");
                break;
            case "gold":
                setTearData(ContextCompat.getDrawable(this,R.drawable.gold), "골드", "난 분명 항상 금메달인데 팀운이 없었다구요? 다시 한번 측정해보세요!");
                kakaoStr = "제 실력측정 결과는 골드입니다. 항상 금메달을 따고있는데 골드라구요? 한번 측정해보세요!";
                kakaoImgSrc = "http://i.imgur.com/46bG2uT.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQAw");
                break;
            case "platinum":
                setTearData(ContextCompat.getDrawable(this,R.drawable.platinum), "플래티넘", "브실골을 무시할 수 있는 권한을 획득했습니다! 플래티넘의 자부심이 느껴지시나요?");
                kakaoStr = "제 실력측정 결과는 플래티넘입니다. 플래티넘 정도는 돼야 오버워치 하는거 아닌가요? 한번 측정해보세요!";
                kakaoImgSrc = "http://i.imgur.com/cuqE1Hf.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQBA");
                break;
            case "diamond":
                setTearData(ContextCompat.getDrawable(this,R.drawable.diamond), "다이아", "축하합니다 다이아입니다! 마스터로 올라가실 수 있는 소질이 보이는군요!");
                kakaoStr = "제 실력측정 결과는 다이아입니다. 현실과 다른건 팀원때문이었군요!.";
                kakaoImgSrc = "http://i.imgur.com/ytP0tYu.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQBQ");
                break;
            case "master":
                setTearData(ContextCompat.getDrawable(this,R.drawable.master), "마스터", "모두가 인정합니다. '마스터' 당신은 지배자입니다.");
                kakaoStr = "제 실력측정 결과는 마스터입니다. 과연 저보다 잘할 수 있을까요?";
                kakaoImgSrc = "http://i.imgur.com/t1QXel0.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQCQ");
                break;
            case "ekko":
                setTearData(ContextCompat.getDrawable(this,R.drawable.ekko), "시공간 붕괴", "설마 다 찍으셨나요?! 돌아가서 다시 풀어보세요!");
                kakaoStr = "실수를 하셨군요! 되돌아가서 다시 풀어보세요.";
                kakaoImgSrc = "http://i.imgur.com/u4SyAhp.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQBw");
                break;
            case "challenger":
                setTearData(ContextCompat.getDrawable(this,R.drawable.challenger), "챌린저", "당신은 챌린저입니다! 프로게이머로 도전하시나요?");
                kakaoStr = "제 실력측정 결과는 챌린저입니다. 리그오브레전드의 정점입니다!";
                kakaoImgSrc = "http://i.imgur.com/jhEAzMw.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQBg");
                break;
            case "teemo":
                setTearData(ContextCompat.getDrawable(this,R.drawable.teemoimg), "티모", "티모 대위의 독버섯을 모두 찾았습니다!! 좋은 눈썰미를 가지셨네요");
                kakaoStr = "티모 대위의 독버섯을 찾아보세요!";
                kakaoImgSrc = "http://i.imgur.com/68mDRos.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQCA");
                break;
            default:
                setTearData(ContextCompat.getDrawable(this,R.drawable.ekko),"시공간 붕괴", "죄송합니다 오류가 발생했습니다! 다시 한번 측정해주세요");
                kakaoStr = "죄송합니다 오류가 발생했습니다.";
                kakaoImgSrc = "http://i.imgur.com/u4SyAhp.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,"CgkI56rslNQCEAIQBw");
        }

        //측정 카운트
        int count = pref.getInt("count");
        Log.e(TAG,"count : "+count);
        if(isGoogleConn){
            if(count == 15){
                Games.Achievements.increment(mGoogleApiClient,"CgkI56rslNQCEAIQCg",4);
            }else if(count == 14){
                Games.Achievements.increment(mGoogleApiClient,"CgkI56rslNQCEAIQCg",3);
            }else if(count == 7){
                Games.Achievements.increment(mGoogleApiClient,"CgkI56rslNQCEAIQCg",2);
            }else if(count == 1){
                Games.Achievements.increment(mGoogleApiClient,"CgkI56rslNQCEAIQCg",1);
            }
        }
        pref.putInt("count",count+1);

    }

    private void setTearData(Drawable img, String tear, String desc){
        iv_tear.setBackground(img);
        tv_tear.setText(tear);
        tv_description.setText(desc);
    }

    private void setKakaoLink() {
        try {
            kakaoLink = KakaoLink.getKakaoLink(GlobalApplication.getInstance());
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ll_tear_kakao_container)
    void kakaoClick(){
        try {
            kakaoTalkLinkMessageBuilder.addImage(kakaoImgSrc,128,128)
                    .addText(kakaoStr)
                    .addAppButton("앱으로 이동",
                            new AppActionBuilder()
                                    .addActionInfo(AppActionInfoBuilder
                                            .createAndroidActionInfoBuilder()
                                            .setMarketParam("referrer=kakaotalklink")
                                            .build())
                                    .build())
                    .build();
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.rl_tear_restart)
    void restartClick(){
        startActivity(new Intent(TearActivity.this, Stage_0_Activity.class));
        finish();
    }

    @OnClick(R.id.rl_tear_achieve_container)
    void achieveClick(){
        if(mGoogleApiClient!= null && mGoogleApiClient.isConnected()){
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 124);
        }else {Toast.makeText(this, "구글 게임 연동이 실패하였습니다 다시 로그인 해 주세요", Toast.LENGTH_SHORT).show();}
    }

    @Override
    public void onBackPressed() {
        TedBackPressDialog.startFacebookDialog(this, getString(R.string.app_name), getString(R.string.facebook_nativeid), new OnBackPressListener() {
            @Override
            public void onReviewClick() {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.hyeongpil.lolscouter"));
                startActivity(intent);
            }

            @Override
            public void onFinish() {
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                finish();
            }

            @Override
            public void onLoaded(int adType) {

            }

            @Override
            public void onAdClicked(int adType) {

            }
        });
    }

    @Override
    public void onSignInFailed() {
        Log.e(TAG,"apiclient 연결 실패");
        try {
            Log.e(TAG, getSignInError().toString());
            FirebaseCrash.report(new Exception(getSignInError().toString()));
        }catch (NullPointerException e){}

        init();
    }

    @Override
    public void onSignInSucceeded() {
        Log.e(TAG,"apiclient 연결 성공");
        mGoogleApiClient = getApiClient();
        isGoogleConn = true;
        init();
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
