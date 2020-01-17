package zzd.com.Login;

import zzd.com.Dao.UserDao;
import zzd.com.domain.Authority;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射创建对象
 */
public class UserModel {
    private String name;//
    private String token;
    static String s = "";
    //存放一份hashmap
    private static ConcurrentHashMap<String, String> um = new ConcurrentHashMap<String, String>();
    //为初次访问的用户创建新的UserModel对象
    public UserModel(Authority[] aa) {
        for(int i=0;i<aa.length;i++){
            s+=aa[i].getMethodName()+"/";
        }
        System.out.println("s="+s);
        this.name = "NotLoginUser";
        this.token = getRandomToken();
        um.put(token,s);
    }

    //通过反射对外提供无参构造
    public static UserModel getUsermodel(Authority[] aa) {
        UserModel usermodel = null;
        try{
            Constructor<UserModel> constructor = UserModel.class.getConstructor(String.class,String.class);
            constructor.setAccessible(true);
            usermodel = constructor.newInstance("","");
            for(int i=0;i<aa.length;i++){
                s+=aa[i].getMethodName()+"/";
            }
            usermodel.setToken(s);
            if (3==aa.length){
                usermodel.setName(UserDao.getUserMsg().getUsername());
            }else if(17==aa.length){
                usermodel.setName(UserDao.getUserMsg().getUsername());
            }
            /*Class c = Class.forName("zzd.com.Login.UserModel");
            Constructor constructor = c.getConstructor(Authority[].class);
            usermodel = (UserModel) constructor.newInstance();*/
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
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
    /**
     * 这里面是一个随机生成token的方法
     */
    public static final String getRandomToken(){
        Random random = new Random();
        char[] ku = ("0123456789abcdefghijklmnopqrstuvwxyz"+"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        char[] rand = new char[32];
        for(int i =0;i<30;i++){
            rand[i] = ku[random.nextInt(65)];
        }
        return new String(rand).trim();
    }
}
