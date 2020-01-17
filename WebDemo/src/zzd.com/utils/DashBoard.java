package zzd.com.utils;

import zzd.com.Dao.AuthorityDao;
import zzd.com.Dao.RoleDao;
import zzd.com.Dao.UserDao;
import zzd.com.Login.UserModel;
import zzd.com.domain.Authority;
import zzd.com.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        System.out.println("Put Those things As tokenMap Name:"+uM.getName()+"/"+uM.getToken());
        return uM;
    }
    public void registUser(User user) {
        boolean flag = userDao.searchUserByUsername(user.getUsername());
        if(!flag){
            UserModel.getUsermodel(user);
            userDao.addUser(user);//应该保存token和user的对应关系
        }else{
            System.out.println("同户名已存在");
        }
    }

    /**
     * 检查用户权限，决定UserModel的创建方式
     * @param token
     * @return
     */
    public UserModel ckeckToken(String token) {
        if (token.equals("")){
            System.out.println("Give new UserModel to customer");
            return single.handleAction(0);
        }else if(token.contains("NotLoginUser")){
            System.out.println("Run Here Where NotLoginUser Is Coming::"+token);
            return tokenMap.get(token);
        }else if(token.contains("SuperAdmin")){//超级管理员
            return tokenMap.get(token);
        }else if(token.contains("NormalUser")){
            System.out.println("Yet To Coding");
            return tokenMap.get(token);
        }else{
            System.out.println("Something is Wrong");
            return single.handleAction(0);
        }
    }
    //权限校验，可以和上面的整合
    public boolean haveRight(String token){
        //首先将用户名和UserDao中存储的用户名做校验，确定用户登录状态
        //未登录用户的权限校验
        if(token.contains("NotLoginUser")){//未登录用户
            return true;
        }else if(token.contains("SuperAdmin")){//登录用户的权限校验
            return true;
        }else
            return false;
    }
    //注册功能，首先进行权限校验
    public void register(HttpServletRequest request, HttpServletResponse response, UserModel um) throws ServletException, IOException {
        //当用户请求注册时：
        if(single.haveRight(um.getToken())){//校验是否可以注册，这里是可以注册
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String nickname = request.getParameter("nickname");
            String email = request.getParameter("email");
            Integer[] roles = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
            if(isNull(username)){
                request.setAttribute("msg","用户名不能为空");
                request.getRequestDispatcher("/regist.jsp").forward(request, response);
                return;
            }
            if(isNull(password)){
                request.setAttribute("msg","密码不能为空");
                request.getRequestDispatcher("/regist.jsp").forward(request, response);
                return;
            }
            if(isNull(password2)&&!password.equals(password2)){
                request.setAttribute("msg","用户名不能为空，且必须和第一次输入的一样");
                request.getRequestDispatcher("/regist.jsp").forward(request, response);
                return;
            }
            if(isNull(nickname)){
                request.setAttribute("msg","昵称不能为空");
                request.getRequestDispatcher("/regist.jsp").forward(request, response);
                return;
            }
            String reg = "\\w+@\\w+(\\.\\w+)+";//邮箱格式
            if(isNull(email)&&!email.matches(reg)){
                request.setAttribute("msg","请输入正确的邮箱格式");
                request.getRequestDispatcher("/regist.jsp").forward(request, response);
                return;
            }
            User user = new User(username, getMD5(password), nickname, email, "",
                    roles, request.getHeader("x-forwarded-for"), null,
                    null, request.getParameter("date"), false);
            single.registUser(user);
        }else{//没有注册权限
            System.out.println("您没有注册权限，详情请联系管理员");
        }
    }
    //登录
    public void login(HttpServletRequest request, HttpServletResponse response, UserModel userModel) {
    }
    //非空校验
    public static boolean isNull(String str){return str ==null||"".equals(str);}
    //MD5加密
    public static String getMD5(String str){
        byte[] secretBytes ;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}