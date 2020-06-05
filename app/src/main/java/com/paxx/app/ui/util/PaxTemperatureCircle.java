package com.paxx.app.ui.util;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class PaxTemperatureCircle
  extends Drawable
{
  private static final float DRAW_STROKE_WIDTH_MULTIPLE = 1.3333F;
  private int backgroundAlpha = 255;
  private int backgroundColor = 0;
  private int borderAlpha = 255;
  private float mBorderWidth;
  private final Paint mPaint = new Paint(1);
  private final Rect mRect = new Rect();
  private final RectF mRectF = new RectF();
  private int numberOfPedals = 0;
  private int paxSymbolAlpha = 255;
  private boolean showBorder = true;
  
  public PaxTemperatureCircle()
  {
    this.mPaint.setStyle(Paint.Style.STROKE);
  }

  public void displayNumberOfPedals(int paramInt)
  {
    this.numberOfPedals = paramInt;
  }

  public void draw(@NonNull Canvas paramCanvas)
  {
    RectF localRectF1 = this.mRectF;
    copyBounds(this.mRect);
    localRectF1.set(this.mRect);
    RectF localRectF2 = new RectF(this.mRect);
    localRectF2.left = (localRectF2.left + this.mPaint.getStrokeWidth() + -3);
    localRectF2.right = (localRectF2.right - this.mPaint.getStrokeWidth() - -3);
    localRectF2.top = (localRectF2.top + this.mPaint.getStrokeWidth() + -3);
    localRectF2.bottom = (localRectF2.bottom - this.mPaint.getStrokeWidth() - -3);
    paramCanvas.save();
    if (this.showBorder)
    {
      Paint localPaint1 = new Paint(this.mPaint);
      localPaint1.setStyle(Paint.Style.STROKE);
      localPaint1.setColor(this.backgroundColor);
      localPaint1.setAlpha(this.borderAlpha);
      paramCanvas.drawOval(localRectF2, localPaint1);
    }
    if (this.backgroundColor != 0)
    {
      this.mPaint.setStyle(Paint.Style.FILL);
      this.mPaint.setColor(this.backgroundColor);
      this.mPaint.setAlpha(this.backgroundAlpha);
      paramCanvas.drawOval(localRectF1, this.mPaint);
    }
    if (this.numberOfPedals > 0)
    {
      Paint localPaint2 = new Paint(this.mPaint);
      localPaint2.setColor(this.backgroundColor);
      localPaint2.setStrokeWidth(20.0F);
      localPaint2.setStrokeCap(Paint.Cap.ROUND);
      localPaint2.setAlpha(this.paxSymbolAlpha);
      float f1 = 0.3F * paramCanvas.getWidth();
      float f2 = paramCanvas.getWidth() - f1;
      if (this.numberOfPedals >= 1) {
        paramCanvas.drawLine(paramCanvas.getWidth() / 2 - 30, paramCanvas.getHeight() / 2 - 30, f1, f1, localPaint2);
      }
      if (this.numberOfPedals >= 2) {
        paramCanvas.drawLine(30 + paramCanvas.getWidth() / 2, paramCanvas.getHeight() / 2 - 30, f2, f1, localPaint2);
      }
      if (this.numberOfPedals >= 3) {
        paramCanvas.drawLine(paramCanvas.getWidth() / 2 - 30, 30 + paramCanvas.getHeight() / 2, f1, f2, localPaint2);
      }
      if (this.numberOfPedals >= 4) {
        paramCanvas.drawLine(30 + paramCanvas.getWidth() / 2, 30 + paramCanvas.getHeight() / 2, f2, f2, localPaint2);
      }
    }
  }
  
  public float getBackgroundAlpha()
  {
    return this.backgroundAlpha;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public boolean getPadding(@NonNull Rect paramRect)
  {
    int i = Math.round(this.mBorderWidth);
    paramRect.set(i, i, i, i);
    return true;
  }
  
  public void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
    invalidateSelf();
  }
  
  public void setBackgroundAlpha(int paramInt)
  {
    this.backgroundAlpha = paramInt;
  }
  
  public void setBackgroundColor(int paramInt)
  {
    this.backgroundColor = paramInt;
  }
  
  public void setBorderAlpha(int paramInt)
  {
    this.borderAlpha = paramInt;
  }
  
  public void setBorderWidth(float paramFloat)
  {
    if (this.mBorderWidth != paramFloat)
    {
      this.mBorderWidth = paramFloat;
      this.mPaint.setStrokeWidth(1.3333F * paramFloat);
      invalidateSelf();
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mPaint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  public void setPaxSymbolAlpha(int paramInt)
  {
    this.paxSymbolAlpha = paramInt;
  }
  
  public void showBorder(boolean paramBoolean)
  {
    this.showBorder = paramBoolean;
  }
}


/* Location:              /Users/admin/Downloads/apktool-jdgui/apktool/dex2jar-0.0.9.15/classes_dex2jar.jar!/com/pax/app/ui/view/PaxTemperatureCircle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */