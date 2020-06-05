package com.paxx.app.ui.bean;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;

/**
 * Created by hong on 2017/6/21.
 */

public class PeripheralDevice implements Serializable {

    BluetoothDevice device;
    int ConnectionStatus = 0;
    int color = 0;

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public int getConnectionStatus() {
        return ConnectionStatus;
    }

    public void setConnectionStatus(int connectionStatus) {
        ConnectionStatus = connectionStatus;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
