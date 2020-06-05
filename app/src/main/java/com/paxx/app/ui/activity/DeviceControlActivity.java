package com.paxx.app.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paxx.app.R;
import com.paxx.app.ui.adapter.DeviceAdapter;
import com.paxx.app.ui.adapter.DynamicModeAdapter;
import com.paxx.app.ui.util.AnimatedCircle;
import com.paxx.app.ui.util.BluetoothLeService;
import com.paxx.app.ui.util.DV_HorizontalSpaceItemDecoration;
import com.paxx.app.ui.util.ImageViewPlus;
import com.paxx.app.ui.util.PrefUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hong on 2017/6/22.
 */

public class DeviceControlActivity extends BaseActivity {

    //    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.colorThemeHeader)
    TextView colorThemeHeader;
    @Bind(R.id.colorThemeLayout)
    RadioGroup colorThemeLayout;
    @Bind(R.id.tv_brightness)
    TextView tvBrightness;
    @Bind(R.id.seekBar_brightness)
    SeekBar seekBarBrightness;
    @Bind(R.id.gameHeaderText)
    TextView gameHeaderText;
    @Bind(R.id.tv_removeDevice)
    TextView tvRemoveDevice;
    @Bind(R.id.hapticsHeader)
    TextView hapticsHeader;

    @Bind(R.id.lockDeviceHeader)
    TextView lockDeviceHeader;
    @Bind(R.id.game1)
    ImageView game1;
    @Bind(R.id.game2)
    ImageView game2;
    @Bind(R.id.game3)
    ImageView game3;
    @Bind(R.id.gameLayout)
    LinearLayout gameLayout;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private final static String TAG = DeviceControlActivity.class.getSimpleName();
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    @Bind(R.id.theme_radio_button1)
    RadioButton themeRadioButton1;
    @Bind(R.id.theme_radio_button2)
    RadioButton themeRadioButton2;
    @Bind(R.id.theme_radio_button3)
    RadioButton themeRadioButton3;
    @Bind(R.id.theme_radio_button4)
    RadioButton themeRadioButton4;
    @Bind(R.id.currentHeaterTemp)
    TextView currentHeaterTemp;
    @Bind(R.id.dynamicModeRecyclerView)
    RecyclerView dynamicModeRecyclerView;
    @Bind(R.id.batteryImage)
    ImageView batteryImage;
    @Bind(R.id.circleRecyclerView)
    RecyclerView circleRecyclerView;
    @Bind(R.id.temperatureRing)
    AnimatedCircle temperatureRing;
    @Bind(R.id.temperatureControlContainer)
    FrameLayout temperatureControlContainer;
    @Bind(R.id.deviceLayout)
    RelativeLayout deviceLayout;
    @Bind(R.id.modify_serial)
    Button modifySerial;
    @Bind(R.id.haptics1)
    TextView haptics1;
    @Bind(R.id.haptics2)
    TextView haptics2;
    @Bind(R.id.haptics3)
    TextView haptics3;
    @Bind(R.id.butHapticsLayout)
    LinearLayout butHapticsLayout;
    @Bind(R.id.seekbar_left_tv)
    TextView seekbarLeftTv;
    @Bind(R.id.seekbar_right_tv)
    TextView seekbarRightTv;
    @Bind(R.id.seekBar_brightness_rl)
    RelativeLayout seekBarBrightnessRl;
    @Bind(R.id.lockDevice1)
    TextView lockDevice1;
    @Bind(R.id.lockDevice2)
    TextView lockDevice2;
    @Bind(R.id.lockDevice_ll)
    LinearLayout lockDeviceLl;
    @Bind(R.id.setting_back_iv)
    ImageView settingBackIv;
    @Bind(R.id.temperature_num_tv)
    TextView temperatureNumTv;
    @Bind(R.id.temperature_type_tv)
    TextView temperatureTypeTv;
    @Bind(R.id.temperature_kind_ll)
    LinearLayout temperatureKindLl;
    @Bind(R.id.temperature_num_rl)
    RelativeLayout temperatureNumRl;
    @Bind(R.id.temperature_up_iv)
    ImageView temperatureUpIv;
    @Bind(R.id.temperature_down_iv)
    ImageView temperatureDownIv;
    @Bind(R.id.temperature_change_ll)
    LinearLayout temperatureChangeLl;
    @Bind(R.id.control_back_iv)
    ImageView controlBackIv;
    @Bind(R.id.control_setting_iv)
    ImageView controlSettingIv;
    @Bind(R.id.setting_view)
    ScrollView settingView;
    @Bind(R.id.temperatureBg)
    ImageViewPlus temperatureBg;


    //连接状态
    private String mDeviceName;
    private String mDeviceAddress;
    private int light = 0;
    private BluetoothLeService mBluetoothLeService;
    int dynamicMode = 0;
    private Handler dynamicHandler = new Handler();

    boolean isFahrenheit = true;

    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    DynamicModeAdapter dynamicModeAdapter;

    protected ProgressDialog waitDialog;

    //写数据
    private BluetoothGattCharacteristic characteristic;
    private BluetoothGattService mnotyGattService;
    ;
    //读数据
    private BluetoothGattCharacteristic readCharacteristic;
    private BluetoothGattService readMnotyGattService;
    //监听数据
    private BluetoothGattCharacteristic notifyCharacteristic;
    private BluetoothGattService notifyMnotyGattService;
    private ActionBarDrawerToggle mDrawerToggle;

    List<String> temperature = new ArrayList<>();

    int haptics = 0;

    DeviceAdapter deviceAdapter;

    private CountDownTimer countDownTimer = new CountDownTimer(5000, 5000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            countDownTimer.start();
            if (mBluetoothLeService != null && readCharacteristic != null) {
                mBluetoothLeService.readCharacteristic(readCharacteristic);
            }
        }
    };

    // 管理服务的生命周期
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                Toast.makeText(DeviceControlActivity.this, "Unable to initialize Bluetooth", Toast.LENGTH_SHORT).show();
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    @OnClick({R.id.tv_removeDevice})
    public void tv_removeDevice() {
        finish();
    }

    public static int char2ASCII(char c) {
        return (int) c;
    }

    public static int[] string2ASCII(String s) {// 字符串转换为ASCII码
        if (s == null || "".equals(s)) {
            return null;
        }

        char[] chars = s.toCharArray();
        int[] asciiArray = new int[chars.length];

        for (int i = 0; i < chars.length; i++) {
            asciiArray[i] = char2ASCII(chars[i]);
        }
        return asciiArray;
    }

    @OnClick({R.id.modify_serial})
    public void modifySerial() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_modify_serial, null);
        final EditText editText1 = (EditText) view.findViewById(R.id.modify_serial_1);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("请输入序列号")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(view)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.setCancelable(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.getText().toString().trim().length() != 8) {
                    Toast.makeText(DeviceControlActivity.this, "请输入8位序列号", Toast.LENGTH_SHORT).show();
                } else {
                    int seria1 = string2ASCII(editText1.getText().toString().trim().substring(0, 1))[0];
                    int seria2 = string2ASCII(editText1.getText().toString().trim().substring(1, 2))[0];
                    int seria3 = string2ASCII(editText1.getText().toString().trim().substring(2, 3))[0];
                    int seria4 = string2ASCII(editText1.getText().toString().trim().substring(3, 4))[0];
                    int seria5 = string2ASCII(editText1.getText().toString().trim().substring(4, 5))[0];
                    int seria6 = string2ASCII(editText1.getText().toString().trim().substring(5, 6))[0];
                    int seria7 = string2ASCII(editText1.getText().toString().trim().substring(6, 7))[0];
                    int seria8 = string2ASCII(editText1.getText().toString().trim().substring(7, 8))[0];

                    final int charaProp = characteristic.getProperties();
                    //如果该char可写
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                        if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        //读取数据，数据将在回调函数中
                        //mBluetoothLeService.readCharacteristic(characteristic);
                        byte[] value = new byte[20];
                        value[0] = (byte) 0x00;
                        byte[] WriteBytes;

                        WriteBytes = new byte[]{0X5B, 0x09, (byte) seria1, (byte) seria2, (byte) seria3, (byte) seria4, (byte) seria5, (byte) seria6, (byte) seria7, (byte) seria8, 0x00};

                        characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                        characteristic.setValue(WriteBytes);
                        mBluetoothLeService.writeCharacteristic(characteristic);
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                    }
                    dialog.cancel();
                }
            }
        });
    }


    @OnClick({R.id.lockDevice1, R.id.lockDevice2})
    public void lockDevice(View paramVie) {
        switch (paramVie.getId()) {
            case R.id.lockDevice1:
                lockDevice1.setSelected(true);
                lockDevice2.setSelected(false);
                break;
            case R.id.lockDevice2:
                lockDevice1.setSelected(false);
                lockDevice2.setSelected(true);
        }
    }

    @OnClick({R.id.haptics1, R.id.haptics2, R.id.haptics3})
    public void haptics(View paramVie) {
        final int charaProp = characteristic.getProperties();

        //如果该char可写
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            // If there is an active notification on a characteristic, clear
            // it first so it doesn't update the data field on the user interface.
            if (mNotifyCharacteristic != null) {
                mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                mNotifyCharacteristic = null;
            }
            //读取数据，数据将在回调函数中
            //mBluetoothLeService.readCharacteristic(characteristic);
            byte[] value = new byte[20];
            value[0] = (byte) 0x00;
            byte[] WriteBytes;
            switch (paramVie.getId()) {
                case R.id.haptics1:
                    WriteBytes = new byte[]{0x5B, 0X06, (byte) 0X00, 0x5B};
                    haptics = 1;
                    haptics1.setSelected(true);
                    haptics2.setSelected(false);
                    haptics3.setSelected(false);
                    haptics1.setClickable(false);
                    haptics2.setClickable(true);
                    haptics3.setClickable(true);
                    break;
                case R.id.haptics2:
                    WriteBytes = new byte[]{0x5B, 0X06, (byte) 0X01, 0x5B};
                    haptics = 2;
                    haptics1.setSelected(false);
                    haptics2.setSelected(true);
                    haptics3.setSelected(false);
                    haptics1.setClickable(true);
                    haptics2.setClickable(false);
                    haptics3.setClickable(true);
                    break;
                default:
                    WriteBytes = new byte[]{0x5B, 0X06, (byte) 0X02, 0x5B};
                    haptics = 0;
                    haptics1.setSelected(false);
                    haptics2.setSelected(false);
                    haptics3.setSelected(true);
                    haptics1.setClickable(true);
                    haptics2.setClickable(true);
                    haptics3.setClickable(false);
                    break;
            }


            characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
            characteristic.setValue(WriteBytes);
            mBluetoothLeService.writeCharacteristic(characteristic);
        }
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            mNotifyCharacteristic = characteristic;
            mBluetoothLeService.setCharacteristicNotification(characteristic, true);
        }

    }

    @OnClick({R.id.theme_radio_button1, R.id.theme_radio_button2, R.id.theme_radio_button3, R.id.theme_radio_button4})
    public void setTheme(View paramView) {
        final int charaProp = characteristic.getProperties();

        //如果该char可写
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            // If there is an active notification on a characteristic, clear
            // it first so it doesn't update the data field on the user interface.
            if (mNotifyCharacteristic != null) {
                mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                mNotifyCharacteristic = null;
            }
            //读取数据，数据将在回调函数中
            //mBluetoothLeService.readCharacteristic(characteristic);
            byte[] value = new byte[20];
            value[0] = (byte) 0x00;
            byte[] WriteBytes;
            switch (paramView.getId()) {
                case R.id.theme_radio_button1:
                    WriteBytes = new byte[]{0x5B, 0X03, (byte) 0x00, 0x5B};
//                    colorThemeHeader.setText(String.format(getString(R.string.color_theme_mode), "Default"));
                    break;
                case R.id.theme_radio_button2:
                    WriteBytes = new byte[]{0x5B, 0X03, (byte) 0x01, 0x5B};
//                    colorThemeHeader.setText(String.format(getString(R.string.color_theme_mode), "Ocean"));
                    break;
                case R.id.theme_radio_button3:
                    WriteBytes = new byte[]{0x5B, 0X03, (byte) 0x02, 0x5B};
//                    colorThemeHeader.setText(String.format(getString(R.string.color_theme_mode), "Sunset"));
                    break;
                default:
                    WriteBytes = new byte[]{0x5B, 0X03, (byte) 0x03, 0x5B};
//                    colorThemeHeader.setText(String.format(getString(R.string.color_theme_mode), "Mars"));
                    break;
            }

            characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
            characteristic.setValue(WriteBytes);
            mBluetoothLeService.writeCharacteristic(characteristic);
        }
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            mNotifyCharacteristic = characteristic;
            mBluetoothLeService.setCharacteristicNotification(characteristic, true);
        }
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
//                Toast.makeText(DeviceControlActivity.this, "Unable", Toast.LENGTH_SHORT).show();
                setLoading(false);
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                Toast.makeText(DeviceControlActivity.this, "Unable to read Service Changed CCCD: device disconnected", Toast.LENGTH_SHORT).show();
                finish();
                Log.e("11111", "11111");
            }
            //发现有可支持的服务
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                try {
                    //写数据的服务和characteristic
                    mnotyGattService = mBluetoothLeService.getSupportedGattServices(UUID.fromString("233FEFF0-3C24-10CC-A1A1-78E4000C659C"));
                    characteristic = mnotyGattService.getCharacteristic(UUID.fromString("233FEFF2-3C24-10CC-A1A1-78E4000C659C"));
                    //读数据的服务和characteristic
                    readMnotyGattService = mBluetoothLeService.getSupportedGattServices(UUID.fromString("233FEFF0-3C24-10CC-A1A1-78E4000C659C"));
                    readCharacteristic = readMnotyGattService.getCharacteristic(UUID.fromString("233FEFF1-3C24-10CC-A1A1-78E4000C659C"));
                    mBluetoothLeService.readCharacteristic(readCharacteristic);

                } catch (Exception e) {
                    finish();
                    Log.e("22222", "22222");
                }
            }
            //显示数据
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //将数据显示在mDataField上
                byte[] data = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                if (data.length <= 0) {
                    return;
                }
                switch (data[3]) {
                    case 0x00:
//                        colorThemeHeader.setText(String.format(getString(R.string.color_theme_mode), "Default"));
                        themeRadioButton1.setChecked(true);
                        break;
                    case 0x01:
//                        colorThemeHeader.setText(String.format(getString(R.string.color_theme_mode), "Ocean"));
                        themeRadioButton2.setChecked(true);
                        break;
                    case 0x02:
//                        colorThemeHeader.setText(String.format(getString(R.string.color_theme_mode), "Sunset"));
                        themeRadioButton3.setChecked(true);
                        break;
                    case 0x03:
//                        colorThemeHeader.setText(String.format(getString(R.string.color_theme_mode), "Mars"));
                        themeRadioButton4.setChecked(true);
                        break;
                }
                switch (data[6]) {
                    case 0x00:
                        haptics = 1;
                        haptics1.setSelected(true);
                        haptics2.setSelected(false);
                        haptics3.setSelected(false);
                        haptics1.setClickable(false);
                        haptics2.setClickable(true);
                        haptics3.setClickable(true);
                        break;
                    case 0x01:
                        haptics = 2;
                        haptics1.setSelected(false);
                        haptics2.setSelected(true);
                        haptics3.setSelected(false);
                        haptics1.setClickable(true);
                        haptics2.setClickable(false);
                        haptics3.setClickable(true);
                        break;
                    case 0x02:
                        haptics = 0;
                        haptics1.setSelected(false);
                        haptics2.setSelected(false);
                        haptics3.setSelected(true);
                        haptics1.setClickable(true);
                        haptics2.setClickable(true);
                        haptics3.setClickable(false);
                        break;
                }
                switch (data[5]) {
                    case 0x00:
                        break;
                    case 0x01:
                        game1.setSelected(true);
                        game2.setSelected(false);
                        game3.setSelected(false);
                        globalValue.setCheck1(true);
                        globalValue.setCheck2(false);
                        globalValue.setCheck3(false);
                        break;
                    case 0x02:
                        game1.setSelected(false);
                        game2.setSelected(true);
                        game3.setSelected(false);
                        globalValue.setCheck1(false);
                        globalValue.setCheck2(true);
                        globalValue.setCheck3(false);
                        break;
                    case 0x03:
                        game1.setSelected(false);
                        game2.setSelected(false);
                        game3.setSelected(true);
                        globalValue.setCheck1(false);
                        globalValue.setCheck2(false);
                        globalValue.setCheck3(true);
                        break;
                }
//                seekBarBrightness.setProgress((data[4] & 0xff));
//                tvBrightness.setText("BRIGHTNESS-" + ((data[4] & 0xff) * 100 / 254) + "%");
                light = (int) PrefUtils.getLong(DeviceControlActivity.this,
                        "light", 254);
                seekBarBrightness.setProgress(light);
//                tvBrightness.setText("BRIGHTNESS-" + (light * 100 / 254) + "%");
                final int charaProp = characteristic.getProperties();
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    // If there is an active notification on a characteristic, clear
                    // it first so it doesn't update the data field on the user interface.
                    if (mNotifyCharacteristic != null) {
                        mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                        mNotifyCharacteristic = null;
                    }
                    //读取数据，数据将在回调函数中
                    //mBluetoothLeService.readCharacteristic(characteristic);
                    byte[] value = new byte[20];
                    value[0] = (byte) 0x00;
                    byte[] WriteBytes;
                    WriteBytes = new byte[]{0x5B, 0X04, (byte) light, 0x5B};
                    PrefUtils.setLong(DeviceControlActivity.this,
                            "light", light);

                    characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                    characteristic.setValue(WriteBytes);
                    mBluetoothLeService.writeCharacteristic(characteristic);
                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    mNotifyCharacteristic = characteristic;
                    mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                }
                float TempH = (data[0] & 0xff) * 256;
                float TempL = (data[1] & 0xff);
                DecimalFormat fnum = new DecimalFormat("##0");
                String dd = fnum.format((int) ((TempH + TempL) / 10 * 1.8 + 32));
                currentHeaterTemp.setVisibility(View.VISIBLE);
//                currentHeaterTemp.setTextColor(0xFF00cc00);
                if (isFahrenheit) {
                    currentHeaterTemp.setText(dd);
                } else {
                    currentHeaterTemp.setText((Integer.parseInt(dd) - 32) * 5 / 9 + "");
                }
                temperatureRing.setVisibility(View.VISIBLE);
                temperatureRing.setColor(0xFF64E4FB);
                temperatureRing.setPercentFull((float) (((TempH + TempL) / 10 * 1.8 + 32) / (temperaturePosition * 5 + 360)));
                String ss = Integer.toHexString(data[0]);
                String s2 = Integer.toHexString(data[1]);
                Log.e("11223", data[0] + "--" + data[1] + "--" + data[2] + "--" + data[3] + "--" + (data[4] & 0xff) + "--" + data[5] + "--" + data[6] + "--" + data[7]);
                Log.e("133322", ss + "------" + s2 + "----" + data[1] + "______" + data[0]);
                position = (int) PrefUtils.getLong(DeviceControlActivity.this, "temperature", 0);
                setTemperature(position);
//                countDownTimer.start();
                notifyMnotyGattService = mBluetoothLeService.getSupportedGattServices(UUID.fromString("233FEFF0-3C24-10CC-A1A1-78E4000C659C"));
                notifyCharacteristic = notifyMnotyGattService.getCharacteristic(UUID.fromString("233FEFF3-3C24-10CC-A1A1-78E4000C659C"));
                mBluetoothLeService.setCharacteristicNotification(notifyCharacteristic, true);
            } else {
                byte[] data = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                Log.e("112321", (data[0] & 0xff) + "-" + (data[1] & 0xff) + "-" + (data[2] & 0xff) + "-" + (data[3] & 0xff) + "-" + (data[4] & 0xff) + "-");
                float TempH = (data[1] & 0xff) * 256;
                float TempL = (data[2] & 0xff);
                DecimalFormat fnum = new DecimalFormat("##0");
                String dd = fnum.format((int) ((TempH + TempL) / 10 * 1.8 + 32));
                currentHeaterTemp.setVisibility(View.VISIBLE);
//                currentHeaterTemp.setTextColor(0xFF00cc00);
                if (isFahrenheit) {
                    currentHeaterTemp.setText(dd);
                } else {
                    currentHeaterTemp.setText((Integer.parseInt(dd) - 32) * 5 / 9 + "");
                }
                temperatureRing.setVisibility(View.VISIBLE);
                temperatureRing.setColor(0xFF64E4FB);
                temperatureRing.setPercentFull((float) (((TempH + TempL) / 10 * 1.8 + 32) / (temperaturePosition * 5 + 360)));
                String ss = Integer.toBinaryString(data[1]);
                String s2 = Integer.toBinaryString(data[2]);
//                String s3 = Integer.toHexString(data[3]);
//                String s4 = Integer.toHexString(data[4]);
                Log.e("1333222", ss + "------" + s2 + "----【" + data[1] + "______" + data[2]);
                float BatADH = (data[3] & 0xff) * 256;
                float BatADL = (data[4] & 0xff);
                float BatAD = ((BatADH + BatADL) * 3) / 512;
                int BatLevel = 0;
                if (BatAD > 3.95) {
                    BatLevel = 4;
                } else if (BatAD > 3.85) {
                    BatLevel = 3;
                } else if (BatAD > 3.75) {
                    BatLevel = 2;
                } else if (BatAD > 3.6) {
                    BatLevel = 1;
                } else {
                    BatLevel = 0;
                }
                batteryImage.setImageLevel(BatLevel);

            }
        }
    };

    final GlobalValue globalValue = new GlobalValue();


    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.base_toolbar_view_device_control;
    }

    private void initTemperatureList() {
        temperature.add("360");
        temperature.add("365");
//        temperature.add("370");
        temperature.add("375");
//        temperature.add("380");
        temperature.add("385");
//        temperature.add("390");
        temperature.add("395");
        temperature.add("400");
//        temperature.add("405");
        temperature.add("410");
//        temperature.add("415");
        temperature.add("420");
        temperature.add("425");
    }

    int position = 0;
    int temperaturePosition = 0;

    private void setTemperature(int position) {
        if (temperaturePosition != position && characteristic != null) {
            temperaturePosition = position;
            temperatureBg.setColor(position);
            PrefUtils.setLong(this, "temperature", position);
            int fahrenheit = 0;
//            int fahrenheit = 5 * position + 360;
            switch (position) {
                case 0:
                    fahrenheit = 360;
                    break;
                case 1:
                    fahrenheit = 365;
                    break;
                case 2:
                    fahrenheit = 375;
                    break;
                case 3:
                    fahrenheit = 385;
                    break;
                case 4:
                    fahrenheit = 395;
                    break;
                case 5:
                    fahrenheit = 400;
                    break;
                case 6:
                    fahrenheit = 410;
                    break;
                case 7:
                    fahrenheit = 420;
                    break;
                case 8:
                    fahrenheit = 425;
                    break;
            }
            if (isFahrenheit) {
                temperatureNumTv.setText(fahrenheit + "");
            } else {
                temperatureNumTv.setText((fahrenheit - 32) * 5 / 9 + "");
            }
            DecimalFormat fnum = new DecimalFormat(".00");
            float centigrade = (float) ((fahrenheit - 32) / 1.8);
            int tempL = Integer.parseInt(fnum.format(centigrade / 10).substring(3, 5));
            int tempH = (int) ((fahrenheit - 32) / 18) * 100 / 256;
            Log.e("1441312311", centigrade + "");
            Log.e("1441312312", tempL + "");
            Log.e("144313123123", tempH + "");

            final int charaProp = characteristic.getProperties();
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                //读取数据，数据将在回调函数中
                //mBluetoothLeService.readCharacteristic(characteristic);
                byte[] value = new byte[20];
                value[0] = (byte) 0x00;
                byte[] WriteBytes;
                WriteBytes = new byte[]{0X5B, 0X02, (byte) tempH, (byte) tempL, 0x5B};

                characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                characteristic.setValue(WriteBytes);
                mBluetoothLeService.writeCharacteristic(characteristic);
                Log.e("123323122", centigrade + "");
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                mBluetoothLeService.setCharacteristicNotification(characteristic, true);
            }
        }
    }

    private void setDynamicMode(int position) {
        int fahrenheit = 5 * position + 360;
        DecimalFormat fnum = new DecimalFormat(".00");
        float centigrade = (float) ((fahrenheit - 32) / 1.8);
        int tempL = Integer.parseInt(fnum.format(centigrade / 10).substring(3, 5));
        int tempH = (int) ((fahrenheit - 32) / 18);
        Log.e("1441312311", centigrade + "");
        Log.e("1441312312", tempL + "");
        Log.e("144313123123", tempH + "");
        final int charaProp = characteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            // If there is an active notification on a characteristic, clear
            // it first so it doesn't update the data field on the user interface.
            if (mNotifyCharacteristic != null) {
                mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                mNotifyCharacteristic = null;
            }
            //读取数据，数据将在回调函数中
            //mBluetoothLeService.readCharacteristic(characteristic);
            byte[] value = new byte[20];
            value[0] = (byte) 0x00;
            byte[] WriteBytes;
            WriteBytes = new byte[]{0X5B, 0X01, (byte) position, 0X5B};

            characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
            characteristic.setValue(WriteBytes);
            mBluetoothLeService.writeCharacteristic(characteristic);
        }
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            mNotifyCharacteristic = characteristic;
            mBluetoothLeService.setCharacteristicNotification(characteristic, true);
        }

    }

    boolean dynamicInitScroll = false;
    int dynamicModeScroll = 0;

//    public boolean onCreateOptionsMenu(Menu paramMenu) {
//        getMenuInflater().inflate(R.menu.drawer_view, paramMenu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
//        if (paramMenuItem.getItemId() == android.R.id.home) {
//            finish();
//        } else {
//            drawerLayout.openDrawer(8388613);
//            Log.e("1111111", paramMenuItem.getItemId() + "");
//        }
//        return true;
//    }

    @Override
    protected void initComp(Bundle savedInstanceState) {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lockDevice1.setSelected(true);
        setLoading(true);
        initTemperatureList();
        final AlphaAnimation localAlphaAnimation1 = new AlphaAnimation(0.0F, 1.0F);
        final AlphaAnimation localAlphaAnimation2 = new AlphaAnimation(1.0F, 0.0F);
        localAlphaAnimation2.setDuration(500L);
        localAlphaAnimation1.setDuration(500L);
        localAlphaAnimation2.setFillAfter(true);
        localAlphaAnimation1.setFillAfter(true);
        final LinearLayoutManager dynamicModeLayoutManager = new LinearLayoutManager(this, 0, false);
        Point localPoint = new Point();
        getWindowManager().getDefaultDisplay().getSize(localPoint);
        int i = (int) ((localPoint.x) / 2 - this.getResources().getDimension(R.dimen.dynamic_mode_icon_size) / 2.0F);
        float f = getResources().getDimension(R.dimen.dynamic_mode_icon_size) / 2.0F - 50;

        int a = (int) ((localPoint.x) / 2 - this.getResources().getDimension(R.dimen.temperature_icon_size) / 2.0F);
        float b = getResources().getDimension(R.dimen.temperature_icon_size) / 2.0F - 200;
//        CarouselLayoutManager temperatureLayoutManager = new CarouselLayoutManager(0);
//        temperatureLayoutManager.setMaxVisibleItems(1);
//        temperatureLayoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
//            @Override
//            public void onCenterItemChanged(int adapterPosition) {
//                Log.e("1231312312", adapterPosition + "");
//                position = adapterPosition;
//            }
//        });
        circleRecyclerView.setLayoutManager(dynamicModeLayoutManager);

        deviceAdapter = new DeviceAdapter(this, temperature);
        circleRecyclerView.setAdapter(deviceAdapter);
        circleRecyclerView.addItemDecoration(new DV_HorizontalSpaceItemDecoration(100));
        circleRecyclerView.setPadding(a, 0, a + (int) b, 0);
        new LinearSnapHelper().attachToRecyclerView(circleRecyclerView);
        circleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
//                    Log.e("1231312311", position + "");
                    position = ((dynamicModeLayoutManager.findFirstVisibleItemPosition() + dynamicModeLayoutManager.findLastVisibleItemPosition()) / 2);
                    setTemperature(position);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        dynamicModeAdapter = new DynamicModeAdapter(DeviceControlActivity.this);
        final LinearLayoutManager dynamicModeLayoutManager1 = new LinearLayoutManager(this, 0, false);
        dynamicModeRecyclerView.setLayoutManager(dynamicModeLayoutManager1);
        dynamicModeRecyclerView.setAdapter(dynamicModeAdapter);
        dynamicModeRecyclerView.addItemDecoration(new DV_HorizontalSpaceItemDecoration(100));

        dynamicModeRecyclerView.setPadding(i, 0, i + (int) f, 0);
        new LinearSnapHelper().attachToRecyclerView(dynamicModeRecyclerView);
        temperatureUpIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < 7) {
                    position++;
                }
                setTemperature(position);
            }
        });
        temperatureDownIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    position--;
                }
                setTemperature(position);
            }
        });

        controlSettingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingView.setVisibility(View.VISIBLE);
            }
        });

        controlBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settingBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingView.setVisibility(View.GONE);
            }
        });

        dynamicModeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    dynamicMode = ((dynamicModeLayoutManager1.findFirstVisibleItemPosition() + dynamicModeLayoutManager1.findLastVisibleItemPosition()) / 2);
                    Log.e("13123", dynamicMode + "");
                    if (dynamicModeScroll != dynamicMode) {
                        String dynamicModeTitleString = "";
                        String dynamicModeDescriptionString = "";

                        switch (dynamicMode) {
                            case 0:
                                dynamicModeTitleString = "Standard";
                                dynamicModeDescriptionString = "The default setting for PAX 3.\nTemp boosts when you draw, auto-cools when you don't.\nThe perfect Standard.";
                                break;
                            case 1:
                                dynamicModeTitleString = "Boost";
                                dynamicModeDescriptionString = "Keeps your device in high gear.\nTemp boosts aggressively and auto-cools slower.\nGet what you need with speed.";
                                break;
                            case 2:
                                dynamicModeTitleString = "Efficiency";
                                dynamicModeDescriptionString = "Don’t waste a drop.\nTemp setting ramps up gradually throughout your session in addition to Standard temp boost and auto-cooling.\nEconomic and long-lasting.";
                                break;
                            case 3:
                                dynamicModeTitleString = "Stealth";
                                dynamicModeDescriptionString = "For ultimate discretion.\nLEDs dim, auto-cools fast.\nReduced material odor means increased privacy.";
                                break;
                            case 4:
                                dynamicModeTitleString = "Flavor";
                                dynamicModeDescriptionString = "The most delicious possible.\nTemp boosts more during draw and decreases quickly after draw.\nThe most on-demand heating ever.";
                                break;
                        }
//                        dynamicModeTitle.setText(dynamicModeTitleString);
//                        dynamicModeDescription.setText(dynamicModeDescriptionString);
                        dynamicInitScroll = false;
                        dynamicHandler.removeCallbacksAndMessages(null);
                        dynamicHandler.postDelayed(new Runnable() {
                            public void run() {
//                                pedalsLayout.startAnimation(localAlphaAnimation1);
//                                dynamicModeLayout.startAnimation(localAlphaAnimation2);
                                dynamicModeAdapter.setFocusedItemPosition(dynamicMode);
                                dynamicModeAdapter.setRecyclerSettled(true);
                                dynamicModeAdapter.notifyDataSetChanged();
                                setDynamicMode(dynamicMode);
                            }
                        }, 1500L);
                        dynamicModeScroll = dynamicMode;

                    }
                } else if (newState == 1) {
                    if (!dynamicInitScroll) {
//                        pedalsLayout.startAnimation(localAlphaAnimation2);
//                        dynamicModeLayout.startAnimation(localAlphaAnimation1);
                        dynamicInitScroll = true;
                        dynamicModeAdapter.setFocusedItemPosition(6);
                        dynamicModeAdapter.setRecyclerSettled(false);
                        dynamicModeAdapter.notifyDataSetChanged();
                        dynamicInitScroll = true;
                    }


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dynamicInitScroll) {

                    dynamicInitScroll = false;

                    return;
                }
            }
        });

        temperatureNumRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fahrenheit = 0;
                try {
                    fahrenheit = Integer.parseInt(temperatureNumTv.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (isFahrenheit) {
                    isFahrenheit = false;
                    temperatureTypeTv.setText("℃");
                    temperatureNumTv.setText((fahrenheit - 32) * 5 / 9 + "");
                } else {
                    isFahrenheit = true;
                    temperatureTypeTv.setText("℉");
                    temperatureNumTv.setText((32 + fahrenheit * 9 / 5 + 1) + "");
                }
            }
        });

        seekBarBrightness.setMax(0xff - 1);
        seekBarBrightness.setProgress(0xff - 1);
        seekBarBrightness.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent) {
                if (MotionEvent.ACTION_DOWN == paramAnonymousMotionEvent.getAction()) {
                    paramAnonymousView.getParent().requestDisallowInterceptTouchEvent(true);
                } else if (MotionEvent.ACTION_MOVE == paramAnonymousMotionEvent.getAction()) {
                    paramAnonymousView.onTouchEvent(paramAnonymousMotionEvent);
                } else if (MotionEvent.ACTION_UP == paramAnonymousMotionEvent.getAction() || MotionEvent.ACTION_CANCEL == paramAnonymousMotionEvent.getAction()) {
                    paramAnonymousView.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                tvBrightness.setText("BRIGHTNESS-" + ((progress + 1) * 100 / 255) + "%");
//                Integer.toHexString(progress);
                light = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                final int charaProp = characteristic.getProperties();
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    // If there is an active notification on a characteristic, clear
                    // it first so it doesn't update the data field on the user interface.
                    if (mNotifyCharacteristic != null) {
                        mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                        mNotifyCharacteristic = null;
                    }
                    //读取数据，数据将在回调函数中
                    //mBluetoothLeService.readCharacteristic(characteristic);
                    byte[] value = new byte[20];
                    value[0] = (byte) 0x00;
                    byte[] WriteBytes;
                    WriteBytes = new byte[]{0x5B, 0X04, (byte) light, 0x5B};
                    PrefUtils.setLong(DeviceControlActivity.this,
                            "light", light);

                    characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                    characteristic.setValue(WriteBytes);
                    mBluetoothLeService.writeCharacteristic(characteristic);
                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    mNotifyCharacteristic = characteristic;
                    mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                }
            }
        });
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = globalValue.isCheck1();
                if (isCheck) {
                    if (v == game1) game1.setSelected(false);

                    final int charaProp = characteristic.getProperties();

                    //如果该char可写
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                        if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        //读取数据，数据将在回调函数中
                        //mBluetoothLeService.readCharacteristic(characteristic);
                        byte[] value = new byte[20];
                        value[0] = (byte) 0x00;
                        byte[] WriteBytes;

                        WriteBytes = new byte[]{0x5B, 0X05, (byte) 0x00, 0x5B};

                        characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                        characteristic.setValue(WriteBytes);
                        mBluetoothLeService.writeCharacteristic(characteristic);
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                    }
                    gameHeaderText.setText("GAME");
                } else {
                    if (v == game1) game1.setSelected(true);
                    game2.setSelected(false);
                    game3.setSelected(false);

                    final int charaProp = characteristic.getProperties();

                    //如果该char可写
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                        if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        //读取数据，数据将在回调函数中
                        //mBluetoothLeService.readCharacteristic(characteristic);
                        byte[] value = new byte[20];
                        value[0] = (byte) 0x00;
                        byte[] WriteBytes;

                        WriteBytes = new byte[]{0x5B, 0X05, (byte) 0X01, 0x5B};

                        characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                        characteristic.setValue(WriteBytes);
                        mBluetoothLeService.writeCharacteristic(characteristic);
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                    }
                    gameHeaderText.setText("GAME- PaxMan");
                }
                globalValue.setCheck1(!isCheck);
                globalValue.setCheck2(false);
                globalValue.setCheck3(false);
            }
        });
        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = globalValue.isCheck2();
                if (isCheck) {
                    if (v == game2) game2.setSelected(false);

                    final int charaProp = characteristic.getProperties();

                    //如果该char可写
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                        if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        //读取数据，数据将在回调函数中
                        //mBluetoothLeService.readCharacteristic(characteristic);
                        byte[] value = new byte[20];
                        value[0] = (byte) 0x00;
                        byte[] WriteBytes;

                        WriteBytes = new byte[]{0x5B, 0X05, (byte) 0X00, 0x5B};

                        characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                        characteristic.setValue(WriteBytes);
                        mBluetoothLeService.writeCharacteristic(characteristic);
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                    }
                    gameHeaderText.setText("GAME");
                } else {
                    if (v == game2) game2.setSelected(true);
                    game1.setSelected(false);
                    game3.setSelected(false);

                    final int charaProp = characteristic.getProperties();

                    //如果该char可写
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                        if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        //读取数据，数据将在回调函数中
                        //mBluetoothLeService.readCharacteristic(characteristic);
                        byte[] value = new byte[20];
                        value[0] = (byte) 0x00;
                        byte[] WriteBytes;

                        WriteBytes = new byte[]{0x5B, 0X05, (byte) 0X02, 0x5B};

                        characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                        characteristic.setValue(WriteBytes);
                        mBluetoothLeService.writeCharacteristic(characteristic);
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                    }
                    gameHeaderText.setText("GAME- Simon");
                }
                globalValue.setCheck2(!isCheck);
                globalValue.setCheck1(false);
                globalValue.setCheck3(false);
            }
        });
        game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = globalValue.isCheck3();
                if (isCheck) {
                    if (v == game3) game3.setSelected(false);

                    final int charaProp = characteristic.getProperties();

                    //如果该char可写
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                        if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        //读取数据，数据将在回调函数中
                        //mBluetoothLeService.readCharacteristic(characteristic);
                        byte[] value = new byte[20];
                        value[0] = (byte) 0x00;
                        byte[] WriteBytes;

                        WriteBytes = new byte[]{0x5B, 0X05, (byte) 0X00, 0x5B};

                        characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                        characteristic.setValue(WriteBytes);
                        mBluetoothLeService.writeCharacteristic(characteristic);
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                    }
                    gameHeaderText.setText("GAME");
                } else {
                    if (v == game3) game3.setSelected(true);
                    game1.setSelected(false);
                    game2.setSelected(false);

                    final int charaProp = characteristic.getProperties();

                    //如果该char可写
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                        if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        //读取数据，数据将在回调函数中
                        //mBluetoothLeService.readCharacteristic(characteristic);
                        byte[] value = new byte[20];
                        value[0] = (byte) 0x00;
                        byte[] WriteBytes;

                        WriteBytes = new byte[]{0x5B, 0X05, (byte) 0X03, 0x5B};

                        characteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                        characteristic.setValue(WriteBytes);
                        mBluetoothLeService.writeCharacteristic(characteristic);
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                    }
                    gameHeaderText.setText("GAME- Spin_The_Pax");
                }
                globalValue.setCheck3(!isCheck);
                globalValue.setCheck1(false);
                globalValue.setCheck2(false);
            }
        });
        currentHeaterTemp.setVisibility(View.VISIBLE);
//        currentHeaterTemp.setTextColor(0xFF00cc00);
        currentHeaterTemp.setText("");
        temperatureRing.setVisibility(View.VISIBLE);
        temperatureRing.setColor(0xFF64E4FB);
        temperatureRing.setPercentFull(0.0f);
    }

    @Override
    protected void initData(Intent intent) {
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
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
        ButterKnife.bind(this);
        initData(getIntent());
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 监控返回键
            if (settingView.getVisibility() == View.GONE) {
                finish();
            } else {
                settingView.setVisibility(View.GONE);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
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

    public class GlobalValue {
        public boolean isCheck1() {
            return isCheck1;
        }

        public void setCheck1(boolean check1) {
            isCheck1 = check1;
        }

        public boolean isCheck2() {
            return isCheck2;
        }

        public void setCheck2(boolean check2) {
            isCheck2 = check2;
        }

        public boolean isCheck3() {
            return isCheck3;
        }

        public void setCheck3(boolean check3) {
            isCheck3 = check3;
        }

        private boolean isCheck1 = false;
        private boolean isCheck2 = false;
        private boolean isCheck3 = false;
    }


    public void setLoading(boolean isLoading) {
        try {
            if (isLoading) {
                if (waitDialog == null || !waitDialog.isShowing()) {
                    waitDialog = new ProgressDialog(this, R.style.MyDialogStyleBottom);
                    waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    waitDialog.setCanceledOnTouchOutside(false);
                    waitDialog.setCancelable(false);
                    ImageView view = new ImageView(this);
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    Animation loadAnimation = AnimationUtils.loadAnimation(
                            this, R.anim.rotate);
                    view.startAnimation(loadAnimation);
                    loadAnimation.start();
                    view.setImageResource(R.mipmap.progressloading);
                    waitDialog.show();
                    waitDialog.setContentView(view);
                    waitDialog.setTitle("Connecting");
                }

            } else {
                if (waitDialog != null && waitDialog.isShowing()) {
                    waitDialog.dismiss();
                    waitDialog = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
