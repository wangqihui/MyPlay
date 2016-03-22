package com.wangqihui.myapplication;

import android.content.Context;

import android.graphics.Paint;
import android.os.*;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by wangqihui on 2016/3/21.
 */
public class TimeTextView extends LinearLayout  {


    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息

    private long[] times;

    private Context mContext;

    private TextView mday, mhour, mmin, msecond;//天，小时，分钟，秒

    private long days,hours,minutes,seconds;

    private boolean run = true; //是否启动了

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext =context;
        inflateLayout();
        /*mPaint =new Paint();
        TypedArray array =context.obtainStyledAttributes(attrs,R.styleable.TimeTextView);
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响*/
    }

    public TimeTextView(Context context) {
        super(context);
        mContext =context;
        inflateLayout();
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext =context;
        inflateLayout();
     /*   mPaint =new Paint();
        TypedArray array =context.obtainStyledAttributes(attrs,R.styleable.TimeTextView);
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响*/
    }



    private void inflateLayout(){
        this.addView(LayoutInflater.from(mContext).inflate(R.layout.timeview_layout, null));
        mday = (TextView) this.findViewById(R.id.electricity_time_day);
        mhour = (TextView) this.findViewById(R.id.electricity_time_hour);
        mmin = (TextView) this.findViewById(R.id.electricity_time_min);
        msecond = (TextView) this.findViewById(R.id.electricity_time_mse);
    }


    public void setTime(String day,String hour,String minute,String second){
        if(TextUtils.isEmpty(day)){
            days = 0;
        }else{
            days = Long.parseLong(day);
        }


        if(TextUtils.isEmpty(hour)){
            hours = 0;
        }else{
            hours = Long.parseLong(hour);
        }


        if(TextUtils.isEmpty(minute)){
            minutes = 0;
        }else{
            minutes = Long.parseLong(minute);
        }


        if(TextUtils.isEmpty(second)){
            seconds = 0;
        }else{
            seconds = Long.parseLong(second);
        }
        setUi();
        handler.removeMessages(0);

//每隔1秒钟发送一次handler消息

        handler.sendEmptyMessageDelayed(0, 1000);
    }


    private void setUi(){
        mday.setText(String.valueOf(days));
        mhour.setText(String.valueOf(hours));
        mmin.setText(String.valueOf(minutes));
        msecond.setText(String.valueOf(seconds));
    }


    /*倒计时的计算方法*/
    private boolean computeTime(){
        boolean flag = true;
        seconds--;
        if (seconds < 0) {
            minutes--;
            seconds = 59;
            if (minutes < 0) {
                minutes = 59;
                hours--;
                if (hours < 0) {
                    hours = 23;
                    days--;
                    if(days<0){
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }


    public void stopComputeTime(){
        run = false;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (run) {
                        if(computeTime()){
                            setUi();
                            handler.sendEmptyMessageDelayed(0, 1000);
                        }
                    }
                    break;
            }
        }
    };




}
