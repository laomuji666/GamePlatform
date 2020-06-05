package com.lmj.gameplatform.model.account;

/*
    AccountJson
    和Account有关的Json信息都在这里
 */
public class AccountJson {

    //如果请求参数不全,会返回该json信息
    public static final String BAD_REQUEST="{\"id\":\"-1\",\"str\":\"未正确提供参数\"}";
    //注册
    public static final String REGISTER_TRUE="{\"id\":\"0\",\"str\":\"注册成功\"}";
    public static final String REGISTER_FALSE_EXISTS="{\"id\":\"1\",\"str\":\"注册失败,该账号已被注册\"}";
    public static final String REGISTER_FALSE_CODE="{\"id\":\"2\",\"str\":\"注册失败,验证码错误\"}";
    public static final String REGISTER_FALSE_ILLEGAL="{\"id\":\"3\",\"str\":\"注册失败,账号或密码长度6-16且只能为大小写字母或数字\"}";

    //登陆
    public static final String LOGIN_TRUE_BEGIN="{\"id\":\"0\",\"str\":\"";
    public static final String LOGIN_TRUE_END="\"}";
    public static final String LOGIN_FALSE_WRONG="{\"id\":\"1\",\"str\":\"登陆失败,账号或密码错误\"}";
    public static final String LOGIN_FALSE_CODE="{\"id\":\"2\",\"str\":\"登陆失败,验证码错误\"}";

    //手动离线
    public static final String OFFLINE_TRUE="{\"id\":\"0\",\"str\":\"离线成功\"}";
    public static final String OFFLINE_FALSE="{\"id\":\"1\",\"str\":\"离线失败\"}";


    //更新在线时间
    public static final String UPDATE_TRUE="{\"id\":\"0\",\"str\":\"在线时间更新成功\"}";
    public static final String UPDATE_FALSE="{\"id\":\"1\",\"str\":\"在线时间更新失败\"}";

    //获取在线状态
    //lobbyId请查看GameLobbyId.java
    // "{\"id:\":\""+lobbyId+"\",\"str\":\""+lobbyName+"\"}";

    //设置通知,获取通知
    public static final String NOTIFY_SET_TRUE="{\"id\":\"0\",\"str\":\"Notify设置成功\"}";
    public static final String NOTIFY_SET_FALSE="{\"id\":\"1\",\"str\":\"Notify设置失败,请输入正确的管理员账户\"}";
}
