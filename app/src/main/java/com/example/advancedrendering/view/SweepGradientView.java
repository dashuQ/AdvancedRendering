package com.example.advancedrendering.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lenovo on 2017/5/12.
 */

public class SweepGradientView extends FrameLayout {
    private int viewSize = 800;
    private Paint mPaintLine;
    private Paint mPaintSector;  // 扫描效果的画笔
    public boolean isstart = false;
    private ScanThread mThread;  // 扫描的线程
    private int start = 0;
    private Matrix matrix;

    public SweepGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        mThread = new ScanThread(this);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public SweepGradientView(Context context) {
        this(context, null);
    }

    /**
     * 画笔的基本设置
     */
    private void initPaint() {
        mPaintLine = new Paint();
        mPaintLine.setStrokeWidth(10);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setColor(0xff000000);
        mPaintSector = new Paint();
        mPaintSector.setColor(0x9D00ff00);
        mPaintSector.setAntiAlias(true);
    }

    public void setViewSize(int size) {
        this.viewSize = size;
        setMeasuredDimension(viewSize, viewSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(viewSize, viewSize);
    }

    /**
     * 开始扫描
     */
    public void startScann() {
        mThread.start();
        isstart = true;
    }

    /**
     * 暂停扫描
     */
    public void stopScann() {
        if (isstart) {
            Thread.interrupted();
            isstart = false;
        }
    }

    public void setList(ArrayList<View> list) {
        for (int i = 0; i < list.size(); i++) {
            int xy[] = getRamdomXY();
            list.get(i).setX(xy[0]);
            list.get(i).setY(xy[1]);
            addView(list.get(i));
        }
    }

    private int[] getRamdomXY() {
        Random rand = new Random();
        int x = rand.nextInt(900);
        int y = rand.nextInt(900);
        int r = (int) ((float) viewSize / 2);
        if ((x >= r - 350 || x <= r + 350) && (y >= r - 350 || y <= r + 350)) {
            int xy[] = new int[2];
            xy[0] = x;
            xy[1] = y;
            return xy;
        } else {
            return getRamdomXY();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(viewSize / 2, viewSize / 2, 175, mPaintLine);
        canvas.drawCircle(viewSize / 2, viewSize / 2, 350, mPaintLine);
        canvas.drawLine(viewSize / 2, 0, viewSize / 2, viewSize, mPaintLine);
        canvas.drawLine(0, viewSize / 2, viewSize, viewSize / 2, mPaintLine); //
        /**
         * SweepGradient扫描/梯度渲染
         * SweepGradient(float cx, float cy, int color0, int color1)
         * cx   渲染中心点x 坐标
         * cy   渲染中心点y 坐标
         * color0   起始渲染颜色
         * color1   结束渲染颜色
         *
         */
        Shader mShader = new SweepGradient(viewSize / 2, viewSize / 2, Color.TRANSPARENT, Color.GREEN);
        mPaintSector.setShader(mShader);
        canvas.concat(matrix);
        canvas.drawCircle(viewSize / 2, viewSize / 2, 350, mPaintSector);
        super.onDraw(canvas);
    }


    protected class ScanThread extends Thread {
        private SweepGradientView view;

        public ScanThread(SweepGradientView view) {
            this.view = view;
        }

        @Override
        public void run() {
            while (true) {
                if (isstart) {
                    view.post(new Runnable() {
                        public void run() {
                            start = start + 1;
                            matrix = new Matrix();
                            matrix.postRotate(start, viewSize / 2, viewSize / 2);
                            view.invalidate();
                        }
                    });
                    try {
                        Thread.sleep(10);  // 设置扫描的停止时间
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
