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
    <form name = "loginForm" action="${pageContext.request.contextPath}/register" method="post">
        Login: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        Repeat password: <input type="password" name="password_repeat"><br>
        Email: <input type="email" name="email"><br>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />

        <input type="submit">
    </form>

    <c:if test="${result != ''}">
        <p style="color: red;">${result}</p>
    </c:if>
</div>


</body>
</html>

