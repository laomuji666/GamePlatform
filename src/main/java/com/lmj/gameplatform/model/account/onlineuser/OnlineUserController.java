package com.lmj.gameplatform.model.account.onlineuser;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
public class OnlineUserController {

    private Map<String,OnlineUserData>onlineUserDataMap=new HashMap<>(); //用户名映射用户

    private static final long updateTimeValid = 30000;//更新在线时间有效期

    //增加在线用户,返回onlineKey
    public String addOnlineUser(String username,Long codeKey){
        OnlineUserData data = onlineUserDataMap.get(username);
        if (data==null){
            //如果在线用户中不存在该映射,则增加一个映射
            data=new OnlineUserData(username, DigestUtils.md5DigestAsHex(codeKey.toString().getBytes()));
            onlineUserDataMap.put(username,data);
        }else {
            //如果已经在线用户中已存在该映射,则更新md5值
            data.setOnlineKey(DigestUtils.md5DigestAsHex(codeKey.toString().getBytes()));
        }
        return data.getOnlineKey();
    }

    //移除在线用户,成功返回true,失败返回false
    public boolean delOnlineUser(String username,String onlineKey){
        OnlineUserData data = onlineUserDataMap.get(username);
        if (data==null)return false;
        if (onlineKey.equals(data.getOnlineKey())==false)return false;
        return onlineUserDataMap.remove(username,data);
    }

    //更新在线用户的在线时间
    public boolean updateOnlineUserTime(String username,String onlineKey){
        OnlineUserData data = onlineUserDataMap.get(username);
        if (data==null) return false;//未找到映射
        if (data.getOnlineKey().equals(onlineKey)==false)return false;//不符合验证
        data.updateTime();//更新时间
        return true;
    }

    //定时移除指定时间未更新在线时间的用户
    @Scheduled(fixedRate = 5000)
    private void removeOnlineUser(){
        long validTime=System.currentTimeMillis()-updateTimeValid;
        Set<String> keySet = onlineUserDataMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String key =iterator.next();
            if (onlineUserDataMap.get(key).getUpdateTime()<validTime) {
                iterator.remove();
                System.out.println("玩家离线:"+key);
            }
        }
    }

    //获取在线用户数据
    public OnlineUserData getOnlineUserData(String username,String onlineKey){
        OnlineUserData data = onlineUserDataMap.get(username);
        if (data==null)return null;
        if (data.getOnlineKey().equals(onlineKey))return data;
        else return null;
    }
}
