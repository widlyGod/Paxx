package com.paxx.app.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.paxx.app.MainApplication;
import com.paxx.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hong on 2017/6/23.
 */

public class DynamicModeAdapter extends RecyclerView.Adapter {

    public Context context;
    private boolean recyclerSettled = true;
    private boolean isable = false;

    private int focusedItemPosition = 0;
    AlphaAnimation localAlphaAnimation;

    public void setFocusedItemPosition(int paramInt) {
        this.focusedItemPosition = paramInt;
    }

    public DynamicModeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.dynamic_mode_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setRecyclerSettled(boolean paramBoolean) {
        this.recyclerSettled = paramBoolean;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.dynamicModeImageView)
        ImageView dynamicModeImageView;

        public ViewHolder(View paramView) {
            super(paramView);
            ButterKnife.bind(this, paramView);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bind(int position) {
            if (localAlphaAnimation == null) {
                localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
                localAlphaAnimation.setStartOffset(500L);
                localAlphaAnimation.setDuration(500L);
                localAlphaAnimation.setFillAfter(true);
            }
            dynamicModeImageView.clearAnimation();
            switch (position) {
                case 0:
                    dynamicModeImageView.setImageDrawable(ContextCompat.getDrawable(MainApplication.getInstance(), R.mipmap.dynamic_easy_white));
                    break;
                case 1:
                    dynamicModeImageView.setImageDrawable(ContextCompat.getDrawable(MainApplication.getInstance(), R.mipmap.dynamic_boost_white));
                    break;
                case 2:
                    dynamicModeImageView.setImageDrawable(ContextCompat.getDrawable(MainApplication.getInstance(), R.mipmap.dynamic_efficiency_white));
                    break;
                case 3:
                    dynamicModeImageView.setImageDrawable(ContextCompat.getDrawable(MainApplication.getInstance(), R.mipmap.dynamic_stealth_white));
                    break;
                case 4:
                    dynamicModeImageView.setImageDrawable(ContextCompat.getDrawable(MainApplication.getInstance(), R.mipmap.dynamic_flavor_white));
                    break;
            }
//            if (position == focusedItemPosition) {
//                dynamicModeImageView.setAlpha(1.0F);
//            } else {
//                dynamicModeImageView.setAlpha(0.4F);
//                if (recyclerSettled) {
//                    dynamicModeImageView.startAnimation(localAlphaAnimation);
//                }
//            }

        }

    }
}
