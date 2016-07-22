package com.fengyulong.android_common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;


public class WokeCommonUtil {
//	public static final String INTERFACE_URL = "http://121.40.161.115:8080/woke/mobile.do";//内部测试服务器
//	public static final String INTERFACE_URL = "http://121.40.161.115:8180/woke/mobile.do";//外部测试服务器
//	public static final String INTERFACE_URL = "http://192.168.199.115:8080/woke/mobile.do";//本地测试服务器
//	public static final String INTERFACE_URL = "http://112.124.106.43:8080/woke/mobile.do";//正式服务器

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final EnumRunMode RUN_MODE = EnumRunMode.RELEASE;

    public static String getImageCacheDir(Context context) {
        try {
            String dir = context.getFilesDir().getPath() + "/cache/";
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return context.getFilesDir().getPath() + "/cache/";
    }

    public static String getImageTemplCacheDir(Context context) {
        try {
            String dir = context.getFilesDir().getPath();
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return context.getFilesDir().getPath();
    }

    public static String getDataCacheDir(Context context) {
        //String dir = context.getFilesDir().getPath() + "/datacache/";
        try {
            String dir = Environment.getExternalStorageDirectory() + "/datacache/";
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getFilesDir().getPath() + "/datacache/";
    }

}
