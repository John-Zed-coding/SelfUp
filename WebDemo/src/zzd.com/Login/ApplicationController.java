package zzd.com.Login;

import zzd.com.domain.User;
import zzd.com.utils.DashBoard;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
    /*protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
    }*/
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //乱码处理
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        //从cookie中获取请求token
        DashBoard ds = DashBoard.getInstance();
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies==null){
            token = "";
        }else{
            for(int i =0;i<cookies.length;i++){
                if(cookies[i].getName().equals("Token")){
                    token=cookies[i].getValue();
                    //System.out.println(cookies[i].getValue());
                    break;
                }else{
                    token ="";
                }
                    break;
            }
        }
        //调用DashBoard里的一个赋值token方法
        UserModel userModel = ds.ckeckToken(token);//通过token获取UserModel
        //根据UserModel实时更改Token
        Cookie cookie = new Cookie("Token",userModel.getName()+"/"+userModel.getToken());
        System.out.println("Cookie has value like this:"+cookie.getValue());
        response.addCookie(cookie);
        //注册
        if(request.getRequestURI().contains("/register.jsp")){
            try {
                Register(request,response,token);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
        //登录
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
    public void Register(HttpServletRequest request,HttpServletResponse response,String token) throws ServletException, IOException {
        //当用户请求注册时：
        if(DashBoard.getInstance().haveRight(token)){//校验是否可以注册，这里是可以注册
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
        doGet(request,response);
    }
}
