package com.lmj.gameplatform.mapping;

import com.lmj.gameplatform.model.account.Account;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    AccountMapping
    接口如下:
    /account/code/key:获取验证码key
    /account/code/picture:获取验证码图片
    /account/register:注册
    /account/login:登陆
    /account/update:更新在线时间
    /account/on-line:在线状态
    /account/off-line:手动离线
*/

@RestController
public class AccountMapping {
    @Autowired
    private Account account=null;

    //如果请求参数不全,会返回该json信息
    private static final String BAD_REQUEST="{\"str\":\"未正确提供所需参数\"}";

    @RequestMapping("/account/code/key")
    public Long getCodeKey(){
        return account.getCodeKey();
    }

    @RequestMapping(value = "/account/code/picture",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getCodePicture(Long codeKey){
        if (codeKey==null) return null;
        return account.getCodePicture(codeKey);
    }

    @RequestMapping("/account/register")
    private String register(String username,String password,Long codeKey,String codeStr){
        if (username==null || password==null ||codeKey==null ||codeStr==null){
            return BAD_REQUEST;
        }
        return account.register(username,password,codeKey,codeStr);
    }

    @RequestMapping("/account/login")
    private String login(String username,String password,Long codeKey,String codeStr){
        if (username==null || password==null ||codeKey==null ||codeStr==null){
            return BAD_REQUEST;
        }
        return account.login(username,password,codeKey,codeStr);
    }

    @RequestMapping("/account/all")
    private String all(){
        return account.getAllUser();
    }
}
