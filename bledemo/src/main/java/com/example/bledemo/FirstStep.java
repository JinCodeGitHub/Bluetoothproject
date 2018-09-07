package com.example.bledemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * <pre>
 *     author : dell
 *     time   : 2018/07/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class FirstStep extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firststep_activity);

        findViewById(R.id.bt_firststep).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent   = new Intent(FirstStep.this, BleDeviceDemo.class);
                startActivity(intent);
            }
        });
    }
}
