package com.leejh.hanul.stylus.lite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyCloseAd;
import com.fsn.cauly.CaulyCloseAdListener;

import java.util.Locale;

public class firstActivity extends Activity implements CaulyCloseAdListener {

    private static final String APP_CODE = "RYgvkAHq"; // 광고 요청을 위한 App Code
    CaulyCloseAd mCloseAd ;                         // CloseAd광고 객체


    String language;
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    TextView view1;
    Animation anim;
    // TextView addressView;


    @Override
    protected void onResume() {
        super.onResume();

        if (mCloseAd != null)
            mCloseAd.resume(this); // 필수 호출


        if ((view1 != null) && (anim != null)) {
            view1.setVisibility(View.VISIBLE);
            view1.startAnimation(anim);


        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 앱을 처음 설치하여 실행할 때, 필요한 리소스를 다운받았는지 여부.
            if (mCloseAd.isModuleLoaded())
            {
                mCloseAd.show(this);
            }
            else
            {
                // 광고에 필요한 리소스를 한번만  다운받는데 실패했을 때 앱의 종료팝업 구현
                showDefaultClosePopup();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDefaultClosePopup()
    {
        new AlertDialog.Builder(this).setTitle("").setMessage("종료 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("아니요",null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        //CloseAd 초기화
        CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder(APP_CODE).build();
        mCloseAd = new CaulyCloseAd();

            /*  Optional
            //원하는 버튼의 문구를 설정 할 수 있다.
            mCloseAd.setButtonText("취소", "종료");
            //원하는 텍스트의 문구를 설정 할 수 있다.
            mCloseAd.setDescriptionText("종료하시겠습니까?");
            */
        mCloseAd.setAdInfo(closeAdInfo);
        mCloseAd.setCloseAdListener(this);
        mCloseAd.disableBackKey();
        // CaulyCloseAdListener 등록
        // 종료광고 노출 후 back버튼 사용을 막기 원할 경우 disableBackKey();을 추가한다
        // mCloseAd.disableBackKey();




        Locale locale = getResources().getConfiguration().locale;
        language = locale.getLanguage();
        language.trim();


        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_bottom_to_top_alpha);

        view1 = (TextView) findViewById(R.id.first_2);
        button1 = (ImageButton) findViewById(R.id.button01);
        button2 = (ImageButton) findViewById(R.id.button02);
        button3 = (ImageButton) findViewById(R.id.button03);
        //  addressView = (TextView) findViewById(R.id.address);

        Toast.makeText(getApplicationContext(), getString(R.string.update), Toast.LENGTH_LONG).show();


        view1.startAnimation(anim);

        // 아이콘 설정.
        if (!language.equals("ko")) {
            button1.setImageResource(R.drawable.selector1_en);
            button1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            button2.setImageResource(R.drawable.selector_manual_en);
            button2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            button3.setImageResource(R.drawable.selector_button_t_en);
            button3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        }




     /*   addressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hanulc.wix.com/tablet"));
                startActivity(intent);


            }
        });

        */


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent set = new Intent();
                set.setClassName("com.android.settings", "com.android.settings.TetherSettings");
                startActivity(set);


            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!language.equals("ko")) {
                    // 한글이 아닐경우.

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app.box.com/s/r9jsrlfj8dhsftg939nkg0cqy04trw39"));
                    startActivity(intent);
                    //box.com , wix.com

                } else {
                    //한글일경우..

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app.box.com/s/giy9zg1hzv89jqkfh5ikkvduxjnib6hx"));
                    startActivity(intent);


                }

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.AnimationListener listener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        Intent intent = new Intent(getApplicationContext(), preparationActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };


                Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_top_to_bottom_alpha);
                anim2.setAnimationListener(listener);
                view1.startAnimation(anim2);

                view1.setVisibility(View.INVISIBLE);


            }
        });

    }


    @Override
    public void onReceiveCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {

    }

    @Override
    public void onShowedCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {

    }

    @Override
    public void onFailedToReceiveCloseAd(CaulyCloseAd caulyCloseAd, int i, String s) {

    }

    @Override
    public void onLeftClicked(CaulyCloseAd caulyCloseAd) {

    }

    @Override
    public void onRightClicked(CaulyCloseAd caulyCloseAd) {
        finish();
    }

    @Override
    public void onLeaveCloseAd(CaulyCloseAd caulyCloseAd) {

    }
}
