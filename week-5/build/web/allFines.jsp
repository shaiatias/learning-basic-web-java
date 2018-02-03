<%-- 
    Document   : allFines
    Created on : Feb 3, 2018, 6:57:17 PM
    Author     : shai
--%>

<%@page import="models.BookReservation"%>
<%@page import="models.Fine"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="results" class="helpers.BeanClass" scope="request" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>My Reservations</h1>
        <table>
            <thead>
            <td>student id</td>
            <td>date</td>
            <td>late in days</td>
            <td>paid</td>
            <td></td>
        </thead>
        <tbody>

            <% for (Fine res : results.finesResults) {%>

            <tr>
                <td><%= res.studentId %></td>
                <td><%= BookReservation.format(res.createdDate) %></td>
                <td><%= res.lateInDays %></td>
                <td><%= res.paid %></td>
                <td>
                    <form action="PayFine" method="POST">
                        <input type="hidden" name="fineId" value="<%= res.id %>" />
                        <input type="submit" value="pay" />
                    </form>
                </td>
            </tr>

            <% }%>

        </tbody>
    </table>
</body>
</html>
