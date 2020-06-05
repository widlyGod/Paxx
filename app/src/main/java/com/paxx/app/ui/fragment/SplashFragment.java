package com.paxx.app.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paxx.app.MainApplication;
import com.paxx.app.R;
import com.paxx.app.ui.activity.CoachmarkActivity;
import com.paxx.app.ui.activity.DeviceSelectorActivity;
import com.paxx.app.ui.activity.GetStartedActivity;
import com.paxx.app.ui.util.PrefUtils;

/**
 * Created by hong on 2017/6/20.
 */

public class SplashFragment extends BaseFragment{



    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected View initLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, null);
    }

    @Override
    protected void iniComp(Bundle savedInstanceState) {

    }

    @Override
    protected void refreshView(Bundle savedInstanceState) {

    }

    @Override
    protected void restoreData(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {

    }

    public static final int GRADIENT_TRANSITION_DURATION = 2000;
    private Runnable animationEnd = new Runnable()
    {
        public void run()
        {
            if (SplashFragment.this.getView() != null)
            {
                SplashFragment.this.getView().setBackground(ContextCompat.getDrawable(MainApplication.getInstance(), R.drawable.splash_drawable_end));
                ((TransitionDrawable)SplashFragment.this.getView().getBackground()).startTransition(666);
            }
            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                    SplashFragment.this.goToNextScreen();
                }
            }, 666L);
        }
    };
    private Runnable animationStart = new Runnable()
    {
        public void run()
        {
            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                    SplashFragment.this.goToNextScreen();
                }
            }, 1500L);
        }
    };

    public static SplashFragment newInstance()
    {
        Bundle localBundle = new Bundle();
        SplashFragment localSplashFragment = new SplashFragment();
        localSplashFragment.setArguments(localBundle);
        return localSplashFragment;
    }

    public void goToNextScreen()
    {
//        boolean userGuide = PrefUtils.getBoolean(getActivity(), "is_user_guide_showed",
//                false);
//        if (!userGuide) {
//            // 跳转到新手引导页
//            Intent intent = new Intent(mContext, CoachmarkActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        } else {
            Intent intent = new Intent(mContext, DeviceSelectorActivity.class);
            startActivity(intent);
            getActivity().finish();
//        }

    }

    public void onViewCreated(View paramView, @Nullable Bundle paramBundle)
    {
        super.onViewCreated(paramView, paramBundle);
        this.animationStart.run();
    }

    public boolean shouldShowBluetoothDialog()
    {
        return false;
    }
}
