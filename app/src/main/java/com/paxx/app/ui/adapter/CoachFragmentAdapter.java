package com.paxx.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.paxx.app.ui.fragment.CoachmarkItemFragment;
import com.paxx.app.ui.util.EvangalistItem;
import com.paxx.app.ui.util.IconPagerAdapter;

import java.util.List;

public class CoachFragmentAdapter
  extends FragmentPagerAdapter
  implements IconPagerAdapter
{
  List<EvangalistItem> items;
  
  public CoachFragmentAdapter(FragmentManager paramFragmentManager, List<EvangalistItem> paramList)
  {
    super(paramFragmentManager);
    this.items = paramList;
  }
  
  public int getCount()
  {
    return this.items.size();
  }
  
  public int getIconResId(int paramInt)
  {
    return 0;
  }
  
  public Fragment getItem(int paramInt)
  {
    return CoachmarkItemFragment.newInstance(((EvangalistItem)this.items.get(paramInt % this.items.size())).bundleUp());
  }
  
  public CharSequence getPageTitle(int paramInt)
  {
    return "";
  }
}


/* Location:              /Users/admin/Downloads/apktool-jdgui/apktool/dex2jar-0.0.9.15/classes_dex2jar.jar!/com/pax/app/ui/adapters/CoachFragmentAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */