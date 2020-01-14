package zzd.com.utils;

import zzd.com.Dao.AuthorityDao;
import zzd.com.Dao.RoleDao;
import zzd.com.Dao.UserDao;
import zzd.com.Login.ApplicationController;
import zzd.com.Login.UserModel;
import zzd.com.domain.Authority;
import zzd.com.domain.User;

import java.util.Arrays;

/**
 * 饿汉式单例工厂
 * 封装用户角色和权限信息
 */
public class DashBoard {
    //构造器私有化
    private DashBoard(){}
    //一个single实例
    private final static DashBoard single =new DashBoard();
    //一个UserDao实例
    private static UserDao userDao = new UserDao();
    //一个RoleDao实例
    private static RoleDao roleDao = new RoleDao();
    //一个AuthorityDao实例
    private static AuthorityDao authorityDao = new AuthorityDao();

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
    /**
     * 前端控制器拦截请求
     * @param
     * @return
     */
    public UserModel handleAction() {
        //反射UserModel对象
        if(ApplicationController.isNull(UserDao.getUserMsg().getUsername())){
            return single.getUserModel();//未登录则创建对象实例
        }else
            return UserModel.getUsermodel();//已登录则通过反射创建对象
    }
    private UserModel getUserModel(){
        Integer[] ids = roleDao.getAuthoritiesId(0);//这是未登录用户
        System.out.println(Arrays.toString(ids));
        Authority[] Authority = authorityDao.getAuthorities(ids);
        UserModel uM = new UserModel(Authority);
        return uM;
    }

    public void registUser(User user) {
        boolean flag = userDao.searchUserByUsername(user.getUsername());
        if(flag){
            System.out.println("同户名已存在");
        }else{
            new UserModel(authorityDao.getAuthorities(user.getRoles()));
            userDao.addUser(user);
        }
    }
}