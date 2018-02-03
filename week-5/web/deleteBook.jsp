<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="iframe-styles.css"/>
    </head>
    <body>
        <h1>Delete book</h1>
        
        <form action="DeleteBook" method="POST">
            
            <label>ISBN:</label>
            <input type="text" name="isbn" />
            <br />
            
            <input type="submit" value="delete" />
            
        </form>
    </body>
</html>
