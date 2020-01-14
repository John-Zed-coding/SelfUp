<%--
  Created by IntelliJ IDEA.
  User: 庄志达
  Date: 2020/1/7
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>登录</title>
  <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
  <link rel="stylesheet" href="../web/css/login.css">
  <script type="text/javascript" src="../web/js/login.js"></script>
</head>
<body>
<div class="container">
  <div class="login-box">
    <div class="login-title">
      <a>login</a>
    </div>
    <div class="login-input">
      <p>
        <a >username:</a>
        <input type="text" name="username"  id="username" value="">
      </p>
      <p>
        <a >password:</a>
        <input type="password" name="password" id="password" value="">
      </p>

    </div>
    <div class="login-btn">
      <input type="button" name="login" value="login" id="login">
    </div>
  </div>
</div>
</body>
</html>

