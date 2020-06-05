package com.lmj.gameplatform.model;

import org.springframework.stereotype.Controller;

import static com.lmj.gameplatform.model.account.AccountJson.NOTIFY_SET_FALSE;
import static com.lmj.gameplatform.model.account.AccountJson.NOTIFY_SET_TRUE;

@Controller
public class Notify {

    private String notifyData="无公告";

    //设置公告,需要管理员账户
    public String setNotify(String username,String password,String data){
        if (Admin.verifyAdmin(username, password)){
            //转换换行
            data=data.replace("<br/>","\r\n");
            data=data.replace("<br>","\r\n");
            this.notifyData=data;
            return NOTIFY_SET_TRUE;
        }else {
            return NOTIFY_SET_FALSE;
        }
    }

    //获取公告
    public String getNotify(){
        return notifyData;
    }
}
