package com.lmj.gameplatform.model.account;

import com.lmj.gameplatform.model.account.code.CodeController;
import com.lmj.gameplatform.model.account.mysql.MySQLController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/*
    Account
    整合该包下所有功能,返回对应的json信息
*/

@Controller
public class Account {
    @Autowired
    private MySQLController mySQLController=null;
    @Autowired
    private CodeController code=null;

    //获取codeKey
    public Long getCodeKey(){
        return code.getCodeKey();
    }

    //获取codePicture
    public byte[] getCodePicture(Long codeKey){
        return code.getCodePicture(codeKey);
    }

    //注册
    public String register(String username,String password,Long codeKey,String codeStr){
        if (code.verifyCode(codeKey,codeStr)==false){
            return AccountJson.REGISTER_FALSE_CODE;
        }
        if (username.length()<6||password.length()<6 ||username.length()>16||password.length()>16){
            return AccountJson.REGISTER_FALSE_ILLEGAL;
        }
        if (mySQLController.insertUser(username, password)){
            return AccountJson.REGISTER_TRUE;
        }else {
            return AccountJson.REGISTER_FALSE_EXISTS;
        }
    }

    //登陆
    public String login(String username,String password,Long codeKey,String codeStr){
        if (code.verifyCode(codeKey,codeStr)==false){
            return AccountJson.LOGIN_FALSE_CODE;
        }
        if (mySQLController.selectUser(username, password)){
            return AccountJson.LOGIN_TRUE;
        }else {
            return AccountJson.LOGIN_FALSE_WRONG;
        }
    }

    public String getAllUser(){
        return mySQLController.getUsers();
    }
}
