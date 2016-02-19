package com.example.wangqihui.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.wangqihui.bean.UserBean;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by wangqihui on 2016/1/12.
 */
public class UserDAO {


    private static final int VERSION=1;
    private static final String DB_NAME ="wangqihui";
    private static UserDAO userDao;
    private  SQLiteDatabase database;


        private  UserDAO(Context context){
             DataBaseHelper dbHelper =new DataBaseHelper(context,DB_NAME,null,VERSION);
             database=dbHelper.getWritableDatabase();
        }

        public  synchronized static  UserDAO getInstance(Context context){
            if(null ==userDao){
                userDao=new UserDAO(context);
            }

            return userDao;
        }

     public boolean insert(UserBean user){

        if(null != user){
            ContentValues values =new ContentValues();
            if(null !=user.getUserName() && !"".equals(user.getUserName())){
                values.put("userName", user.getUserName());
            }
            if(null!= user.getPassword() && !"".equals(user.getPassword())){
                values.put("password" ,user.getPassword());
            }

            if(values.size()>0){
            //    SQLiteDatabase database =this.getWritableDatabase();
                database.insert("user_table",null ,values);
                return true;
            }
        }
        return false;
    }

    public boolean update(UserBean user){
        if(null != user) {
            ContentValues values = new ContentValues();
            if (null != user.getUserName() && !"".equals(user.getUserName())) {
                values.put("userName", user.getUserName());
            }
            if (null != user.getPassword() && !"".equals(user.getPassword())) {
                values.put("password", user.getPassword());
            }
            if (values.size() > 0) {
         //       SQLiteDatabase database = this.getWritableDatabase();
                database.update("user_table", values, "id=?", new String[]{user.getUserId().toString()});
                return true;
            }
        }
        return false;
    }

    public boolean delete(UserBean user){

        StringBuilder sql =new StringBuilder(32);
        sql.append( " delete from user_table where 1=1  " ) ;
        if(null != user) {
            List values = new ArrayList();
            if (null != user.getUserName() && !"".equals(user.getUserName())) {
                values.add(user.getUserName());
                sql.append(" and userName= ?  ");
            }
            if (null != user.getPassword() && !"".equals(user.getPassword())) {
                values.add( user.getPassword());
                sql.append(" and password= ? ");
            }
            if(null !=user.getUserId()){
                values.add(user.getUserId());
                sql.append(" and  user_id= ? ");
            }
            if (values.size() > 0) {
           //     SQLiteDatabase database = this.getWritableDatabase();
                database.execSQL(sql.toString(), values.toArray());
                return true;
            }
        }
        return false;
    }


    public List<UserBean> query(UserBean user){
        StringBuilder sql =new StringBuilder(32);
        sql.append(" select user_id, userName ,password from user_table where 1=1 ");
        if(null != user) {
            List<String> values = new ArrayList<String>();
            if(null !=user.getUserId()){
                sql.append(" and user_id=? ");
                values.add(user.getUserId().toString());
            }
            if(null!=user.getUserName() && !"".equals(user.getUserName())){
                sql.append(" and userName=? ");
                values.add(user.getUserName());
            }
            if(null!=user.getPassword() && !"".equals(user.getPassword())){
                sql.append(" and password=? ");
                values.add(user.getPassword());
            }
            if (values.size() > 0) {
            //    SQLiteDatabase database = this.getReadableDatabase();
                Cursor cursor = database.rawQuery(sql.toString(), values.toArray(new String[values.size()]));

                List<UserBean> userList =new ArrayList<UserBean>();
                while(cursor.moveToNext()){
                    UserBean bean =new UserBean();
                    bean.setUserId(cursor.getLong(0));
                    bean.setUserName(cursor.getString(1));
                    bean.setPassword(cursor.getString(2));

                    userList.add(bean);
                }

                return userList;
            }
        }
        return null;
    }
}
