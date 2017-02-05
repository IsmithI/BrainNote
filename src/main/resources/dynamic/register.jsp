<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<html>
<head>
    <title>Registration</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body>
<div align="center">
    <form action="/register_submit" method="post">
        Login: <input type="text" name="login"><br>
        Password: <input type="password" name="password" id="p1"><br>
        Repeat password: <input type="password" name="password_repeat" id="p2"><br>
        Email: <input type="email" name="email"><br>
        <input type="submit" id="submit_reg">
    </form>

    <c:if test="${result != ''}">
        <p style="color: red;">${result}</p>
    </c:if>
</div>

<script>
    $('#submit_reg').click(function () {
       var p1 = $('#p1').val();
       var p2 = $('#p2').val();

       if (p1 != p2) {
           alert("Passwords do not match!");
           window.location.reload();
       }
    });
</script>

</body>
</html>

