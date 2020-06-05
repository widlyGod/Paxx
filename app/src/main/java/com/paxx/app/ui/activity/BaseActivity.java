package com.paxx.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import butterknife.ButterKnife;

/**
 * Created by hong on 2017/6/20.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BaseFragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /** init views */
        int ViewId = initLayout(savedInstanceState);
        if (ViewId > 0) {
            setContentView(ViewId);
            ButterKnife.bind(this);
            initComp(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreData(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        receiveData(data);
    }

    /**
     * 初始化组件
     * @param savedInstanceState
     */
    protected abstract int initLayout(Bundle savedInstanceState);

    /**
     * 初始化组件
     *
     * @param savedInstanceState
     */
    protected abstract void initComp(Bundle savedInstanceState);

    /**
     * 初始化数据 onCreate()
     *
     * @param intent
     */
    protected abstract void initData(Intent intent);

    /**
     * 接收Activity传递的数据 onActivityResult()
     *
     * @param intent
     */
    protected abstract void receiveData(Intent intent);

    /**
     * 恢复数据 onResume()
     *
     * @param intent
     */
    protected abstract void restoreData(Intent intent);


}