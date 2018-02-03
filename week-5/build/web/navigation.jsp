
<%@page import="java.util.Optional"%>
<%
    String role = (String) Optional.ofNullable(session.getAttribute("role")).orElse("guest");
    boolean isGuest = role.equals("guest");
    boolean isStudent = role.equals("student");
    boolean isAdmin = role.equals("admin");
    boolean isLibrarian = role.equals("librarian");    
%>

<link rel="stylesheet" href="iframe-styles.css"/>
<nav>
    <ul>
        <li><a href="home.jsp" target="content">home</a></li>
        <li><a href="Search" target="content">search</a></li>
        <li><a href="AllBooks" target="content">books</a></li>
        <li><a href="AddReservation" target="content">reserve book</a></li>
        <li><a href="returnBook.html" target="content">return book</a></li>
        <li><a href="AllReservations" target="content">my reservations</a></li>
        <li><a href="AllMyFines" target="content">my fines</a></li>
        <%= isAdmin || isLibrarian ? "<li><a target=\"content\" href=\"addStudent.html\">student book</a></li>" : "" %>
        <%= isAdmin || isLibrarian ? "<li><a target=\"content\" href=\"AddBook\">add book</a></li>" : "" %>
        <%= isAdmin || isLibrarian ? "<li><a target=\"content\" href=\"DeleteBook\">delete book</a></li>" : "" %>
    </ul>
</nav>
