package com.lmj.gameplatform.model.account;

/*
    AccountJson
    和Account有关的Json信息都在这里
 */
public class AccountJson {
    //注册
    public static final String REGISTER_TRUE="{\"id\":\"0\",\"str\":\"注册成功\"}";
    public static final String REGISTER_FALSE_EXISTS="{\"id\":\"1\",\"str\":\"注册失败,该账号已被注册\"}";
    public static final String REGISTER_FALSE_CODE="{\"id\":\"2\",\"str\":\"注册失败,验证码错误\"}";
    public static final String REGISTER_FALSE_ILLEGAL="{\"id\":\"3\",\"str\":\"注册失败,账号或密码不符合规定\"}";

    //登陆
    public static final String LOGIN_TRUE_BEGIN="{\"id\":\"0\",\"str\":\"";
    public static final String LOGIN_TRUE_END="\"}";
    public static final String LOGIN_FALSE_WRONG="{\"id\":\"1\",\"str\":\"登陆失败,账号或密码错误\"}";
    public static final String LOGIN_FALSE_CODE="{\"id\":\"2\",\"str\":\"登陆失败,验证码错误\"}";

    //更新在线时间
    public static final String UPDATE_TRUE="{\"id\":\"0\",\"str\":\"在线时间更新成功\"}";
    public static final String UPDATE_FALSE="{\"id\":\"1\",\"str\":\"在线时间更新失败\"}";
}
