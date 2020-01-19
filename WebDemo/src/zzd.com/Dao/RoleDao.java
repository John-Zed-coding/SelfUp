package zzd.com.Dao;

import zzd.com.domain.Role;

import java.util.HashMap;

/**
 * 定义三个Role
 *
 */
public class RoleDao {
    /*private Integer id;
    private String username;
    private Integer[] Authority;
    private String describe;*/
    //统一的set方法
    /*public void set(String name ,String value){
        switch (name){
            case "username":
                this.username=value;
                break;
            case "describe":
                this.describe=value;
        }
    }*/
    private static Role ro = new Role();
    private static HashMap<Integer,Role> roles = new HashMap();
    static {
        ro.setId(0);
        ro.setUsername("UserNotLogin");
        ro.setDescribe("未登录的用户初设权限");
        Integer[] ids = {0,9,12};
        ro.setAuthority(ids);
        roles.put(0,ro);

        ro = new Role();
        ro.setId(1);
        ro.setUsername("SuperAdmin");
        ro.setDescribe("超级管理员权限");
        ids = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        ro.setAuthority(ids);
        roles.put(1,ro);
    }
    //获取Authority权限数组
    public Integer[] getAuthoritiesId(int x) {
        Role o =  roles.get(x);
        return o.getAuthority();
    }
}

