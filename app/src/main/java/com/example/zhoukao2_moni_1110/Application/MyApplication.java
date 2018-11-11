package com.example.zhoukao2_moni_1110.Application;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by 择木 on 2018/11/10.
 */

public class MyApplication  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }
}
