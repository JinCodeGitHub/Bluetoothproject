package com.example.dell.bluetoothproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;
    private TextView tv_name;
    private TextView tv_pwd;
    private EditText et_name;
    private EditText et_pwd;
    private Button   btn_login;
    private Button   btn_sign_up;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initView();

    }
    //初始化界面
    private void initView() {
        ll1  = findViewById(R.id.ll1);
        ll2  = findViewById(R.id.ll2);
        ll3  = findViewById(R.id.ll3);
        ll4  = findViewById(R.id.ll4);
        tv_name  = findViewById(R.id.tv_name);
        tv_pwd   = findViewById(R.id.tv_pwd);
        et_name  = findViewById(R.id.et_name);
        et_pwd   = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);
        btn_sign_up = findViewById(R.id.btn_sign_up);

        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:
                //需要有业务上面的判断处理逻辑；
                //获取用户名跟密码，设置页面需要显示当前登陆用户（登陆的时间）。

                Intent  intentMain   = new Intent(LoginActivity.this,ContentActivity.class);
                startActivityForResult(intentMain,100);

                break;
            case  R.id.btn_sign_up:
                //如果没有账户跟密码的时候，需要注册；
                Intent intent   = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,100);
                break;

                default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }



}
