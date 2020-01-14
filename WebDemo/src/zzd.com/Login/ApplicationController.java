package zzd.com.Login;

import zzd.com.domain.User;
import zzd.com.utils.DashBoard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 前端控制器 - 分发任务请求
 * 按照不同的请求，调用不同方法
 */
public class ApplicationController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //乱码处理
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        //获取请求token
        String token = request.getParameter("token");
        UserModel usermodel = DashBoard.getInstance().handleAction();
        if(0==Check(usermodel,token)){//用户初次访问
            response.addHeader("Token",
                    usermodel.getName()+"_"+usermodel.getToken());
        }else if(1==Check(usermodel,token)){//超级管理员

        }else{//普通用户
        }
    }
    //校验登录状态
    public int Check(UserModel um, String token){
        //用户未登录 - 初次访问
        if (isNull(token)||!token.equals("NotLoginUser_"+um.getToken())||token=="null_null"){
            System.out.println("Give new UserModel to customer");
            return 0;
        }else if(token.equals("SuperAdmin_"+token)){//超级管理员
            System.out.println("Test success");
            return 1;
        }else//普通用户
            return 2;
    }
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
    //注册功能，首先进行权限校验
    public void Register(HttpServletRequest request,HttpServletResponse response,UserModel um) throws ServletException, IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        //当用户请求注册时：
        if(haveRight(um.getToken())){//校验是否可以注册，这里是可以注册
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
            DashBoard.getInstance().registUser(user);
        }else{//没有注册权限
            System.out.println("您没有注册权限，详情请联系管理员");
        }
    }
    //非空校验
    public static boolean isNull(String str){return str ==null||"".equals(str);}
    //权限校验
    private static boolean haveRight(String token){
        //首先将用户名和UserDao中存储的用户名做校验，确定用户登录状态
        //未登录用户的权限校验
        if(token.contains("NotLoginUser_")){//未登录用户

        }
        //登录用户的权限校验
        return true;
    }
}
