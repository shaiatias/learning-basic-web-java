<%@ page import="services.UsersService" %>

<%--
  Created by IntelliJ IDEA.
  User: Benjamin
  Date: 1/7/2018
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>app title</title>
  </head>
  <body>
  <p>dqddwq</p>

  <%
    String a = UsersService.getName();
  %>

  <h1><%= a %></h1>

  </body>
</html>
