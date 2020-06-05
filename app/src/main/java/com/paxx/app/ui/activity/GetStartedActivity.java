package com.paxx.app.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paxx.app.MainApplication;
import com.paxx.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hong on 2017/6/21.
 */

public class GetStartedActivity extends BaseActivity {
    @Bind(R.id.paxEraImage)
    ImageView paxEraImage;
    @Bind(R.id.pax3Image)
    ImageView pax3Image;
    @Bind(R.id.get_started_lin_layout)
    LinearLayout getStartedLinLayout;
    @Bind(R.id.getting_started_line1)
    TextView gettingStartedLine1;
    @Bind(R.id.getting_started_name_your_pax)
    EditText gettingStartedNameYourPax;
    @Bind(R.id.getting_started_line2)
    TextView gettingStartedLine2;
    @Bind(R.id.pairImage)
    ImageView pairImage;
    @Bind(R.id.pair_lin_layout)
    RelativeLayout pairLinLayout;
    @Bind(R.id.butDontHaveAPax)
    Button butDontHaveAPax;
    @Bind(R.id.container)
    CoordinatorLayout container;

    private Handler mHandler;
    BluetoothAdapter adapter;
    private boolean mScanning;
    private static final long SCAN_PERIOD = 10000;
    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.activity_get_started;
    }

    @Override
    protected void initComp(Bundle savedInstanceState) {
        mHandler = new Handler();
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = bluetoothManager.getAdapter();
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled() && adapter != null) {
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
                                Snackbar.make(container, "Bluetooth must be enabled to use your AOX3.", Snackbar.LENGTH_LONG).show();
                            }
                        });

                dialog = builder.create();
            }
            dialog.show();
        }
        startAnimation();
    }

    @OnClick({R.id.pax3Image})
    public void pairPax(View paramView) {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled() && adapter != null) {
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
                                Snackbar.make(container, "Bluetooth must be enabled to use your AOX3.", Snackbar.LENGTH_LONG).show();
                            }
                        });

                dialog = builder.create();
            }
            dialog.show();
        } else {
            getStartedLinLayout.setVisibility(View.GONE);
            pairLinLayout.setVisibility(View.VISIBLE);
            gettingStartedNameYourPax.setVisibility(View.GONE);
            pairImage.setImageDrawable(GetStartedActivity.this.getResources().getDrawable(R.mipmap.pairing_pax3));
            gettingStartedLine1.setVisibility(View.VISIBLE);
            gettingStartedLine1.setText("Turn_your_AOX3_on_by_pressing_the_mouthpiece");
            setTitle(getString(R.string.pair_your_pax3));
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = "AOX3".toUpperCase();
            gettingStartedLine2.setText("Shake your AOX3 until blue lights appear. We'll take care of the rest.");
            scanLeDevice(true);
        }
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    adapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            adapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            adapter.stopLeScan(mLeScanCallback);
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
                            if("PAX3".equals(device.getName())){
                                adapter.stopLeScan(mLeScanCallback);
                                Intent intent = new Intent(GetStartedActivity.this, DeviceSelectorActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            };

    public void startAnimation() {
        Animation localAnimation = AnimationUtils.loadAnimation(this, R.anim.pair);
        pairImage.startAnimation(localAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!adapter.isEnabled()) {
            if (!adapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Initializes list view adapter.
//        scanLeDevice(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }else if(requestCode == REQUEST_ENABLE_BT){
//            scanLeDevice(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
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
        ButterKnife.bind(this);
    }
}
