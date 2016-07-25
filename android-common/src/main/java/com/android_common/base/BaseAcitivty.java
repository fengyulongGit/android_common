package com.fengyulong.android_common.base;

import android.app.Activity;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by fengyulong on 2016/7/22.
 */
public class BaseAcitivty extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
