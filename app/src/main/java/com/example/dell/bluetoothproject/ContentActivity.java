package com.example.dell.bluetoothproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.dell.bluetoothproject.internet.BaseStationActivity;


public class ContentActivity extends AppCompatActivity  implements View.OnClickListener {

    private LinearLayout ll1;
    private Button       btn1;
    private Button       btn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
    }

    private void initView() {
     ll1 =     findViewById(R.id.ll1);
     btn1  =   findViewById(R.id.btn1);
     btn2  =    findViewById(R.id.btn2);

     btn1.setOnClickListener(this);
     btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn1:

                Intent intentble   = new Intent(ContentActivity.this, MainActivity.class);
                startActivityForResult(intentble,100);

                break;
            case  R.id.btn2:
                Intent  intentIntenet     = new Intent(ContentActivity.this,BaseStationActivity.class);
                startActivityForResult(intentIntenet ,101);
                break;

                default:
                    break;
        }
    }
}
