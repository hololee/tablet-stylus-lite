package com.leejh.hanul.stylus.lite;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class settingActivity extends Activity {

    String language;

    ImageButton bt1, bt2, bt3;
    ImageButton ctrl1, alt1, shift1, sbar1;
    ImageButton ctrl2, alt2, shift2, sbar2;
 //   ImageButton ctrl3, alt3, shift3, sbar3;
    EditText key1, key2, key3;
    ImageButton key1C, key2C, key3C;
    TextView ke1, ke2, ke3;
    TextView init;
    RelativeLayout p1, p2, p3;
    ImageButton confirm;


    final static String SETTING = "setting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Locale locale = getResources().getConfiguration().locale;
        language = locale.getLanguage();
        language.trim();


        bt1 = (ImageButton) findViewById(R.id.bt_01);
        bt2 = (ImageButton) findViewById(R.id.bt_02);
        bt3 = (ImageButton) findViewById(R.id.bt_03);

        p1 = (RelativeLayout) findViewById(R.id.page1);
        p2 = (RelativeLayout) findViewById(R.id.page2);
        p3 = (RelativeLayout) findViewById(R.id.page3);

        ctrl1 = (ImageButton) findViewById(R.id.button_ctrl1);
        alt1 = (ImageButton) findViewById(R.id.button_alt1);
        shift1 = (ImageButton) findViewById(R.id.button_shift1);
        sbar1 = (ImageButton) findViewById(R.id.button_spacebar1);

        ctrl2 = (ImageButton) findViewById(R.id.button_ctrl2);
        alt2 = (ImageButton) findViewById(R.id.button_alt2);
        shift2 = (ImageButton) findViewById(R.id.button_shift2);
        sbar2 = (ImageButton) findViewById(R.id.button_spacebar2);
/*
        ctrl3 = (ImageButton) findViewById(R.id.button_ctrl3);
        alt3 = (ImageButton) findViewById(R.id.button_alt3);
        shift3 = (ImageButton) findViewById(R.id.button_shift3);
        sbar3 = (ImageButton) findViewById(R.id.button_spacebar3);
*/
        key1 = (EditText) findViewById(R.id.key1);
        key2 = (EditText) findViewById(R.id.key2);
     //   key3 = (EditText) findViewById(R.id.key3);

        key1C = (ImageButton) findViewById(R.id.key1C);
        key2C = (ImageButton) findViewById(R.id.key2C);
    //    key3C = (ImageButton) findViewById(R.id.key3C);

        ke1 = (TextView) findViewById(R.id.ketText1);
        ke2 = (TextView) findViewById(R.id.ketText2);
      //  ke3 = (TextView) findViewById(R.id.ketText3);

        init = (TextView) findViewById(R.id.init);

        confirm = (ImageButton) findViewById(R.id.button01);

        // 아이콘 설정.
        if (!language.equals("ko")) {
            confirm.setImageResource(R.drawable.selector2_en);
            confirm.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }

        //초기 page1 성정
        getPage1Settings();
        getPage1key();


//////////////////////////// help , init 설정////////////////////////////////////////////////////////////////////////


        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                //page1
                editor.putString("Ctrl1", "0");
                editor.putString("Alt1", "0");
                editor.putString("Shift1", "0");
                editor.putString("Spacebar1", "0");
                editor.putString("key1", "0");
                //page2
                editor.putString("Ctrl2", "0");
                editor.putString("Alt2", "0");
                editor.putString("Shift2", "0");
                editor.putString("Spacebar2", "0");
                editor.putString("key2", "0");
                //page3
                editor.putString("Ctrl3", "0");
                editor.putString("Alt3", "0");
                editor.putString("Shift3", "0");
                editor.putString("Spacebar3", "0");
                editor.putString("key3", "0");

                editor.commit();

                finish();
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.string3), Toast.LENGTH_SHORT).show();

            }
        });


////////////////////////////단축 키 지정 set 버튼 설정//////////////////////////////////////////////////////////////

        key1C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(confirmKey(key1.getText().toString().trim()) == 0){

                    Toast.makeText(getApplicationContext(),getResources().getText(R.string.string11),Toast.LENGTH_SHORT).show();

                }


                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("key1",String.valueOf(confirmKey(key1.getText().toString().trim())));
                editor.commit();


                ke1.setText(key1.getText().toString().trim());


            }
        });


        key2C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(confirmKey(key2.getText().toString().trim()) == 0){

                    Toast.makeText(getApplicationContext(),getResources().getText(R.string.string11),Toast.LENGTH_SHORT).show();

                }


                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("key2",String.valueOf(confirmKey(key2.getText().toString().trim())));
                editor.commit();

                ke2.setText(key2.getText().toString().trim());

            }
        });


/*        key3C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(confirmKey(key3.getText().toString().trim()) == 0){

                    Toast.makeText(getApplicationContext(),getResources().getText(R.string.string11),Toast.LENGTH_SHORT).show();

                }


                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("key3", String.valueOf(confirmKey(key3.getText().toString().trim())));
                editor.commit();

                ke3.setText(key3.getText().toString().trim());

            }
        });
*/

///////////////////////////////////각 페이지, 버튼별 설정 즉각 적용/////////////////////////및 이미지 변경///////////////////////////

        ctrl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Ctrl1", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Ctrl1", "1");
                    editor.commit();

                    ctrl1.setImageResource(R.drawable.button_ctrl_pressed);
                    ctrl1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Ctrl1", "0");
                    editor.commit();

                    ctrl1.setImageResource(R.drawable.button_ctrl);
                    ctrl1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }


            }
        });

        alt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Alt1", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Alt1", "1");
                    editor.commit();

                    alt1.setImageResource(R.drawable.button_alt_pressed);
                    alt1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Alt1", "0");
                    editor.commit();

                    alt1.setImageResource(R.drawable.button_alt);
                    alt1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });

        shift1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Shift1", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Shift1", "1");
                    editor.commit();

                    shift1.setImageResource(R.drawable.button_shift_pressed);
                    shift1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Shift1", "0");
                    editor.commit();

                    shift1.setImageResource(R.drawable.button_shift);
                    shift1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });

        sbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Spacebar1", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Spacebar1", "1");
                    editor.commit();

                    sbar1.setImageResource(R.drawable.button_spacebar_pressed);
                    sbar1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Spacebar1", "0");
                    editor.commit();

                    sbar1.setImageResource(R.drawable.button_spacebar);
                    sbar1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });


        ///////////////////////////////////////////////////////////////////////////

        ctrl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Ctrl2", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Ctrl2", "1");
                    editor.commit();

                    ctrl2.setImageResource(R.drawable.button_ctrl_pressed);
                    ctrl2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Ctrl2", "0");
                    editor.commit();

                    ctrl2.setImageResource(R.drawable.button_ctrl);
                    ctrl2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });

        alt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Alt2", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Alt2", "1");
                    editor.commit();

                    alt2.setImageResource(R.drawable.button_alt_pressed);
                    alt2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Alt2", "0");
                    editor.commit();

                    alt2.setImageResource(R.drawable.button_alt);
                    alt2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });

        shift2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Shift2", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Shift2", "1");
                    editor.commit();

                    shift2.setImageResource(R.drawable.button_shift_pressed);
                    shift2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Shift2", "0");
                    editor.commit();

                    shift2.setImageResource(R.drawable.button_shift);
                    shift2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });

        sbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Spacebar2", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Spacebar2", "1");
                    editor.commit();

                    sbar2.setImageResource(R.drawable.button_spacebar_pressed);
                    sbar2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Spacebar2", "0");
                    editor.commit();

                    sbar2.setImageResource(R.drawable.button_spacebar);
                    sbar2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });


/*//////////////////////////////////////////////////////////////////////////


        ctrl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Ctrl3", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Ctrl3", "1");
                    editor.commit();

                    ctrl3.setImageResource(R.drawable.button_ctrl_pressed);
                    ctrl3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Ctrl3", "0");
                    editor.commit();

                    ctrl3.setImageResource(R.drawable.button_ctrl);
                    ctrl3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });

        alt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Alt3", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Alt3", "1");
                    editor.commit();

                    alt3.setImageResource(R.drawable.button_alt_pressed);
                    alt3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Alt3", "0");
                    editor.commit();

                    alt3.setImageResource(R.drawable.button_alt);
                    alt3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }


            }
        });

        shift3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Shift3", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Shift3", "1");
                    editor.commit();

                    shift3.setImageResource(R.drawable.button_shift_pressed);
                    shift3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Shift3", "0");
                    editor.commit();

                    shift3.setImageResource(R.drawable.button_shift);
                    shift3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });


        sbar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                if (settings.getString("Spacebar3", "0").equals("0")) {
                    //false면 true로 변환.// 이미지도 선택으로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Spacebar3", "1");
                    editor.commit();

                    sbar3.setImageResource(R.drawable.button_spacebar_pressed);
                    sbar3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                } else {
                    //true면 false로 변환.// 이미지도 해제로
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Spacebar3", "0");
                    editor.commit();

                    sbar3.setImageResource(R.drawable.button_spacebar);
                    sbar3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

            }
        });

*/
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pressedCofirm();
            }
        });


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pressed1();
                getPage1Settings();
                getPage1key();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pressed2();
                getPage2Settings();
                getPage2key();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pressed3();
                //getPage3Settings();
                //getPage3key();
            }
        });

    }


    protected void getPage1key() {
        SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);
        ke1.setText(settings.getString("key1", "0"));

    }

    protected void getPage2key() {

        SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);
        ke2.setText(settings.getString("key2", "0"));
    }

    protected void getPage3key() {
        SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);
        ke3.setText(settings.getString("key3", "0"));

    }


    protected void getPage1Settings() {


        SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

        if (settings.getString("Ctrl1", "0").equals("1")) {
            //ctrl1 이 눌러져 있으면/

            ctrl1.setImageResource(R.drawable.button_ctrl_pressed);
            ctrl1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            ctrl1.setImageResource(R.drawable.button_ctrl);
            ctrl1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


        if (settings.getString("Alt1", "0").equals("1")) {
            //alt1 이 눌러져 있으면/

            alt1.setImageResource(R.drawable.button_alt_pressed);
            alt1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            alt1.setImageResource(R.drawable.button_alt);
            alt1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


        if (settings.getString("Shift1", "0").equals("1")) {
            //shift1 이 눌러져 있으면/

            shift1.setImageResource(R.drawable.button_shift_pressed);
            shift1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            shift1.setImageResource(R.drawable.button_shift);
            shift1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        if (settings.getString("Spacebar1", "0").equals("1")) {
            //spacebsr1 이 눌러져 있으면/

            sbar1.setImageResource(R.drawable.button_spacebar_pressed);
            sbar1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            sbar1.setImageResource(R.drawable.button_spacebar);
            sbar1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


    }

    protected void getPage2Settings() {

        SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

        if (settings.getString("Ctrl2", "0").equals("1")) {
            //ctrl1 이 눌러져 있으면/

            ctrl2.setImageResource(R.drawable.button_ctrl_pressed);
            ctrl2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            ctrl2.setImageResource(R.drawable.button_ctrl);
            ctrl2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


        if (settings.getString("Alt2", "0").equals("1")) {
            //alt1 이 눌러져 있으면/

            alt2.setImageResource(R.drawable.button_alt_pressed);
            alt2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            alt2.setImageResource(R.drawable.button_alt);
            alt2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


        if (settings.getString("Shift2", "0").equals("1")) {
            //shift1 이 눌러져 있으면/

            shift2.setImageResource(R.drawable.button_shift_pressed);
            shift2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            shift2.setImageResource(R.drawable.button_shift);
            shift2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        if (settings.getString("Spacebar2", "0").equals("1")) {
            //spacebsr1 이 눌러져 있으면/

            sbar2.setImageResource(R.drawable.button_spacebar_pressed);
            sbar2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            sbar2.setImageResource(R.drawable.button_spacebar);
            sbar2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


    }
/*
    protected void getPage3Settings() {

        SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

        if (settings.getString("Ctrl3", "0").equals("1")) {
            //ctrl1 이 눌러져 있으면/

            ctrl3.setImageResource(R.drawable.button_ctrl_pressed);
            ctrl3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            ctrl3.setImageResource(R.drawable.button_ctrl);
            ctrl3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


        if (settings.getString("Alt3", "0").equals("1")) {
            //alt1 이 눌러져 있으면/

            alt3.setImageResource(R.drawable.button_alt_pressed);
            alt3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            alt3.setImageResource(R.drawable.button_alt);
            alt3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


        if (settings.getString("Shift3", "0").equals("1")) {
            //shift1 이 눌러져 있으면/

            shift3.setImageResource(R.drawable.button_shift_pressed);
            shift3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            shift3.setImageResource(R.drawable.button_shift);
            shift3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        if (settings.getString("Spacebar3", "0").equals("1")) {
            //spacebsr1 이 눌러져 있으면/

            sbar3.setImageResource(R.drawable.button_spacebar_pressed);
            sbar3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {

            sbar3.setImageResource(R.drawable.button_spacebar);
            sbar3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }


    }

*/
    protected void pressedCofirm() {

        finish();
        overridePendingTransition(0, 0);


    }


    protected void pressed1() {

        p1.setVisibility(View.VISIBLE);
        p2.setVisibility(View.GONE);
        p3.setVisibility(View.GONE);

        bt1.setImageResource(R.drawable.button1_pressed);
        bt2.setImageResource(R.drawable.button2);
        bt3.setImageResource(R.drawable.button3);


    }

    protected void pressed2() {

        p1.setVisibility(View.GONE);
        p2.setVisibility(View.VISIBLE);
        p3.setVisibility(View.GONE);

        bt1.setImageResource(R.drawable.button1);
        bt2.setImageResource(R.drawable.button_2_pressed);
        bt3.setImageResource(R.drawable.button3);


    }

    protected void pressed3() {

        p1.setVisibility(View.GONE);
        p2.setVisibility(View.GONE);
        p3.setVisibility(View.VISIBLE);

        bt1.setImageResource(R.drawable.button1);
        bt2.setImageResource(R.drawable.button2);
        bt3.setImageResource(R.drawable.button_3_pressed);

    }



    public int confirmKey(String string){
        int value = 0;

        switch(string){

            case "a" :
                value = 1;
                break;
            case "b" :
                value = 2;
                break;
            case "c" :
                value = 3;
                break;
            case "d" :
                value = 4;
                break;
            case "e" :
                value = 5;
                break;
            case "f" :
                value = 6;
                break;
            case "g" :
                value = 7;
                break;
            case "h" :
                value = 8;
                break;
            case "i" :
                value = 9;
                break;
            case "j" :
                value = 10;
                break;
            case "k" :
                value = 11;
                break;
            case "l" :
                value = 12;
                break;
            case "m" :
                value = 13;
                break;
            case "n" :
                value = 14;
                break;
            case "o" :
                value = 15;
                break;
            case "p" :
                value = 16;
                break;
            case "q" :
                value = 17;
                break;
            case "r" :
                value = 18;
                break;
            case "s" :
                value = 19;
                break;
            case "t" :
                value = 20;
                break;
            case "u" :
                value = 21;
                break;
            case "v" :
                value = 22;
                break;
            case "w" :
                value = 23;
                break;
            case "x" :
                value = 24;
                break;
            case "y" :
                value = 25;
                break;
            case "z" :
                value = 26;
                break;
            case "[" :
                value = 27;
                break;
            case "]" :
                value = 28;
                break;
            case "0" :
                value = 29;
                break;
            case "1" :
                value = 30;
                break;
            case "2" :
                value = 31;
                break;
            case "3" :
                value = 32;
                break;
            case "4" :
                value = 33;
                break;
            case "5" :
                value = 34;
                break;
            case "6" :
                value = 35;
                break;
            case "7" :
                value = 36;
                break;
            case "8" :
                value = 37;
                break;
            case "9" :
                value = 38;
                break;
            case "<" :
                value = 39;
                break;
            case ">" :
                value = 40;
                break;
            default:
                value = 0;
                break;

        }

        return value;
    }


    @Override
    public void onBackPressed() {


        finish();
        overridePendingTransition(0, 0);
    }
}
