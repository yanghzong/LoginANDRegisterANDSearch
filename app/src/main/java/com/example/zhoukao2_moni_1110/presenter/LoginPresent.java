package com.example.zhoukao2_moni_1110.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.zhoukao2_moni_1110.bean.LoginBean;
import com.example.zhoukao2_moni_1110.callback.ICallBack;
import com.example.zhoukao2_moni_1110.model.LoginModel;
import com.example.zhoukao2_moni_1110.view.IView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by 择木 on 2018/11/10.
 */

public class LoginPresent {


    private IView iv;
    private LoginModel loginModel;

    public void attach(IView iv){
        this.iv=iv;
        loginModel =new LoginModel();
    }
    public void detach(){
        if (iv!=null){
            iv=null;
        }
    }

    //检验

    public void check(){
        if (TextUtils.isEmpty(iv.getUsername())||TextUtils.isEmpty(iv.getPassword())){
            iv.check(false);

        }else{
            iv.check(true);
        }
    }

    public void  isFirst(){
        SharedPreferences sp=iv.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        String username=sp.getString("username","");
        String password=sp.getString("password","");

        //如果不是第一次登录
        if (!TextUtils.isEmpty(username)  && !TextUtils.isEmpty(password)){
            iv.setUsername(username);
            iv.setPassword(password);
        }
    }

    public void login(String url){

        final String username=iv.getUsername();
        final String password=iv.getPassword();
        url = url.concat("?username=").concat(username).concat("&password=").concat(password);

        Type type=new TypeToken<LoginBean>(){}.getType();

        loginModel.login(url, new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                iv.success(obj);

                SharedPreferences  sp=iv.getContext().getSharedPreferences("config",Context.MODE_PRIVATE);
                 sp.edit().putString("username",username)
                         .putString("password",password)
                         .commit();

                 iv.gotoMain();



            }

            @Override
            public void onFailed(Exception e) {
                iv.failed(e);

            }
        },type);



    }
}
