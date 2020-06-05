package com.paxx.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paxx.app.R;
import com.paxx.app.ui.activity.DeviceControlActivity;
import com.paxx.app.ui.bean.PeripheralDevice;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hong on 2017/6/21.
 */

public class PeripheralAdapter extends RecyclerView.Adapter {

    public Context context;

    private ArrayList<PeripheralDevice> mLeDevices;

    public PeripheralAdapter(Context context, ArrayList<PeripheralDevice> mLeDevices) {
        this.context = context;
        this.mLeDevices = mLeDevices;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.device_selector_peripheral_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(mLeDevices.get(position));
    }

    @Override
    public int getItemCount() {
        return mLeDevices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.peripheral_image_view)
        ImageView peripheralImageView;
        @Bind(R.id.peripheral_title)
        TextView peripheralTitle;
        @Bind(R.id.peripheral_status)
        TextView peripheralStatus;
        @Bind(R.id.peripheral)
        RelativeLayout peripheral;


        public ViewHolder(View paramView) {
            super(paramView);
            ButterKnife.bind(this, paramView);
        }

        public void bind(final PeripheralDevice device) {
            peripheralTitle.setText(device.getDevice().getName());
            String status = "Connected";
            switch (device.getConnectionStatus()) {
                case 1:
                    status = "Connected";
                    break;
                case 0:
                    status = "Disconnected";
                    break;
                case 2:
                    status = "Connecting";
                    break;
            }
            peripheralStatus.setText(status);
            Log.e("11122", "bind: " + device.getDevice().getUuids());
            peripheral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (device.getConnectionStatus() == 1) {
                        Intent intent = new Intent(context, DeviceControlActivity.class);
                        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getDevice().getName());
                        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getDevice().getAddress());
                        context.startActivity(intent);
                    }
                }
            });
            switch (device.getColor()){
                case 0:
                    peripheralImageView.setImageDrawable(context.getResources().getDrawable((R.mipmap.aox_black)));
                    break;
                case 1:
                    peripheralImageView.setImageDrawable(context.getResources().getDrawable((R.mipmap.aox_gray)));
                    break;
                case 2:
                    peripheralImageView.setImageDrawable(context.getResources().getDrawable((R.mipmap.aox_yellow)));
                    break;
                case 3:
                    peripheralImageView.setImageDrawable(context.getResources().getDrawable((R.mipmap.aox_pink)));
                    break;
            }
        }
    }
}
