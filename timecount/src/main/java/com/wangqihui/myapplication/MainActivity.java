package com.wangqihui.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private TimeTextView mTimeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTimeTextView=(TimeTextView) findViewById(R.id.electricity_countdown);

//为控件设置一个初始时间，通常是服务器给返回一组时间
        mTimeTextView.setTime("02","00","00","00");
    }
}
