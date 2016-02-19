package com.example.wangqihui.presenter;

import android.content.Context;

import com.example.wangqihui.bean.UserBean;
import com.example.wangqihui.model.IUserModel;
import com.example.wangqihui.model.OnLoginListner;
import com.example.wangqihui.model.UserModel;
import com.example.wangqihui.view.IUserView;

/**
 * Created by wangqihui on 2016/1/8.
 */
public class UserPresenter {

    public IUserView userView;
    public IUserModel model;

    public UserPresenter(IUserView view){
        userView=view;
        model=new UserModel();
    }

    public void login(Context context ,String mUsername,String mPassword){
        model.login(context ,mUsername, mPassword, new OnLoginListner() {
            @Override
            public boolean loginSucces(UserBean user) {
                userView.showResult("用户"+user.getUserName()+":登陆成功");
                userView.clear();
                return true;
            }

            @Override
            public boolean loginFail() {
                userView.showResult("登陆失败请重新登陆");
                userView.clear();
                return false;
            }
        });
    }
}
