
<%@page import="models.BookReservation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="results" class="helpers.BeanClass" scope="session" />

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
                <td>book name</td>
                <td>book isbn</td>
                <td>email</td>
                <td>reservation started</td>
                <td>reservation finished</td>
                <td>remaining days</td>
            </thead>
            <tbody>
            
                <% for (BookReservation res : results.reservationsResults) { %>

                <tr>
                    <td><%= res.bookIsbn %></td>
                    <td><%= res.bookIsbn %></td>
                    <td><%= res.bookIsbn %></td>
                    <td><%= BookReservation.format(res.fromDate) %></td>
                    <td><%= BookReservation.format(res.toDate) %></td>
                    <td><%= res.getRemainingDays() %></td>
                </tr>

                <% } %>
                
            </tbody>
        </table>        
    </body>
</html>
