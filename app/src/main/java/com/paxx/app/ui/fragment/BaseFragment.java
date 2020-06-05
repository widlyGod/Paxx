package com.paxx.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * CreatedTrip by zhs on 15/9/1.
 * <p/>
 * 所有Fragment基类
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ButKnifeFragment";
    /**
     * 根View
     */
    protected View mRootView;
    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(getArguments());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = initLayout(inflater, container, savedInstanceState);
            ButterKnife.bind(this, mRootView);
            iniComp(savedInstanceState);
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshView(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        restoreData(getArguments());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 初始化数据 onCreate()
     *
     * @param bundle
     */
    protected abstract void initData(Bundle bundle);

    /**
     * 初始化RootView onCreateView()
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View initLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化组件
     *
     * @param savedInstanceState
     */
    protected abstract void iniComp(Bundle savedInstanceState);

    /**
     * 刷新View onViewCreated()
     *
     * @param savedInstanceState
     */
    protected abstract void refreshView(Bundle savedInstanceState);

    /**
     * 恢复数据 onResume()
     *
     * @param bundle
     */
    protected abstract void restoreData(Bundle bundle);

}
