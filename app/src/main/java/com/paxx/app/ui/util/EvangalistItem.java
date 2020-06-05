package com.paxx.app.ui.util;

import android.os.Bundle;

import com.paxx.app.MainApplication;

public class EvangalistItem
{
  public static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";
  public static final String KEY_HEADER = "KEY_HEADER";
  public static final String KEY_IMAGE = "KEY_IMAGE";
  private String description;
  private String header;
  private int image;
  
  public EvangalistItem() {}
  
  public EvangalistItem(int paramInt1, int paramInt2, int paramInt3)
  {
    this.header = MainApplication.getInstance().getResources().getString(paramInt1);
    this.description = MainApplication.getInstance().getResources().getString(paramInt2);
    this.image = paramInt3;
  }
  
  public static EvangalistItem fromBundle(Bundle paramBundle)
  {
    EvangalistItem localEvangalistItem = new EvangalistItem();
    localEvangalistItem.header = paramBundle.getString("KEY_HEADER");
    localEvangalistItem.description = paramBundle.getString("KEY_DESCRIPTION");
    localEvangalistItem.image = paramBundle.getInt("KEY_IMAGE");
    return localEvangalistItem;
  }
  
  public Bundle bundleUp()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("KEY_HEADER", this.header);
    localBundle.putString("KEY_DESCRIPTION", this.description);
    localBundle.putInt("KEY_IMAGE", this.image);
    return localBundle;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String getHeader()
  {
    return this.header;
  }
  
  public int getImage()
  {
    return this.image;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void setHeader(String paramString)
  {
    this.header = paramString;
  }
  
  public void setImage(int paramInt)
  {
    this.image = paramInt;
  }
}


/* Location:              /Users/admin/Downloads/apktool-jdgui/apktool/dex2jar-0.0.9.15/classes_dex2jar.jar!/com/pax/app/data/model/EvangalistItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */