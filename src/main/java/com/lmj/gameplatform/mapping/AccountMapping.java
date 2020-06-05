package com.lmj.gameplatform.mapping;

import com.lmj.gameplatform.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lmj.gameplatform.model.account.AccountJson.BAD_REQUEST;

/*
    AccountMapping
    接口如下:
    /account/code/key:获取验证码key
    /account/code/picture:获取验证码图片
    /account/register:注册
    /account/login:登陆
    /account/update:更新在线时间
    /account/on-line:在线后请求,获取账号信息
    /account/off-line:离线前请求,手动离线
*/

@RestController
public class AccountMapping {
    private static final String url="/account";
    @Autowired
    private Account account=null;

    @RequestMapping(url+"/code/key")
    public Long getCodeKey(){
        return account.getCodeKey();
    }

    @RequestMapping(value = url+"/code/picture",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getCodePicture(Long codeKey){
        if (codeKey==null) return null;
        return account.getCodePicture(codeKey);
    }

    @PostMapping(url+"/register")
    private String register(String username,String password,Long codeKey,String codeStr){
        if (username==null || password==null ||codeKey==null ||codeStr==null){
            return BAD_REQUEST;
        }
        return account.register(username,password,codeKey,codeStr);
    }

    @PostMapping(url+"/login")
    private String login(String username,String password,Long codeKey,String codeStr){
        if (username==null || password==null ||codeKey==null ||codeStr==null){
            return BAD_REQUEST;
        }
        return account.login(username,password,codeKey,codeStr);
    }

    @PostMapping(url+"/update")
    private String update(String username,String onlineKey){
        if (username==null ||onlineKey==null) return BAD_REQUEST;
        return account.update(username, onlineKey);
    }

    @PostMapping(url+"/online")
    private String online(String username,String onlineKey){
        if (username==null ||onlineKey==null) return BAD_REQUEST;
        String info=account.online(username, onlineKey);
        if (info==null)return BAD_REQUEST;
        return info;
    }

    @PostMapping(url+"/offline")
    private String offline(String username,String onlineKey){
        if (username==null ||onlineKey==null) return BAD_REQUEST;
        return account.offline(username, onlineKey);
    }

}
