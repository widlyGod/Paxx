package com.paxx.app.ui.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DV_HorizontalSpaceItemDecoration
  extends RecyclerView.ItemDecoration
{
  private final int horizontalSpaceWidth;

  public DV_HorizontalSpaceItemDecoration(int paramInt)
  {
    this.horizontalSpaceWidth = paramInt;
  }

  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    paramRect.left = (this.horizontalSpaceWidth / 2);
    paramRect.right = (this.horizontalSpaceWidth / 2);
  }
}


/* Location:              /Users/admin/Downloads/apktool-jdgui/apktool/dex2jar-0.0.9.15/classes_dex2jar.jar!/com/pax/app/ui/view/recyclerview/DV_HorizontalSpaceItemDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */