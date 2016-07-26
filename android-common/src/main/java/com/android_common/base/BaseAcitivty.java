package com.fengyulong.android_common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by fengyulong on 2016/7/22.
 */
public class BaseAcitivty extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(
                        getApplicationContext())
                        .threadPriority(Thread.NORM_PRIORITY - 2)
                        .memoryCacheExtraOptions(480, 480)
                        .memoryCacheSize(2 * 1024 * 1024)
                        .denyCacheImageMultipleSizesInMemory()
                        .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        .memoryCache(new WeakMemoryCache()).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
