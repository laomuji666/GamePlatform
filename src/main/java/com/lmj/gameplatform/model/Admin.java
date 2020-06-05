package com.lmj.gameplatform.model;


public class Admin {
    private static final String adminUsername="laomuji";
    private static final String adminPassword="991023";

    public static final boolean verifyAdmin(String username,String password){
        if (username==null||password==null)return false;
        if (adminUsername.equals(username)&&adminPassword.equals(password)){
            return true;
        }else {
            return false;
        }
    }
}
