package com.example.zhoukao2_moni_1110;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhoukao2_moni_1110.bean.LoginBean;
import com.example.zhoukao2_moni_1110.presenter.LoginPresent;
import com.example.zhoukao2_moni_1110.view.IView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IView {

    private EditText edUsername;
    private EditText edPassword;
    private Button btnLogin;
    private LoginPresent loginPresent;
    private LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        loginPresent = new LoginPresent();
        loginPresent.attach(this);
        loginPresent.isFirst();



    }

    private void initView() {
        edUsername = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        btnLogin   = findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                loginPresent.check();
                break;
        }

    }



    @Override
    public void success(Object o) {
        loginBean = new LoginBean();
        if (loginBean !=null){
            Toast.makeText(this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failed(Exception e) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();

    }

    @Override
    public String getUsername() {
        return edUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return edPassword.getText().toString().trim();
    }

    @Override
    public void setUsername(String username) {
              edUsername.setText(username);
    }

    @Override
    public void setPassword(String password) {
           edPassword.setText(password);
    }

    @Override
    public void check(boolean isChecked) {
            if (isChecked){
                loginPresent.login("http://www.xieast.com/api/user/login.php");
            }
    }




    @Override
    public void gotoMain() {
        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginPresent!=null){
            loginPresent.detach();
        }
    }
}
