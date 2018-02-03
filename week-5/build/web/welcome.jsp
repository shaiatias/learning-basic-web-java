
<%@page import="java.util.Optional"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="styles.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        

        <div  class="navigation-frame">
            <iframe src="navigation.jsp" frameborder="0"></iframe>
            <div>
                <form action="Logout" method="POST">
                    <input type="submit" value="Logout">
                </form>
            </div>
        </div>
        
        <iframe src="home.jsp" frameborder="0" name="content" class="content-frame"></iframe>
        

    </body>
</html>
