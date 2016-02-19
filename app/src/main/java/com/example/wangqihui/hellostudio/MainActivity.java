package com.example.wangqihui.hellostudio;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wangqihui.bean.UserBean;
import com.example.wangqihui.dao.UserDAO;
import com.example.wangqihui.presenter.UserPresenter;
import com.example.wangqihui.view.IUserView;

public class MainActivity extends AppCompatActivity implements IUserView{

    public Button button ;
    public EditText mUserName;
    public EditText mPassword;
    public UserPresenter presenter ;
    public Context context;

    public Button updateButton;
    public Button deleteButton;
    public Button insertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context =this;
        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void initView(){
        presenter =new UserPresenter(this);
        mUserName =(EditText)findViewById(R.id.user_edit);
        mPassword =(EditText)findViewById(R.id.password_edit);
        button =(Button)findViewById(R.id.login_btn);


        updateButton =(Button)findViewById(R.id.update_btn);
        insertButton =(Button)findViewById(R.id.insert_btn);
        deleteButton =(Button)findViewById(R.id.delete_btn);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDAO userDao =UserDAO.getInstance(context);
                UserBean bean =new UserBean();
                bean.setUserName("wangqihui");
                bean.setPassword("123456");
                if(userDao.insert(bean)){
                    showResult("插入成功");
                }else {
                    showResult("插入失败");
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDao =UserDAO.getInstance(context);
                UserBean bean =new UserBean();
                bean.setUserName("wangqihui");
                if(userDao.delete(bean)){
                    showResult("删除成功");
                }else {
                    showResult("删除失败");
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDao =UserDAO.getInstance(context);
                UserBean bean =new UserBean();
                bean.setUserName("wangqihui");
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null==mUserName.getText() || "".equals(mUserName.getText().toString())
                        || null==mPassword || "".equals(mPassword.getText().toString())){
                    showResult("用户名和密码不能为空");
                    clear();
                }else {
                    presenter.login(context ,mUserName.getText().toString(),mPassword.getText().toString());
                }

            }
        });
    }




    @Override
    public Boolean login(UserBean user) {
        return null;
    }

    @Override
    public void showResult(String message) {
        Toast.makeText(MainActivity.this
                ,message,Toast.LENGTH_LONG).show();
    }

    public void clear(){
        mUserName.setText("");
        mPassword.setText("");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
