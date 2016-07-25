package com.fyl.android_common.base;

import android.app.Application;

import com.fyl.android_common.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by fyl on 2016/7/22.
 */
public class BaseApp extends Application {
    private static BaseApp instance;

    public static BaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //设置默认字体
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .setDefaultFontPath("fonts/hei_xi.ttf")
                .build()
        );
    }
}
