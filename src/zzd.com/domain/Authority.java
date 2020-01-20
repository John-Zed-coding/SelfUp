package zzd.com.domain;

import javax.crypto.Mac;
import java.util.ArrayList;

/**
 * 定义角色权限
 */
public class Authority implements Cloneable{
    private String roleName;//角色名
    private String require;//页面名或者操作功能名
    private String httpMethod;//请求操作
    private String methodName;//权限名
    private String describe;
    private static Authority authority = new Authority();
    public static Authority getAuthority(){
        try{
            return (Authority) authority.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRequire() {
        return require;
    }

    /**
     * 设置权限对应的请求
     * @param path
     */
    public void setRequire(String path) {
        this.require = require;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * 设置权限对应的http方法名 - 增删查改
     * @param httpMethod
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    /**
     * 权限名
     * @param methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "roleName='" + roleName + '\'' +
                ", require='" + require + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", methodName='" + methodName + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
