package com.paxx.app.ui.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class AnimatedCircle
        extends View {
    private final Paint circlePaint = new Paint(20);
    private float percentFull;

    public AnimatedCircle(Context paramContext) {
        super(paramContext);
        initCommon();
    }

    public AnimatedCircle(Context paramContext, @Nullable AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initCommon();
    }

    public AnimatedCircle(Context paramContext, @Nullable AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        initCommon();
    }

    private void initCommon() {
        this.circlePaint.setStrokeWidth(10.0F);
        this.circlePaint.setStyle(Paint.Style.STROKE);
        this.circlePaint.setColor(-65536);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        this.circlePaint.setPathEffect(effects);
    }

    public void onDraw(Canvas paramCanvas) {
        float f1 = paramCanvas.getWidth();
        float f2 = paramCanvas.getHeight();
        float f3 = this.circlePaint.getStrokeWidth();
        paramCanvas.drawArc((int) f3, (int) f3, f1 - f3, f2 - f3, -90.0F, (int) (360.0F * this.percentFull), false, this.circlePaint);
    }

    public void setColor(int paramInt) {
        if (paramInt == this.circlePaint.getColor()) {
            return;
        }
        this.circlePaint.setColor(paramInt);
        invalidate();
    }

    public void setPercentFull(float paramFloat) {
        if (paramFloat == this.percentFull) {
            return;
        }
        this.percentFull = paramFloat;
        invalidate();
    }
}


/* Location:              /Users/admin/Downloads/apktool-jdgui/apktool/dex2jar-0.0.9.15/classes_dex2jar.jar!/com/pax/app/ui/view/AnimatedCircle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */