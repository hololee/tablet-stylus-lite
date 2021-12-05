package com.leejh.hanul.stylus.lite;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;

import java.util.Locale;

public class helpActivity extends Activity {


    String language;
    ImageView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Locale locale = getResources().getConfiguration().locale;
        language = locale.getLanguage();
        language.trim();

        a= (ImageView) findViewById(R.id.a);


        if (!language.equals("ko")) {
            a.setImageResource(R.drawable.pad_help_en);
            a.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }

    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }

}
