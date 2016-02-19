package com.example.wangqihui.model;

import android.content.Context;

import com.example.wangqihui.bean.UserBean;
import com.example.wangqihui.dao.UserDAO;

import java.util.List;

/**
 * Created by wangqihui on 2016/1/8.
 */
public class UserModel implements  IUserModel {


    @Override
    public boolean login(Context context ,String name, String password ,OnLoginListner loginListner) {
         UserDAO  userdao =UserDAO.getInstance(context );
         UserBean bean =new UserBean();
         bean.setUserName(name);
         List<UserBean> userList= userdao.query(bean);
         if(null!=name &&  null!=password && null !=userList
                 && userList.size()>0 && password.equals(userList.get(0).getPassword())) {
             UserBean user = new UserBean();
             user.setPassword(password);
             user.setUserName(name);
             loginListner.loginSucces(user);
             return true;
         }else {
             loginListner.loginFail();
             return false;
         }

    }

}
