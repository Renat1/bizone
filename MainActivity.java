package com.example.mike.bizone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.DecimalFormat;

import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

//import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import android.provider.Settings;
import android.provider.Settings.System;

public class MainActivity extends AppCompatActivity {

    String[] data = {"Device Model", "b", "OS + OS Version", "d", "IMEI", "f", "Screen Resolution", "h", "Screen DPI", "j", "GPS Coordinates", "k", "Network Connection type", "x", "WiFi SSID", "x", "WiFi BSSID", "x", "Current time", "x"};
    GridView gvMain;
    ArrayAdapter<String> adapter;
    private LocationManager myLocationManager;
    private LocationListener myLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(this, R.layout.activity_main, R.id.tvText, data);
        gvMain = (GridView) findViewById(R.id.gvMain);

        Button start = (Button) findViewById(R.id.Start);
        start.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
            public void onClick(View v) {

                String manufacturer = Build.MANUFACTURER;
                String model = Build.MODEL;
                data[1] = manufacturer + " " + model;
                data[3] = Build.VERSION.RELEASE;
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

               // data[5] = tm.getDeviceId();


                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                data[7] = width + "X" + height;
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int densityDpi = (int) (metrics.density);  // * 160f);
                data[9] = densityDpi + "";

              /*  if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {



                } */

              /*  LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                double longitude = location.getLongitude();
                longitude = Math.round(longitude * 100) / 100.0d;
                double latitude = location.getLatitude();
                latitude = Math.round(latitude * 100) / 100.0d;
                double altitude = location.getAltitude();
                data[11] = longitude + "X" + latitude + "X" + altitude; */

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); //this.getSystemService(Context.CONNECTIVITY_SERVICE);

                final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                String network = networkInfo.getTypeName();
                data[13] = network;

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ssid_number_1 = wifiInfo.toString().indexOf(":");
                int ssid_number_2 = wifiInfo.toString().indexOf(",");
                String ssid = wifiInfo.toString().substring(ssid_number_1 + 1, ssid_number_2);
                data[15] = ssid;

                String ssid_cut = wifiInfo.toString().substring(ssid_number_2 + 1);
                int bssid_number_1 = ssid_cut.toString().indexOf(":");
                int bssid_number_2 = ssid_cut.toString().indexOf(",");
                String bssid = ssid_cut.substring(bssid_number_1 + 1, bssid_number_2);
                data[17] = bssid;

                Time now = new Time();
                now.setToNow();
                String time = now.toString();
                int loc_1 = time.indexOf("T");
                int loc_2 = time.indexOf("E");
                time = time.substring(loc_1 + 1, loc_2);
                String time_h = time.substring(0, 2) + "ч ";
                String time_m = time.substring(2, 4) + "м ";
                String time_s = time.substring(4, 6) + "с";
                data[19] = time_h + time_m + time_s;


                gvMain.setAdapter(adapter);

            }
        });
    }
}

