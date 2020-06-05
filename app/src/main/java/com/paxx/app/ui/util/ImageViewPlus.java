package com.paxx.app.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by hongyang on 2018/4/5.
 */

public class ImageViewPlus extends View implements Runnable{
    private Paint mPaint;
    private Context mContext;
    private int screenWidth;
    private int screenHeight;
    private int radial = 50;

    int color = Color.parseColor("#5144AE");

    public ImageViewPlus(Context context) {
        super(context, null);
        init();
    }

    public ImageViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setColor(int c) {
        switch (c){
            case 0:
                this.color = Color.parseColor("#5144AE");
                break;
//            case 1:
//                this.color = Color.parseColor("#5162C2");
//                break;
            case 1:
                this.color = Color.parseColor("#5196CA");
                break;
//            case 3:
//                this.color = Color.parseColor("#51A6C8");
//                break;
            case 2:
                this.color = Color.parseColor("#51B6A7");
                break;
            case 3:
                this.color = Color.parseColor("#7CCE8E");
                break;
            case 4:
                this.color = Color.parseColor("#ABCE8E");
                break;
//            case 4:
//                this.color = Color.parseColor("#D3CE8E");
//                break;
            case 5:
                this.color = Color.parseColor("#E7B795");
                break;
            case 6:
                this.color = Color.parseColor("#ED9477");
                break;
//            case 10:
//                this.color = Color.parseColor("#ED4F4F");
//                break;
//            case 7:
//                this.color = Color.parseColor("#ED2A39");
//                break;
//            case 7:
//                this.color = Color.parseColor("#ED0F14");
//                break;
            default:
                this.color = Color.parseColor("#FF0000");
                break;
        }
        init();
        invalidate();
    }

    /**
     * 由于onDraw()方法会不停的绘制 所以需要定义一个初始化画笔方法 避免导致不停创建造成内存消耗过大
     */
    private void init() {
        mPaint = new Paint();
        // 设置画笔为抗锯齿
        mPaint.setAntiAlias(true);
        // 设置颜色为红色
        mPaint.setColor(color);
        /**
         * 画笔样式分三种： 1.Paint.Style.STROKE：描边 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 设置描边的粗细，单位：像素px 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        /**
         * 获取屏幕的宽高
         *
         */
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        screenWidth = dip2px(mContext,200);
        screenHeight = dip2px(mContext,200);
        radial = dip2px(mContext,100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制圆环drawCircle的前两个参数表示圆心的XY坐标， 这里我们用到了一个工具类获取屏幕尺寸以便将其圆心设置在屏幕中心位置，
         * 第三个参数是圆的半径，第四个参数则为我们的画笔
         *
         */
        canvas.drawCircle(radial , radial , radial, mPaint);

    }

    @Override
    public void run() {
        /**
         * 使用while循环不断的刷新view的半径
         * 当半径小于100每次增加10 invalidate()重绘view会报错
         * android.view.ViewRootImpl$CalledFromWrongThreadException 是非主线程更新UI
         * Android给提供postInvalidate();快捷方法来重绘view
         *  现在明白了invalidate和postInvalidate的小区别了吧
         */
        postInvalidate();
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}