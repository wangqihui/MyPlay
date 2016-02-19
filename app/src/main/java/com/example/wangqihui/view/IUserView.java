package com.example.wangqihui.view;

import com.example.wangqihui.bean.UserBean;

/**
 * Created by wangqihui on 2016/1/8.
 */
public interface IUserView {

         public Boolean login(UserBean user);
         public void  showResult(String message);
         public void  clear();
}


