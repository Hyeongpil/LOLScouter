package com.hyeongpil.lolscouter.stage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

/**
 * Created by Hyeongpil on 2017-01-13.
 */
public class TearActivity extends BaseGameActivity {
    final static String TAG = TearActivity.class.getSimpleName();
    final static int RC_RESOLVE = 9001;
    private final long	FINSH_INTERVAL_TIME    = 2000;
    private long		backPressedTime        = 0;
    private KakaoLink kakaoLink = null;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = null;
    private String tear = "";
    private SharedPreferences pref = null;
    private String kakaoStr = "";
    private String kakaoImgSrc = "";
    private boolean isGoogleConn = false;
    private boolean isReviewSkip = false;

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
                setTearData(ContextCompat.getDrawable(this,R.drawable.bronze), "브론즈", "정말 정확한 측정이었습니다! 게임을 즐기며 하는 당신은 분명 현실의 승리자입니다.");
                kakaoStr = "제 실력측정 결과는 브론즈입니다. 브론즈라고 무시하지 말고 한번 해보세요!";
                kakaoImgSrc = "http://i.imgur.com/475Ukv6.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_bronze));
                break;
            case "silver":
                setTearData(ContextCompat.getDrawable(this,R.drawable.silver), "실버", "내 실력은 플레인데 왜 실버인지 모르겠다구요? 문제를 풀어보며 되돌아보세요!");
                kakaoStr = "제 실력측정 결과는 실버입니다. 정말 정확한 실력측정기입니다!";
                kakaoImgSrc = "http://i.imgur.com/4VdX1gi.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_silver));
                break;
            case "gold":
                setTearData(ContextCompat.getDrawable(this,R.drawable.gold), "골드", "난 분명 항상 금메달인데 팀운이 없었다구요? 다시 한번 측정해보세요!");
                kakaoStr = "제 실력측정 결과는 골드입니다. 항상 금메달을 따고있는데 골드라구요? 한번 측정해보세요!";
                kakaoImgSrc = "http://i.imgur.com/KNbhdmb.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_gold));
                break;
            case "platinum":
                setTearData(ContextCompat.getDrawable(this,R.drawable.platinum), "플래티넘", "브실골을 무시할 수 있는 권한을 획득했습니다! 플래티넘의 자부심이 느껴지시나요?");
                kakaoStr = "제 실력측정 결과는 플래티넘입니다. 플래티넘 정도는 돼야 오버워치 하는거 아닌가요? 한번 측정해보세요!";
                kakaoImgSrc = "http://i.imgur.com/FmHohqA.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_platinum));
                break;
            case "diamond":
                setTearData(ContextCompat.getDrawable(this,R.drawable.diamond), "다이아", "축하합니다 다이아입니다! 마스터로 올라가실 수 있는 소질이 보이는군요!");
                kakaoStr = "제 실력측정 결과는 다이아입니다. 현실과 다른건 팀원때문이었군요!.";
                kakaoImgSrc = "http://i.imgur.com/FHS5O6A.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_diamond));
                break;
            case "master":
                setTearData(ContextCompat.getDrawable(this,R.drawable.master), "마스터", "모두가 인정합니다. '마스터' 당신은 지배자입니다.");
                kakaoStr = "제 실력측정 결과는 마스터입니다. 과연 저보다 잘할 수 있을까요?";
                kakaoImgSrc = "http://i.imgur.com/t1QXel0.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_master));
                break;
            case "grand":
                setTearData(ContextCompat.getDrawable(this,R.drawable.grandmaster), "그랜드마스터", "축하합니다! 오버워치의 '정점'에 올라섰습니다! 정상적인 일상생활을 하고 계신가요..?");
                kakaoStr = "제 실력측정 결과는 그랜드마스터입니다. 축하합니다! 오버워치의 '정점'입니다!";
                kakaoImgSrc = "http://i.imgur.com/kdaVht0.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_grandmaster));
                break;
            case "tracer":
                setTearData(ContextCompat.getDrawable(this,R.drawable.tracer), "시간역행", "설마 다 찍으셨나요?! 시간을 역행해서 다시 풀어보세요!");
                kakaoStr = "다 찍어버렸습니다. 다시 풀어보세요!";
                kakaoImgSrc = "http://i.imgur.com/KIMTsYp.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_tracer));
                break;
            case "sombra":
                setTearData(ContextCompat.getDrawable(this,R.drawable.sombra), "솜브라", "¿Me extrañaste?");
                kakaoStr = "솜브라를 찾았습니다!";
                kakaoImgSrc = "http://i.imgur.com/gkzI7BA.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_sombra));
                break;
            default:
                setTearData(ContextCompat.getDrawable(this,R.drawable.tracer),"시간역행", "죄송합니다 오류가 발생했습니다! 다시 한번 측정해주세요");
                kakaoStr = "죄송합니다 오류가 발생했습니다!";
                kakaoImgSrc = "http://i.imgur.com/KIMTsYp.png";
                if(isGoogleConn)
                    Games.Achievements.unlock(mGoogleApiClient,getString(R.string.achievement_tracer));
        }

        //측정 카운트
        int count = pref.getInt("count");
        Log.e(TAG,"count : "+count);
        if(isGoogleConn){
            if(count == 15){
                Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_challenger),4);
            }else if(count == 14){
                Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_challenger),3);
            }else if(count == 7){
                Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_challenger),2);
            }else if(count == 1){
                Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_challenger),1);
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
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            moveTaskToBack(true);
            //구글 연결 종료
            if(isGoogleConn){
                Log.e(TAG,"구글 로그아웃");
                mGoogleApiClient.disconnect();
            }
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }else {
            //리뷰 남기기
            if(isReviewSkip){
                if(pref.getString("review","false").equals("false")){
                    if(tear.equals("platinum") || tear.equals("diamond") || tear.equals("master") || tear.equals("grandmaster")){
                        showReviewDialog();
                        pref.putString("review","true");
                    }
                }
            }
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(),"'뒤로'버튼을 한번더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }

    //리뷰 남기기
    private void showReviewDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("평점이 마음에 드시나요? 리뷰를 남겨주세요!")
                .setCancelable(false)
                .setPositiveButton("좋아요!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.hyeongpil.overscouter"));
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("싫어요..",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                isReviewSkip = true;
                            }
                        });
        AlertDialog alert = dialog.create();
        alert.show();
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
