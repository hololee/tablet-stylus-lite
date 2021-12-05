package com.leejh.hanul.stylus.lite;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Path;
import android.graphics.PorterDuff;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;

import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyCloseAd;
import com.fsn.cauly.CaulyCloseAdListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class padLeftActivity extends Activity implements CaulyCloseAdListener {


    private static final String APP_CODE = "RYgvkAHq"; // 광고 요청을 위한 App Code
    CaulyCloseAd mCloseAd ;                         // CloseAd광고 객체


    String ip;
    int portNumber = 7748;

    boolean doubleClicked = false;

    final static String SETTING = "setting";

    final static int PAD_KEY_CLICK_UP = 8888;
    final static int PAD_KEY_CLICK = 7777;
    final static int PAD_DRAG_MODE = 4444;
    final static int PAD_RIGHT_CLICK = 5555;
    final static int PAD_ONE_CLICK = 6666;
    final static int TOUCH_UP = 3;
    final static int TOUCH_DOWN = 2;

    final static int DISPLAY_INFO = 4545;

    final static int MODE_SET = 9999;
    boolean modeSet = false;



    int phoneX;
    int phoneY;
    boolean modeChanged = false;


    boolean newButon3Selected = true;

    float oldXcordinate = 0;
    float oldYcordinate = 0;
    float newXcordinate = 0;
    float newYcordinate = 0;

    Socket socket;

    ObjectOutputStream out;
    ObjectInputStream in;

    RelativeLayout view;

    ImageButton button1, button2, button3, button4;
    ImageButton button5,button6;

    RelativeLayout bx1, bx2, bx3, bx4, bx5;

    ImageButton newButton1,  newButton3;
    RelativeLayout newBx1, newBx3;

    GestureDetector mDetector;
    myGestureListener listener;

    boolean buttonSFlag = false;

    RelativeLayout sSetting;


    float Svalue = 1;

    SeekBar sbar;

    public static Path path;

    padView pad;

    float y;
    float x;

    int Ver;

    @Override
    protected void onResume() {
        super.onResume();

        if (mCloseAd != null)
            mCloseAd.resume(this); // 필수 호출
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
        setContentView(R.layout.activity_pad_left);


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



        //api level 확인

        Ver = Build.VERSION.SDK_INT;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        phoneX = dm.widthPixels;
        phoneY = dm.heightPixels;


        view = (RelativeLayout) findViewById(R.id.pads);

        pad = new padView(getApplicationContext());
        pad.setFocusable(true);

        view.addView(pad);


        //  Toast.makeText(getApplicationContext(),""+view.getHeight(),Toast.LENGTH_SHORT).show();

        //오른쪽부터 버튼 1,2,3,4,
        button1 = (ImageButton) findViewById(R.id.bt_01);
        button2 = (ImageButton) findViewById(R.id.bt_02);
        button3 = (ImageButton) findViewById(R.id.bt_03);
        button4 = (ImageButton) findViewById(R.id.bt_04); //s
        button5 = (ImageButton) findViewById(R.id.bt_05);
        button6 = (ImageButton) findViewById(R.id.bt_06);


        newButton1 = (ImageButton) findViewById(R.id.bt_new_01);
        newButton3 = (ImageButton) findViewById(R.id.bt_new_03);

        sSetting = (RelativeLayout) findViewById(R.id.Ssetting);

        bx1 = (RelativeLayout) findViewById(R.id.bt_box1);
        bx2 = (RelativeLayout) findViewById(R.id.bt_box2);
        bx3 = (RelativeLayout) findViewById(R.id.bt_box3);
        bx4 = (RelativeLayout) findViewById(R.id.bt_box4);
        bx5 = (RelativeLayout) findViewById(R.id.bt_box5);


        newBx1 = (RelativeLayout) findViewById(R.id.bt_new_box1);

        newBx3 = (RelativeLayout) findViewById(R.id.bt_new_box3);

        sbar = (SeekBar) findViewById(R.id.sbar);

        Intent intent = getIntent();
        ip = intent.getStringExtra("IP");


        path = new Path();


        socketThread thread = new socketThread(ip);
        thread.setDaemon(true);
        thread.start();


        listener = new myGestureListener();
        mDetector = new GestureDetector(getApplicationContext(), listener);


        pad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = event.getX();
                y = event.getY();


                mDetector.onTouchEvent(event);

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {

                        path.moveTo(x, y);

                        if (modeChanged) {

                            try {
                                new modeThread(MODE_SET, String.valueOf((int) event.getY()), String.valueOf((int) (phoneX - event.getX()))).start();
                                Thread.sleep(1);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (newButon3Selected) {
                                new sendThread(String.valueOf(PAD_DRAG_MODE), String.valueOf(TOUCH_DOWN)).start();
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }


                            }


                        }

                        oldXcordinate = (int) event.getX() / Svalue;
                        oldYcordinate = (int) event.getY() / Svalue;

                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {

                        newXcordinate = (int) event.getX() / Svalue;
                        newYcordinate = (int) event.getY() / Svalue;

                        float finalX = ((newXcordinate - oldXcordinate));
                        float finalY = ((newYcordinate - oldYcordinate));

                        //나눗셈으로  민감도 조정. 주의할것. n 보다 작은 값에 대해서는 0으로 인식.
                        // int finalX = (int) ((newXcordinate - oldXcordinate)/n);
                        // int finalY = (int) ((newYcordinate - oldYcordinate)/n);

                        try {

                            if (modeSet) {
                                new modeThread(MODE_SET, String.valueOf((int) event.getY()), String.valueOf((int) (phoneX - event.getX()))).start();

                                x = event.getX();
                                y = event.getY();
                                path.lineTo(x, y);
                                //Toast.makeText(getApplicationContext(),"success!",Toast.LENGTH_SHORT).show();
                                // Log.d("log","invalidated");


                            } else {

                                new sendThread(String.valueOf((int) finalY), String.valueOf((int) (finalX * (-1)))).start();
                                Thread.sleep(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        oldXcordinate = newXcordinate;
                        oldYcordinate = newYcordinate;

                        break;
                    }
                    case MotionEvent.ACTION_UP: {


                        new sendThread(String.valueOf(PAD_DRAG_MODE), String.valueOf(TOUCH_UP)).start();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        oldXcordinate = 0;
                        oldYcordinate = 0;
                        newXcordinate = 0;
                        newYcordinate = 0;

                        doubleClicked = false;


                        break;
                    }
                }

                return true;
            }
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),getString(R.string.lite),Toast.LENGTH_SHORT).show();


            }
        });


        newButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                path.reset();


                for (int i = 0; i < 5; i++) {
                    Canvas canvas = pad.getHolder().lockCanvas();

                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    pad.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        });

        newButton3.setImageResource(R.drawable.button_v_left_pressed);
        newButton3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        newButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!newButon3Selected) {


                    newButton3.setImageResource(R.drawable.button_v_left_pressed);
                    newButton3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    newButon3Selected = true;


                } else {

                    newButton3.setImageResource(R.drawable.button_v_left);
                    newButton3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    newButon3Selected = false;
                }


            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mode 정보 보내기

                new displaySendThread(phoneX, phoneY).start();

                if (!modeSet) {
                    button5.setImageResource(R.drawable.button_mode_left_pressed);
                    button5.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                    bx3.setVisibility(View.GONE);
                    bx4.setVisibility(View.GONE);

                    newBx1.setVisibility(View.VISIBLE);
                    newBx3.setVisibility(View.VISIBLE);


                    modeChanged = true;
                } else {
                    button5.setImageResource(R.drawable.button_mode_left);
                    button5.setScaleType(ImageView.ScaleType.CENTER_INSIDE);



                    bx3.setVisibility(View.VISIBLE);
                    bx4.setVisibility(View.VISIBLE);

                    newBx1.setVisibility(View.GONE);
                    newBx3.setVisibility(View.GONE);


                    modeChanged = false;
                }

                modeSet = !modeSet;

            }
        });


        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

              Toast.makeText(getApplicationContext(),getString(R.string.lite),Toast.LENGTH_SHORT).show();


                return true;
            }
        });

        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Toast.makeText(getApplicationContext(),getString(R.string.lite),Toast.LENGTH_SHORT).show();



                return true;
            }
        });


        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {

                        new sendThread(String.valueOf(PAD_DRAG_MODE), String.valueOf(TOUCH_DOWN)).start();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        button3.setImageResource(R.drawable.button_3_left_pressed);
                        button3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
/*

                        SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                        new keyDownThread(settings.getString("Ctrl3", "0"), settings.getString("Alt3", "0"), settings.getString("Shift3", "0"), settings.getString("Spacebar3", "0"), settings.getString("key3", "0")).start();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
*/

                        break;
                    }

                    case MotionEvent.ACTION_UP: {


                        new sendThread(String.valueOf(PAD_DRAG_MODE), String.valueOf(TOUCH_UP)).start();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                        button3.setImageResource(R.drawable.button_3_left);
                        button3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
/*
                        SharedPreferences settings = getSharedPreferences(SETTING, MODE_PRIVATE);

                        new keyUpThread(settings.getString("Ctrl3", "0"), settings.getString("Alt3", "0"), settings.getString("Shift3", "0"), settings.getString("Spacebar3", "0"), settings.getString("key3", "0")).start();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

*/
                        break;
                    }


                }


                return true;
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!buttonSFlag) {
                    //버튼이 눌려있지 않으면

                    bx1.setVisibility(View.GONE);
                    bx2.setVisibility(View.GONE);
                    bx3.setVisibility(View.GONE);
                    bx5.setVisibility(View.GONE);
                    sSetting.setVisibility(View.VISIBLE);

                    buttonSFlag = true;

                    button4.setImageResource(R.drawable.button_s_left_pressed);
                    button4.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                } else {
                    // 눌려있으면

                    bx1.setVisibility(View.VISIBLE);
                    bx2.setVisibility(View.VISIBLE);
                    bx3.setVisibility(View.VISIBLE);
                    bx5.setVisibility(View.VISIBLE);
                    sSetting.setVisibility(View.GONE);

                    buttonSFlag = false;

                    button4.setImageResource(R.drawable.button_s_left);
                    button4.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                }


            }
        });


        sbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                switch (progress) {
                    case 0:
                        Svalue = 2.5f;
                        break;
                    case 1:
                        Svalue = 2f;
                        break;
                    case 2:
                        Svalue = 1.5f;
                        break;
                    case 3:
                        Svalue = 1;
                        break;
                    case 4:
                        Svalue = 0.75f;
                        break;
                    case 5:
                        Svalue = 0.5f;
                        break;
                    case 6:
                        Svalue = 0.25f;
                        break;


                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
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

    class socketThread extends Thread {

        String mIp;


        private Handler handler = new Handler() {

            public void handleMessage(Message msg) {
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.string15), Toast.LENGTH_LONG).show();
                super.handleMessage(msg);
            }
        };

        public socketThread(String ip) {

            mIp = ip;

        }

        @Override
        public void run() {


            try {
                socket = new Socket(mIp, portNumber);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());


            } catch (Exception e) {

                handler.sendEmptyMessage(0);

            }
        }
    }


    class keyUpThread extends Thread {


        String mArg1;
        String mArg2;
        String mArg3;
        String mArg4;
        String mArg5;


        public keyUpThread(String arg1, String arg2, String arg3, String arg4, String arg5) {

            mArg1 = arg1;
            mArg2 = arg2;
            mArg3 = arg3;
            mArg4 = arg4;
            mArg5 = arg5;

        }

        @Override
        public void run() {

            try {
                out.writeObject(String.valueOf(PAD_KEY_CLICK_UP) + "/" + mArg1 + "/" + mArg2 + "/" + mArg3 + "/" + mArg4 + "/" + mArg5);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    class keyDownThread extends Thread {


        String mArg1;
        String mArg2;
        String mArg3;
        String mArg4;
        String mArg5;


        public keyDownThread(String arg1, String arg2, String arg3, String arg4, String arg5) {

            mArg1 = arg1;
            mArg2 = arg2;
            mArg3 = arg3;
            mArg4 = arg4;
            mArg5 = arg5;

        }

        @Override
        public void run() {

            try {
                out.writeObject(String.valueOf(PAD_KEY_CLICK) + "/" + mArg1 + "/" + mArg2 + "/" + mArg3 + "/" + mArg4 + "/" + mArg5);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    class sendThread extends Thread {

        String xCordinate;
        String yCordinate;


        public sendThread(String xCordinate, String yCordinate) {

            this.xCordinate = xCordinate;
            this.yCordinate = yCordinate;
        }

        @Override
        public void run() {

            try {
                out.writeObject(xCordinate + "/" + yCordinate);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class modeThread extends Thread {

        String xCordinate;
        String yCordinate;
        int Mode;

        public modeThread(int mode, String xCordinate, String yCordinate) {

            this.xCordinate = xCordinate;
            this.yCordinate = yCordinate;
            this.Mode = mode;
        }

        @Override
        public void run() {

            try {
                out.writeObject(Mode + "/" + xCordinate + "/" + yCordinate);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class displaySendThread extends Thread {

        int displayX;
        int displayY;


        public displaySendThread(int x, int y) {

            displayX = y;
            displayY = x;

            displayX = displayX - dpToPx(getApplicationContext(), 80);

        }

        @Override
        public void run() {

            try {
                out.writeObject(DISPLAY_INFO + "/" + displayX + "/" + displayY);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class myGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            doubleClicked = true;

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            if (!doubleClicked) {
                new sendThread(String.valueOf(PAD_RIGHT_CLICK), "1").start();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            new sendThread(String.valueOf(PAD_ONE_CLICK), "1").start();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            return super.onSingleTapConfirmed(e);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 화면이 꺼지거나 app  에서 나갔을 경우 재연결을 위해서 종료.
        finish();

    }


    @Override
    public void onBackPressed() {

    }


    public class padView extends SurfaceView implements SurfaceHolder.Callback {

        private padViewThread mThread = new padViewThread(getHolder(), this);

        public padView(Context context) {
            super(context);
            getHolder().addCallback(this);

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {


            mThread.setRun(true);
            mThread.start();

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            mThread.setRun(false);


            while (retry) {

                try {
                    mThread.join();
                    retry = false;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
