package com.example.zhoukao2_moni_1110.model;

import com.example.zhoukao2_moni_1110.callback.ICallBack;
import com.example.zhoukao2_moni_1110.utils.HttpUtils;

import java.lang.reflect.Type;

/**
 * Created by 择木 on 2018/11/10.
 */

public class LoginModel {


    public void login(String url, ICallBack callBack, Type type){
        HttpUtils.getInstance().get(url, callBack, type);
    }
}
