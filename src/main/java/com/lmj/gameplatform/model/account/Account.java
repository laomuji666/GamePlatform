package com.lmj.gameplatform.model.account;

import static com.lmj.gameplatform.model.account.AccountJson.*;
import com.lmj.gameplatform.model.account.code.CodeController;
import com.lmj.gameplatform.model.account.mysql.MySQLController;
import com.lmj.gameplatform.model.account.onlineuser.OnlineUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



/*
    Account
    处理该包下所有功能,将多个模块合并处理
    提供接口供外部调用
*/

@Controller
public class Account {
    @Autowired
    private MySQLController mySQLController=null;
    @Autowired
    private CodeController codeController=null;
    @Autowired
    private OnlineUserController onlineUserController=null;

    //获取codeKey
    public Long getCodeKey(){
        return codeController.getCodeKey();
    }

    //获取codePicture
    public byte[] getCodePicture(Long codeKey){
        return codeController.getCodePicture(codeKey);
    }

    //注册
    public String register(String username,String password,Long codeKey,String codeStr){
        if (codeController.verifyCode(codeKey,codeStr)==false){
            return REGISTER_FALSE_CODE;
        }
        if (username.length()<6||password.length()<6 ||username.length()>16||password.length()>16){
            return REGISTER_FALSE_ILLEGAL;
        }
        if (mySQLController.insertUser(username, password)){
            return REGISTER_TRUE;
        }else {
            return REGISTER_FALSE_EXISTS;
        }
    }

    //登陆
    public String login(String username,String password,Long codeKey,String codeStr){
        if (codeController.verifyCode(codeKey,codeStr)==false){
            return AccountJson.LOGIN_FALSE_CODE;
        }
        if (mySQLController.selectUser(username, password)){
            String onlineKey=onlineUserController.addOnlineUser(username, codeKey);
            return LOGIN_TRUE_BEGIN+onlineKey+LOGIN_TRUE_END;
        }else {
            return LOGIN_FALSE_WRONG;
        }
    }

    //更新在线时间
    public String update(String username,String onlineKey){
        if (onlineUserController.updateOnlineUserTime(username, onlineKey)){
            return UPDATE_TRUE;
        }else {
            return UPDATE_FALSE;
        }
    }



    public String getAllUser(){
        return mySQLController.getUsers();
    }
}
