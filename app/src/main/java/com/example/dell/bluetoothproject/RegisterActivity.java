package com.example.dell.bluetoothproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RegisterActivity extends AppCompatActivity  {

    private LinearLayout ll_register;
    private EditText     et_name_register;
    private EditText      et_pwd_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

    }

    private void initView() {

      ll_register  =   findViewById(R.id.ll_register);
      et_name_register  = findViewById(R.id.et_name_register);
       et_pwd_register  =   findViewById(R.id.et_pwd_register);
    }
}
