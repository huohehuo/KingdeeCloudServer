<%--
  Created by IntelliJ IDEA.
  User: NB
  Date: 2017/8/7
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" import="Bean.RegisterBean" import="Webdao.WebDao"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>注册用户管理</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>

    <!-- popper.min.js 用于弹窗、提示、下拉菜单 -->
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>

    <!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <a class="navbar-brand col-xl-7" href="#" style="margin-left: 6.25rem;">方左科技</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse col-xl-3" id="collapsibleNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="index.html">首页</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="case.html">查询</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="feedback.html">个人信息</a>
                </li>
            </ul>
        </div>
    </nav>

    <div  style="width: auto;height: auto;"class="col-lg-6">
        <div class="input-group">
            <input type="text" class="form-control">
            <span class="input-group-btn">
						<button class="btn btn-default" type="button">
							Go!
						</button>
					</span>
        </div><!-- /input-group -->
    </div><!-- /.col-lg-6 -->
</div>
</body>


</html>
