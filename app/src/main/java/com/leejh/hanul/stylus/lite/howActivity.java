package com.leejh.hanul.stylus.lite;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.Locale;

public class howActivity extends Activity {

    String language;

    ScrollView view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how);

        Locale locale = getResources().getConfiguration().locale;
        language = locale.getLanguage();
        language.trim();


        view1 = (ScrollView) findViewById(R.id.first_layout);

        ImageButton button1 = (ImageButton) findViewById(R.id.button01);


        if (!language.equals("ko")) {
            button1.setImageResource(R.drawable.selector2_en);
            button1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_bottom_to_top_alpha);
        view1.startAnimation(anim);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backAnimAction();
            }
        });


    }


    @Override
    public void onBackPressed() {

        backAnimAction();


    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
        overridePendingTransition(0, 0);
    }


    protected void backAnimAction() {

        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
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
}
