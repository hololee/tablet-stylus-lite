package com.leejh.hanul.stylus.lite;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by pc on 2016-06-03.
 */
public class padViewThreadRight extends Thread {

    Paint paint;

    private SurfaceHolder mHolder;
    private padRightActivity.padView mPadView;
    private boolean mRun = false;


    public padViewThreadRight(SurfaceHolder holder, padRightActivity.padView padview) {
        mHolder = holder;
        mPadView = padview;

        paint = new Paint();


    }


    public void setRun(boolean run) {
        mRun = run;

    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas canvas;

        while (mRun) {
            canvas = null;


            try {
                canvas = mHolder.lockCanvas(null);
                synchronized (mHolder) {


                    paint.setColor(Color.argb(255, 26, 232, 164));//지정해 놓은 color;
                    paint.setStrokeWidth(1);// 색상이 밝아서 1만 해도???
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setAntiAlias(true);

                    canvas.drawPath(padRightActivity.pathR, paint);


                    mPadView.onDraw(canvas);

                }
            }catch(Exception e) {

                e.printStackTrace();

            } finally {

                if (canvas != null) {

                    mHolder.unlockCanvasAndPost(canvas);

                }

            }


        }
    }
}
