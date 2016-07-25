/**
 *
 */
package com.fengyulong.android_common.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * @author fengyulong
 */
public class NetworkProber {
    private Context context;
    private ConnectivityManager connectivityManager;
    private TelephonyManager telephonyManager;
    private LocationManager locationManager;
    private WifiManager wifiManager;

    public NetworkProber(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        locationManager = ((LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE));
        wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 网络是否可用
     *
     * @return
     */
    public boolean isNetworkAvailable() {
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gps是否打开
     *
     * @return
     */
    public boolean isGpsEnabled() {
        if (locationManager != null) {
            List<String> accessibleProviders = locationManager.getProviders(true);
            return accessibleProviders != null && accessibleProviders.size() > 0;
        } else {
            return false;
        }
    }

    /**
     * wifi是否打开
     */
    public boolean isWifiEnabled() {
        return ((connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || telephonyManager
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断当前网络是否是wifi网络
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
     *
     * @return boolean
     */
    public boolean isWifi() {
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前网络是否是3G网络
     *
     * @return boolean
     */
    public boolean is3G() {
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public NetworkInfo getActiveNetworkInfo() {
        return connectivityManager.getActiveNetworkInfo();
    }

    public DhcpInfo getDhcpInfo() {
        return wifiManager.getDhcpInfo();
    }
}
