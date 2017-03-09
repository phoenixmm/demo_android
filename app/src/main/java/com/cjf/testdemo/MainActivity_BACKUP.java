package com.cjf.testdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cjf.testdemo.floatwind.MainService_testTopWindow;
import com.cjf.testdemo.toolbar.MainActivity_testToolbar;

import java.util.List;
import java.util.Set;

public class MainActivity_BACKUP extends AppCompatActivity {
    public String LOG_TAG = "cjf------";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //just like Build.VERSION_CODES.JELLY_BEAN (= 16)
        Log.d(LOG_TAG, " Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
        if (Build.VERSION_CODES.JELLY_BEAN >= Build.VERSION.SDK_INT) {

        }







        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------

    private void testFloatWind() { //activity_test_location: float window
        Intent show = new Intent(this, MainService_testTopWindow.class);
        show.putExtra(MainService_testTopWindow.OPERATION,
                MainService_testTopWindow.OPERATION_SHOW);
        this.getApplicationContext().startService(show);

    }

    private void testWifi() {        //test3: get wifi/bt bound device-list
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        ;
        Log.d(LOG_TAG, "wifi getConfiguredNetworks start");
        if (wifiManager != null) {
            List<WifiConfiguration> boundDevicesList = wifiManager.getConfiguredNetworks();
            if (boundDevicesList != null) {
                for (WifiConfiguration boundDevice : boundDevicesList) {
                    Log.d(LOG_TAG, " info --------- ");
                    Log.d(LOG_TAG, " addr = " + boundDevice.toString());
                }
            }
        }
        Log.d(LOG_TAG, "wifi getConfiguredNetworks end");
    }

    private void testBt() {        //test4: get wifi/bt bound device-list
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d(LOG_TAG, "bt getBondedDevices start");
        if (btAdapter != null) {
            Set<BluetoothDevice> boundDevicesList = btAdapter.getBondedDevices();
            for (BluetoothDevice boundDevice : boundDevicesList) {
                Log.d(LOG_TAG, " info --------- ");
                Log.d(LOG_TAG, " addr = " + boundDevice.getAddress());
                Log.d(LOG_TAG, " name = " + boundDevice.getName());
                Log.d(LOG_TAG, " class = " + boundDevice.getBluetoothClass());
                Log.d(LOG_TAG, " Uuid = " + boundDevice.getUuids());
            }
        }
        Log.d(LOG_TAG, "bt getBondedDevices end");
    }


    private void testToolBar() {
        //test4 toolbar
        Intent intentToolbar = new Intent(this, MainActivity_testToolbar.class);
        this.startActivity(intentToolbar);
    }
}
