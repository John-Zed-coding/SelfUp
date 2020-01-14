package zzd.com.Login;

import zzd.com.domain.Authority;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射创建对象
 */
public class UserModel {
    private String name;
    private String token;
    //存放一份hashmap
    private static ConcurrentHashMap<String, String> um = new ConcurrentHashMap<String, String>();
    //为初次访问的用户创建新的UserModel对象
    public UserModel(Authority[] authority) {
        String s = new String();
        for(int i=0;i<authority.length;i++){
            s+=authority[i].getMethodName()+" ";
        }
        this.setToken(s);
        if (3==authority.length){
            this.setName("NotLoginUser");
        }else if(17==authority.length){
            this.setName("SuperAdmin");
        }
        um.put(name,s);
    }

    //通过反射对外提供无参构造
    public static UserModel getUsermodel() {
        UserModel usermodel = null;
        try{
            Class c = Class.forName("zzd.com.Login.UserModel");
            usermodel = (UserModel) c.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return usermodel;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getToken() {
        if(ApplicationController.isNull(token)){
            return "";
        }else{
            return token;
        }
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
