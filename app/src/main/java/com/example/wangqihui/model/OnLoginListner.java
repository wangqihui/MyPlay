package com.example.wangqihui.model;

import com.example.wangqihui.bean.UserBean;

/**
 * Created by wangqihui on 2016/1/8.
 */
public interface OnLoginListner {

      public boolean loginSucces(UserBean user);
      public boolean loginFail();

}
