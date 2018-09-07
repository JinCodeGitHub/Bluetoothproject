package com.example.dell.bluetoothproject.internet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dell.bluetoothproject.R;

import org.w3c.dom.Text;

public class BaseStationActivity extends AppCompatActivity  {

    private TextView  tv_text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basestation_activity);

        initView();

    }

    private void initView() {

        tv_text   = findViewById(R.id.tv_text);
    }
}
