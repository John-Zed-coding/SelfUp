package zzd.com.Dao;

import zzd.com.domain.Authority;

import java.util.HashMap;

public class AuthorityDao{
    private static Authority auth;
    private static HashMap<Integer,Authority> an = new HashMap();
    static String[] s = new String[]{"UserLogIn","UserLogOut","GetAuthorityList",
            "GetSingleAuthorityMsg","AddAuthority","ChangeAuthority","DeleteAuthority",
            "GetUserList", "AddUser","ChangeUserMsg","DeleteUser","GetRoleList",
            "GetSingleRoleMsg","AddRole","ChangeRole","DeleteRole"};
    //静态代码块 - 设置Authority[]内存储的值
    static {
        for(int i =0;i<s.length;i++){
            //浅拷贝创建对象
            auth = Authority.getAuthority();
            auth.setMethodName(s[i]);
            an.put(i,auth);
        }
    }
    public Authority[] getAuthorities(Integer[] ids) {
        Authority[] aaa = new Authority[ids.length];
        for(int i =0;i<ids.length;i++){
            aaa[i]= an.get(ids[i]);
        }
        return aaa;
    }
}
