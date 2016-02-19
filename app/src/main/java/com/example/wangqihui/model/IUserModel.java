package com.example.wangqihui.model;

import android.content.Context;

/**
 * Created by wangqihui on 2016/1/8.
 */
public interface   IUserModel {

     public boolean login(Context context ,String name ,String password ,OnLoginListner loginListner);
}
