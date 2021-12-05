package com.leejh.hanul.stylus.lite;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class preparationActivity extends Activity {

    EditText ipInput;
    ImageButton button_set, left, right;

    String language;

    String Ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation);

        Locale locale = getResources().getConfiguration().locale;
        language = locale.getLanguage();
        language.trim();

        ipInput = (EditText) findViewById(R.id.ip);

        button_set = (ImageButton) findViewById(R.id.button_setting);
        left = (ImageButton) findViewById(R.id.left);
        right = (ImageButton) findViewById(R.id.right);

        //아이콘 설정.

        if (!language.equals("ko")) {
            left.setImageResource(R.drawable.selector_left_en);
            left.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            right.setImageResource(R.drawable.selector_right_en);
            right.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


        //왼손 이용자 설정.
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ipInput.length() == 0) {
                    //ip가 입력x

                    String string = getResources().getString(R.string.toast1);
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();

                } else {

                    //입력0
                    Ip = ipInput.getText().toString().trim();

                    Intent intent = new Intent(getApplicationContext(), padLeftActivity.class);
                    intent.putExtra("IP", Ip);


                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }


            }
        });

        //오른손 이용자 설정.

        right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (ipInput.length() == 0) {
                    //ip가 입력x

                    String string = getResources().getString(R.string.toast1);
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();

                } else {

                    //입력0
                    Ip = ipInput.getText().toString().trim();

                    Intent intent = new Intent(getApplicationContext(), padRightActivity.class);
                    intent.putExtra("IP", Ip);


                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }


            }
        });

        //setting;

        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(),getString(R.string.lite),Toast.LENGTH_SHORT).show();


            }
        });

        //설정방법.



    }


}
