package zzd.com.utils;

import zzd.com.Dao.AuthorityDao;
import zzd.com.Dao.RoleDao;
import zzd.com.Dao.UserDao;
import zzd.com.Login.UserModel;
import zzd.com.domain.Authority;
import zzd.com.domain.User;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 饿汉式单例工厂
 * 封装用户角色和权限信息
 */
public class DashBoard {
    //构造器私有化
    private DashBoard(){}
    //一个single实例
    private final static DashBoard single =new DashBoard();
    public static DashBoard getInstance(){
        if(single == null){
            synchronized (DashBoard.class){
                if(single == null){
                    new DashBoard();
                }
            }
        }
        return single;
    }
    //一个UserDao实例
    private static UserDao userDao = new UserDao();
    //一个RoleDao实例
    private static RoleDao roleDao = new RoleDao();
    //一个AuthorityDao实例
    private static AuthorityDao authorityDao = new AuthorityDao();
    //存放一个token对应UserModel的hashMap
    private static ConcurrentHashMap<String,UserModel> tokenMap = new ConcurrentHashMap<>();
    /**
     * 前端控制器拦截请求
     *
     */
    public UserModel handleAction(int i) {
        Integer[] ids = roleDao.getAuthoritiesId(i);
        System.out.println(Arrays.toString(ids));
        Authority[] authority = authorityDao.getAuthorities(ids);
        UserModel uM = new UserModel(authority);
        tokenMap.put(uM.getName()+"/"+uM.getToken(),uM);
        System.out.println("Put Those things As UserModel Name:"+uM.getName()+"/"+uM.getToken());
        return uM;
    }
    public void registUser(User user) {
        boolean flag = userDao.searchUserByUsername(user.getUsername());
        if(flag){
            System.out.println("同户名已存在");
        }else{
            UserModel.getUsermodel(authorityDao.getAuthorities(user.getRoles()));
            userDao.addUser(user);
        }
    }

    //传进来的token是usermodel.getName()+"/"+usermodel.getToken()或者""
    public UserModel ckeckToken(String token) {
        //System.out.println("Here The Coming in Token is:"+token);
        if (token.equals("")){
            System.out.println("Give new UserModel to customer");
            return single.handleAction(0);
        }else if(token.contains("NotLoginUser")){
            System.out.println("Run Here Where NotLoginUser Is Coming::"+token);
            return tokenMap.get(token);
        }else if(token.equals("SuperAdmin"+token)){//超级管理员
            return tokenMap.get(token);
        }else{
            System.out.println("Something is Wrong");
            return single.handleAction(0);
        }
    }
}