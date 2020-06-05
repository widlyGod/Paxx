package com.paxx.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.paxx.app.R;
import com.paxx.app.ui.adapter.CoachFragmentAdapter;
import com.paxx.app.ui.adapter.MyFragmentPagerAdapter;
import com.paxx.app.ui.fragment.CoachmarkItemFragment;
import com.paxx.app.ui.util.EvangalistItem;
import com.paxx.app.utils.CircleIndicator;
import com.paxx.app.utils.PageManager;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * Created by hong on 2017/6/20.
 */

public class CoachmarkActivity extends BaseActivity {


    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    private CoachFragmentAdapter mAdapter;

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.activity_coachmarks;
    }

    @Override
    protected void initComp(Bundle savedInstanceState) {

//        setContentView(mRootView);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        View mRootView = getLayoutInflater().inflate(R.layout.activity_coachmarks, null);
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new EvangalistItem(R.string.header1, R.string.description1, R.mipmap.evangelize1));
        localArrayList.add(new EvangalistItem(R.string.header2, R.string.description2, R.mipmap.evangelize2));
        localArrayList.add(new EvangalistItem(R.string.header3, R.string.description3, R.mipmap.evangelize3));
        localArrayList.add(new EvangalistItem(R.string.header4, R.string.description4, R.mipmap.evangelize4));
//
        this.mAdapter = new CoachFragmentAdapter(getSupportFragmentManager(), localArrayList);
        pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            public void transformPage(View paramAnonymousView, float paramAnonymousFloat) {
                if ((paramAnonymousFloat <= -1.0F) || (paramAnonymousFloat >= 1.0F)) {
                    paramAnonymousView.setTranslationX(paramAnonymousFloat * paramAnonymousView.getWidth());
                    paramAnonymousView.setAlpha(0.0F);
                    return;
                }
                if (paramAnonymousFloat == 0.0F) {
                    paramAnonymousView.setTranslationX(paramAnonymousFloat * paramAnonymousView.getWidth());
                    paramAnonymousView.setAlpha(1.0F);
                    return;
                }
                paramAnonymousView.setTranslationX(paramAnonymousView.getWidth() * -paramAnonymousFloat);
                paramAnonymousView.setAlpha(1.0F - Math.abs(paramAnonymousFloat));
            }
        });
        pager.setAdapter(this.mAdapter);
        this.indicator.setViewPager(this.pager);
    }
}
