package com.example.zhoukao2_moni_1110.view;

import android.content.Context;

import com.example.zhoukao2_moni_1110.bean.LoginBean;

/**
 * Created by 择木 on 2018/11/10.
 */

public interface IView<T> {

    void success(T t);
    void  failed(Exception e);
    String getUsername();
    String getPassword();
    void setUsername(String username);
    void setPassword(String password);

    void check(boolean  isChecked);



    void  gotoMain();

    Context getContext();

}
