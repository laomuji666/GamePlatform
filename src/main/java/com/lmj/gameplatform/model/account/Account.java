package com.lmj.gameplatform.model.account;

import static com.lmj.gameplatform.model.account.AccountJson.*;
import com.lmj.gameplatform.model.account.code.CodeController;
import com.lmj.gameplatform.model.account.mysql.MySQLController;
import com.lmj.gameplatform.model.account.onlineuser.OnlineUserController;
import com.lmj.gameplatform.model.account.onlineuser.OnlineUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


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

    /*
        供Mapping使用的接口
     */

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
        String regex = "^[a-z0-9A-Z]+$";
        if (username.matches(regex)==false || password.matches(regex)==false){
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
        String regex = "^[a-z0-9A-Z]+$";
        if (username.matches(regex)==false || password.matches(regex)==false){
            return REGISTER_FALSE_ILLEGAL;
        }
        if (mySQLController.selectUser(username, password)){
            String onlineKey=onlineUserController.addOnlineUser(username, codeKey);
            return LOGIN_TRUE_BEGIN+onlineKey+LOGIN_TRUE_END;
        }else {
            return LOGIN_FALSE_WRONG;
        }
    }

    //登陆后获取信息
    public String online(String username,String onlineKey){
        OnlineUserData data=getOnlineUserData(username, onlineKey);
        if (data!=null) return data.getLobbyInfo();
        return null;
    }

    //手动离线
    public String offline(String username,String onlineKey){
        if (onlineUserController.delOnlineUser(username,onlineKey)){
            return OFFLINE_TRUE;
        }else {
            return OFFLINE_FALSE;
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

    /*
        供GameLobby使用的接口
     */

    //成功返回用户信息,失败返回null
    public OnlineUserData getOnlineUserData(String username, String onlineKey){
        return onlineUserController.getOnlineUserData(username, onlineKey);
    }



}
