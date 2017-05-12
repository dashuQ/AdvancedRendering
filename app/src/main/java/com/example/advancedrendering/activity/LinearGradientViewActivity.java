package com.example.advancedrendering.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.example.advancedrendering.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinearGradientViewActivity extends AppCompatActivity {

    @BindView(R.id.iv2)
    ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_gradient_view);

        ButterKnife.bind(this);

        iv2.setImageBitmap(createReflectedImage(((BitmapDrawable) this.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap(), 110));

    }

    public Bitmap createReflectedImage(Bitmap originalImage, int reflectionHeight) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        // 实现图片翻转90度
        matrix.preScale(1, -1);
        if (reflectionHeight > height)
            reflectionHeight = height;
        // 创建倒影图片（是原始图片的一半大小）
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height - reflectionHeight, width, reflectionHeight, matrix, false);
        // 创建倒影图片
        Bitmap finalReflection = Bitmap.createBitmap(width, reflectionHeight, Bitmap.Config.ARGB_8888);
        // 创建画布
        Canvas canvas = new Canvas(finalReflection);
        // canvas.drawBitmap(originalImage, 0, 0, null);
        // 把倒影图片画到画布上
        canvas.drawBitmap(reflectionImage, 0, 0, null);
        Paint shaderPaint = new Paint();
        // 创建线性渐变LinearGradient对象
        LinearGradient shader = new LinearGradient(0, 0, 0, finalReflection.getHeight() + 1, 0x70ffffff, 0x00ffffff, Shader.TileMode.MIRROR);
        shaderPaint.setShader(shader);
        shaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
        canvas.drawRect(0, 0, width, finalReflection.getHeight(), shaderPaint);
        return finalReflection;
    }

}
