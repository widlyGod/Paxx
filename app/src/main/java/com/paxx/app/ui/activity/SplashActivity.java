package com.paxx.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.paxx.app.R;
import com.paxx.app.ui.fragment.SplashFragment;
import com.paxx.app.utils.PageManager;


public class SplashActivity extends BaseActivity {


  @Override
  protected int initLayout(Bundle savedInstanceState) {
    return  R.layout.activity_main;
  }

  @Override
  protected void initComp(Bundle savedInstanceState) {
    PageManager mPageManager = new PageManager(this, R.id.frame_main);
    mPageManager.switchFragment(SplashFragment.class, null, PageManager.ADD);
  }

  @Override
  protected void initData(Intent intent) {

  }

  @Override
  protected void receiveData(Intent intent) {

  }

  @Override
  protected void restoreData(Intent intent) {

  }

  @Override
  public void onClick(View v) {

  }
}


/* Location:              /Users/admin/Downloads/apktool-jdgui/apktool/dex2jar-0.0.9.15/classes_dex2jar.jar!/com/pax/app/ui/activity/SplashActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */