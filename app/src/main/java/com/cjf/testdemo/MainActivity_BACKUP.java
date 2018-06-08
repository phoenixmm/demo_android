package com.cjf.testdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cjf.testdemo.floatwind.MainService_testTopWindow;
import com.cjf.testdemo.toolbar.MainActivity_testToolbar;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
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

    private void testWifi(Context context) {        //test3: get wifi/bt bound device-list
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        Log.d(LOG_TAG, "wifi test start");
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if(info!=null) {
                Log.d(LOG_TAG, " getConnectionInfo --------- ");
                Log.d(LOG_TAG, " info = " + info.toString());
                Log.d(LOG_TAG, " bssid = " + info.getBSSID());
                Log.d(LOG_TAG, " ssid = " + info.getSSID());
                Log.d(LOG_TAG, " rssi = " + info.getRssi());
                Log.d(LOG_TAG, " ip = " + info.getIpAddress());
                Log.d(LOG_TAG, " mac = " + info.getMacAddress());
                Log.d(LOG_TAG, " speed = " + info.getLinkSpeed());
//                        Log.d(LOG_TAG, " frequency = " + info.getFrequency());
                Log.d(LOG_TAG, " hideSsid = " + info.getHiddenSSID());
                Log.d(LOG_TAG, " networkId = " + info.getNetworkId());
            }

            List<WifiConfiguration> boundDevicesList = wifiManager.getConfiguredNetworks();
            if (boundDevicesList != null) {
                Log.d(LOG_TAG, " getConfiguredNetworks --------- size ==" + boundDevicesList.size());

                for (WifiConfiguration boundDevice : boundDevicesList) {
                    Log.d(LOG_TAG, " getConfiguredNetworks --------- ");
//                            Log.d(LOG_TAG, " info = " + boundDevice.toString());
                    Log.d(LOG_TAG, " bssid = " + boundDevice.BSSID);
                    Log.d(LOG_TAG, " ssid = " + boundDevice.SSID);
                    Log.d(LOG_TAG, " fqdn = " + boundDevice.FQDN);

                }
            }

            IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            context.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.d(LOG_TAG, " receive --------- " + intent.getAction());
//                            wifiManager.startScan();
                    List<ScanResult> scanDevicesList = wifiManager.getScanResults();
                    if (scanDevicesList != null) {
                        Log.d(LOG_TAG, " getScanResults --------- size ==" + scanDevicesList.size());

                        for (ScanResult boundDevice : scanDevicesList) {
                            Log.d(LOG_TAG, " getScanResults --------- ");
//                                    Log.d(LOG_TAG, " info = " + boundDevice.toString());
                            Log.d(LOG_TAG, " bssid = " + boundDevice.BSSID);
                            Log.d(LOG_TAG, " ssid = " + boundDevice.SSID);
                            Log.d(LOG_TAG, " level = " + boundDevice.level);
                        }
                    }
                }
            }, filter);

            Log.d(LOG_TAG, "wifi start scan");
            wifiManager.startScan();

        }
        Log.d(LOG_TAG, "wifi test end");
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
//                Log.d(LOG_TAG, " Uuid = " + boundDevice.getUuids());
            }
        }
        Log.d(LOG_TAG, "bt getBondedDevices end");
    }


    private void testToolBar() {
        //test4 toolbar
        Intent intentToolbar = new Intent(this, MainActivity_testToolbar.class);
        this.startActivity(intentToolbar);
    }

    private void testProxy(Context context) {
        String proxyhost;
        int proxyport=-1;

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH){

            proxyhost = System.getProperty("http.proxyHost");
            String port =   System.getProperty("http.proxyPort");
            Log.d(LOG_TAG, "proxyhost = " + proxyhost);
            Log.d(LOG_TAG, "port = " + port);

            if(port != null && port.length() > 0){
                proxyport = Integer.parseInt(port);
            }

            String proHost = android.net.Proxy.getDefaultHost();
            int proPort = android.net.Proxy.getDefaultPort();
            Log.d(LOG_TAG, "proHost = " + proHost);
            Log.d(LOG_TAG, "proPort = " + proPort);

        }else{
            proxyhost = android.net.Proxy.getHost(context);
            proxyport = android.net.Proxy.getPort(context);
        }
    }

    private void testVpn(Context context) {
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if(niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if(!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    Log.d(LOG_TAG, "isVpnUsed() NetworkInterface Name: " + intf.getName());
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())){
                        Log.d(LOG_TAG, "VpnUsed !!!");
                    }
                }
            } else {
                Log.d(LOG_TAG, "no NetworkInterface !!!");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
