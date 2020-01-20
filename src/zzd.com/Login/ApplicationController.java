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
                    break;
                }else{
                    token ="";
                    break;
                }
            }
        }//通过token获取UserModel
        UserModel userModel = ds.ckeckToken(token);
        //根据UserModel实时更改Token
        Cookie cookie = new Cookie("Token",userModel.getName()+"/"+userModel.getToken());
        System.out.println("Cookie has value like this:"+cookie.getValue());
        response.addCookie(cookie);
        //注册
        if(request.getRequestURI().contains("/register.jsp")){
            try {
                ds.register(request,response,userModel);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
        //登录
        if(request.getRequestURI().contains("/login.jsp")){
            ds.login(request,response,userModel);
        }
    }
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
