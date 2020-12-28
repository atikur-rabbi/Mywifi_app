package com.we2app.mywifiapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

class WifiReceiver extends BroadcastReceiver {
    private WifiManager wifiManager;
    private ListView wifiDeviceList;

    public WifiReceiver(WifiManager wifiManager, ListView wifiDeviceList) {
        this.wifiManager = wifiManager;
        this.wifiDeviceList = wifiDeviceList;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onReceive(Context context, Intent intent) {
        boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
        if (success) {
            scanSuccess(context);
        } else {
            scanFailure(context);
        }
    }

    private void scanFailure(Context context) {
        Toast.makeText(context, "scan Failure...", Toast.LENGTH_LONG).show();
    }

    private void scanSuccess(Context context) {
        StringBuilder sb = new StringBuilder();
        List<ScanResult> wifiList = wifiManager.getScanResults();
        ArrayList<String> deviceList = new ArrayList<>();
        for (ScanResult scanResult : wifiList) {
            sb.append("\n").append(scanResult.SSID).append(" - ").append(scanResult.BSSID);
            deviceList.add(scanResult.SSID + " - " + scanResult.BSSID);
        }
        Toast.makeText(context, deviceList.size(), Toast.LENGTH_LONG).show();
        Toast.makeText(context, sb, Toast.LENGTH_SHORT).show();
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, deviceList.toArray());
        wifiDeviceList.setAdapter(arrayAdapter);
    }
}
