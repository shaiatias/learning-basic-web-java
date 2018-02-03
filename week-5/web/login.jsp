
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Optional"%>
<%@page import="helpers.LoginHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="styles.css"/>
    </head>
    <body>
        <h1>Login to the system</h1>

        <form action="Login" method="POST">
            <label>username: </label>
            <input name="username" value="<%= Optional.ofNullable(request.getAttribute("username")).orElse("") %>" autofocus />
            <br />

            <label>password: </label>
            <input name="password" type="password" />
            <br />

            <input type="submit" value="Login" />
        </form>

        <form action="GuestLogin" method="POST">
            <input type="submit" value="Login as guest" />
        </form>



    </body>
</html>
