
<%@page import="models.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="results" class="helpers.BeanClass" scope="request" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>        
        <link rel="stylesheet" href="iframe-styles.css"/>
    </head>
    <body>
        <h1>Rent a book</h1>

        <form action="AddReservation" method="POST">

            <%
                if (results.searchResults == null) {
            %>
                <label>select a category</label>
                <select name="categoty">
                    <%= request.getAttribute("categoriesOptions")%>
                </select>
                <input type="submit" value="select" />
            <%
                }
            
                else {

                    out.println("<label>select a book</label>");
                    out.println("<select name=\"bookName\">");

                    for (Book book : results.searchResults) {
                        out.println("<option>" + book.name + "</option>");
                    }

                    out.println("</select>");
                    out.println("<input type=\"submit\" value=\"select\" />");
                }
            %>
            
        </form>
    </body>
</html>
