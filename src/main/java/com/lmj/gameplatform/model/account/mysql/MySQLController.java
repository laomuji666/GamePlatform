package com.lmj.gameplatform.model.account.mysql;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.stereotype.Controller;

import java.sql.*;

/*
    MySQLController
    功能:自动连接数据库,如果库或表不存在则自动创建
    接口如下:
    insertUser:注册账号
    selectUser:登陆账号,成功返回用户id,失败返回-1
 */

@Controller
public class MySQLController {
    //数据库基本信息
    private static String databaseName="LMJGamePlatform";
    private static String tableName="users";
    private static String table=tableName+"(" +
            "username varchar(40) not null unique," +
            "password varchar(40) not null" +
            ");";

    private Connection connection=null; //Connection 用于连接数据库

    private Statement statement=null; //Statement 用于对数据库操作

    //首先连接mysql
    //然后进入对应的数据库里
    MySQLController(){
        connectMySQL();
        useDatabase();
        checkTable();
    }

    //连接MySQL
    private void connectMySQL(){
        String username="root",password="root";
        try {
            //连接数据库
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC", username, password);
            //实例化Statement对象
            statement= connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //进入数据库 若不存在则自动创建对应数据库
    private void useDatabase(){
        try {
            statement.execute("use "+databaseName);
        } catch (SQLException e) {
            try {
                //数据库不存在 创建数据库
                statement.execute("create database "+databaseName);
                System.out.println("数据库不存在,已自动创建 库名"+databaseName);
                //创建数据库后再次进入
                statement.execute("use "+databaseName);
            } catch (SQLException ex) {
                //数据库创建失败
                ex.printStackTrace();
            }
        }
    }

    //检查表是否存在 不存在自动创建
    private void checkTable(){
        try {
            String s="Create Table If Not Exists "+table;
            statement.execute(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //增加账号密码
    //成功返回true,失败返回false
    public boolean insertUser(String username,String password){
        String insertStr="insert into "+
                tableName+"(username,password) " +
                "values('"+username+"','"+password+"');";
        try {
            statement.execute(insertStr);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    //查询账号密码
    //成功返回true,失败返回false
    public boolean selectUser(String username,String password){
        String selectStr="select * from "+tableName+
                " where username='"+username+
                "' and password='"+password+"';";
        try {
            ResultSet resultSet = statement.executeQuery(selectStr);
            if (resultSet.next()){
                resultSet.close();
                return true;
            }
        } catch (SQLException e) {
            //查询失败
        }
        return false;
    }

    //获取所有账号密码
    public String getUsers(){
        String rtStr="";
        String sql="select * from "+tableName;
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String data=
                        resultSet.getString(1)+" "+
                                resultSet.getString(2)+" "+
                                "<br/>";;
                rtStr+=data;
            }
            resultSet.close();
        } catch (SQLException e) {
            rtStr="查询异常";
        }
        return rtStr;

    }

}