package com.paxx.app.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paxx.app.R;
import com.paxx.app.ui.bean.PeripheralDevice;
import com.paxx.app.ui.util.BluetoothLeService;
import com.paxx.app.ui.util.HexUtil;
import com.paxx.app.ui.util.ShareprefUtils;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hong on 2017/6/21.
 */

public class DeviceSelectorActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.bg_layout)
    ImageView bgLayout;
    @Bind(R.id.peripheral_image_view)
    ImageView peripheralImageView;
    @Bind(R.id.device_type)
    TextView deviceType;
    @Bind(R.id.device_selector_bg)
    RelativeLayout deviceSelectorBg;

    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000;
    //    private ArrayList<PeripheralDevice> mLeDevices = new ArrayList<>();
//    private ArrayList<BluetoothDevice> mDevices = new ArrayList<>();
    private BluetoothDevice peripheralDevice;
//    int choose = 0;
//    int position = 0;

    Intent gattServiceIntent;
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;

    private BluetoothGattCharacteristic readCharacteristic;
    private BluetoothGattService readMnotyGattService;
    boolean isFirst = true;

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.base_toolbar_view;
    }

    @Override
    protected void initComp(Bundle savedInstanceState) {
        gattServiceIntent = new Intent(this, BluetoothLeService.class);
        mHandler = new Handler();
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled() && mBluetoothAdapter != null) {
            AlertDialog dialog = null;
            if (dialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Bluetooth is disabled.")
                        .setMessage("Bluetooth must be enabled to use your AOX3.")
                        .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1);
                            }
                        })
                        .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Snackbar.make(drawerLayout, "Bluetooth must be enabled to use your AOX3.", Snackbar.LENGTH_LONG).show();
                            }
                        });

                dialog = builder.create();
            }
            dialog.show();
        }
//        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
//        View localView = getLayoutInflater().inflate(R.layout.home_menu, navView, false);
//        navView.addView(localView);

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (Iterator iterator = devices.iterator(); iterator.hasNext(); ) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                //得到远程已配对蓝牙设备的mac地址
                if (peripheralDevice != null && !bluetoothDevice.getAddress().equals(peripheralDevice.getAddress()) && "PAX3".equals(bluetoothDevice.getName())) {
                    peripheralDevice = bluetoothDevice;
                }
            }
        }
//        CarouselLayoutManager temperatureLayoutManager = new CarouselLayoutManager(0);
//        LinearLayoutManager dynamicModeLayoutManager = new LinearLayoutManager(this, 0, false);
//        Point localPoint = new Point();
//        getWindowManager().getDefaultDisplay().getSize(localPoint);
//        int i = (int) ((localPoint.x) / 2 - this.getResources().getDimension(R.dimen.peripheral_icon_size) / 2.0F);
//        float f = getResources().getDimension(R.dimen.peripheral_icon_size) / 2.0F - 240;
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerview.setLayoutManager(manager);
//        peripheralAdapter = new PeripheralAdapter(DeviceSelectorActivity.this, mLeDevices);
//        recyclerview.setAdapter(peripheralAdapter);
//        recyclerview.addItemDecoration(new DV_HorizontalSpaceItemDecoration(45));
//        recyclerview.setPadding(i, 0, i + (int) f, 0);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerview);
//        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == 0) {
////                    Log.e("1231312311", position + "");
//                    int position = ((manager.findFirstVisibleItemPosition() + manager.findLastVisibleItemPosition()) / 2);
//                    Log.e("11111111", "onScrollStateChanged: " + position);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//            }
//        });
//        goLeftIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (position > 0) {
//                    position--;
//                    recyclerview.smoothScrollToPosition(position);
//                }
//            }
//        });
//        goRightIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (position < mLeDevices.size() - 1) {
//                    position++;
//                    recyclerview.smoothScrollToPosition(position);
//                }
//            }
//        });
//        new LinearSnapHelper().attachToRecyclerView(recyclerview);
        deviceSelectorBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (peripheralDevice != null) {
                    Intent intent = new Intent(DeviceSelectorActivity.this, DeviceControlActivity.class);
                    intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, peripheralDevice.getName());
                    intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, peripheralDevice.getAddress());
                    startActivity(intent);
                }
            }
        });
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("PAX3".equals(device.getName())) {
                                if (peripheralDevice == null || !peripheralDevice.getAddress().equals(device.getAddress())) {
                                    peripheralDevice = device;
                                    String color = ShareprefUtils.getString(DeviceSelectorActivity.this, device.getAddress(), "");
                                    if (!TextUtils.isEmpty(color)) {
                                        switch (color) {
                                            case "SR":
                                                peripheralImageView.setImageDrawable(getResources().getDrawable((R.mipmap.aox_gray)));
                                                break;
                                            case "GD":
                                                peripheralImageView.setImageDrawable(getResources().getDrawable((R.mipmap.aox_yellow)));
                                                break;
                                            case "RG":
                                                peripheralImageView.setImageDrawable(getResources().getDrawable((R.mipmap.aox_pink)));
                                                break;
                                            default:
                                                peripheralImageView.setImageDrawable(getResources().getDrawable((R.mipmap.aox_black)));
                                                break;
                                        }
                                    }

                                    deviceType.setText("CONNECTING");

                                    if (TextUtils.isEmpty(mDeviceAddress) && TextUtils.isEmpty(color)) {
                                        mDeviceAddress = device.getAddress();
                                        mBluetoothLeService.connect(mDeviceAddress);
                                    }
//                                    peripheralAdapter.notifyDataSetChanged();

                                }
                            }
//                            peripheralAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };

    // 管理服务的生命周期
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Toast.makeText(DeviceSelectorActivity.this, "Unable to initialize Bluetooth", Toast.LENGTH_SHORT).show();
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
//            if (!TextUtils.isEmpty(mDeviceAddress)) {
//                mBluetoothLeService.connect(mDeviceAddress);
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                try {
                    //写数据的服务和characteristic
                    readMnotyGattService = mBluetoothLeService.getSupportedGattServices(UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb"));
                    readCharacteristic = readMnotyGattService.getCharacteristic(UUID.fromString("00002a25-0000-1000-8000-00805f9b34fb"));
                    mBluetoothLeService.readCharacteristic(readCharacteristic);

                } catch (Exception e) {
                    finish();
                    Log.e("22222", "22222");
                }
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //将数据显示在mDataField上
                byte[] data = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                Log.e("1111", "onReceive: " + asciiToString(data[0] + "") + "---" + data[1] + "====" + HexUtil.encodeHexStr(data));

                String color = "";
                if (data.length >= 2) {
                    color = asciiToString(data[0] + "") + asciiToString(data[1] + "");
                }

                if (mDeviceAddress != null && mDeviceAddress.equals(peripheralDevice.getAddress())) {
                    switch (color) {
                        case "SR":
                            peripheralImageView.setImageDrawable(getResources().getDrawable((R.mipmap.aox_gray)));
                            break;
                        case "GD":
                            peripheralImageView.setImageDrawable(getResources().getDrawable((R.mipmap.aox_yellow)));
                            break;
                        case "RG":
                            peripheralImageView.setImageDrawable(getResources().getDrawable((R.mipmap.aox_pink)));
                            break;
                        default:
                            peripheralImageView.setImageDrawable(getResources().getDrawable((R.mipmap.aox_black)));
                            break;
                    }

                    ShareprefUtils.saveString(DeviceSelectorActivity.this, mDeviceAddress, color);

                }
            }
        }
    };

    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Initializes list view adapter.
        scanLeDevice(true);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
        unregisterReceiver(mGattUpdateReceiver);
        unbindService(mServiceConnection);
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
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothLeService = null;

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_NOTIFY);
        return intentFilter;
    }

}
