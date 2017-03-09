package com.cjf.testdemo.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cjf.testdemo.R;

public class MainActivity_testLocationMock extends AppCompatActivity {

    EditText x_location;
    EditText y_location;
    private String mMockProviderName = LocationManager.NETWORK_PROVIDER;//GPS_PROVIDER;
    private LocationManager locationManager;
    private double latitude = 31.3029742, longitude = 120.6097126;// 默认常州

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_location);


        x_location = (EditText) findViewById(R.id.x_location);
        y_location = (EditText) findViewById(R.id.y_location);
        System.out.println("cjf--- test：");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Button locationBtn = (Button) findViewById(R.id.setlocation);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mMockProviderName.equals(LocationManager.NETWORK_PROVIDER)) {
                        mMockProviderName = LocationManager.GPS_PROVIDER;
                    } else {
                        mMockProviderName = LocationManager.NETWORK_PROVIDER;
                    }

                    locationManager.addTestProvider(mMockProviderName, false, true, false, false, true, true,
                            true, 0, 5);
                    locationManager.setTestProviderEnabled(mMockProviderName, true);
                    if (ActivityCompat.checkSelfPermission(MainActivity_testLocationMock.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(MainActivity_testLocationMock.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(mMockProviderName, 0, 0, mlocationListener);

                    Location la = locationManager.getLastKnownLocation(mMockProviderName);
                    if (la != null) {
                        System.out.println("cjf---la.latitude" + Double.toString(la.getLatitude()));
                        System.out.println("cjf---la.longitude" + Double.toString(la.getLongitude()));
                    }

                    // x == 123.38
                    // y == 41.8
                    latitude = Double.valueOf(y_location.getText().toString());//维度
                    longitude = Double.valueOf(x_location.getText().toString());//经度

                    Location location = new Location(mMockProviderName);
                    location.setTime(System.currentTimeMillis());
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    location.setAltitude(2.0f);
                    location.setAccuracy(3.0f);
                    locationManager.setTestProviderLocation(mMockProviderName, location);
                } catch (Exception e) {
                    System.out.println("cjf---e = " + e);

                }

            }
        });

    }

    LocationListener mlocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            System.out.println("cjf--- 时间："+location.getTime());
            System.out.println("cjf---经度："+location.getLongitude());
            System.out.println("cjf---纬度："+location.getLatitude());
            System.out.println("cjf---海拔："+location.getAltitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println("cjf---onStatusChanged ==" + provider );

            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    System.out.println("cjf---当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    System.out.println("cjf---当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    System.out.println("cjf---当前GPS状态为暂停服务状态");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            System.out.println("cjf---onProviderEnabled ==" + provider );

        }

        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("cjf---onProviderDisabled");

        }
    };


}
