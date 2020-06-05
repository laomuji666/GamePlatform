package com.lmj.gameplatform.mapping;

import com.lmj.gameplatform.model.Notify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lmj.gameplatform.model.account.AccountJson.BAD_REQUEST;

@RestController
public class NotifyMapping {
    @Autowired
    private Notify notify;

    @RequestMapping("/notify/get")
    private String getNotify(){
        return notify.getNotify();
    }

    @RequestMapping("/notify/set")
    private String setNotify(String username,String password,String data){
        if (username==null || password==null ||notify==null){
            return BAD_REQUEST;
        }
        return notify.setNotify(username, password, data);
    }
}
