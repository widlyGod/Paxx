package com.paxx.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.paxx.app.R;
import com.paxx.app.ui.util.PaxTemperatureCircle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hong on 2017/6/22.
 */

public class DeviceAdapter extends RecyclerView.Adapter {

    public Context context;
    List<String> mList = new ArrayList<>();

    public DeviceAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.temperature_circle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private PaxTemperatureCircle drawable = new PaxTemperatureCircle();

        @Bind(R.id.unit)
        TextView unit;
        @Bind(R.id.textTemperature)
        TextView textTemperature;
        @Bind(R.id.temperature_bg)
        FrameLayout temperatureBg;


        public ViewHolder(View paramView) {
            super(paramView);
            ButterKnife.bind(this, paramView);
        }

        public void bind(int i) {
            this.drawable.setBorderWidth(5.0F);
            drawable.setBackgroundColor(getColorChanges(i));
            textTemperature.setText(mList.get(i));
            temperatureBg.setBackground(drawable);
        }

        private int getColorChanges(int cl1) {
            int R, G, B;
            // 颜色的渐变，应该把分别获取对应的三基色，然后分别进行求差值；这样颜色渐变效果最佳
//            R = Color.red(255/mList.size()*cl1);
//            G = Color.green((255-(255/mList.size()*cl1)));
            R = 255 / mList.size() * cl1;
            G = 255 - (255 / mList.size() * cl1);

            int cl = Color.rgb(R, G, 0);
            return cl;
        }
    }
}
