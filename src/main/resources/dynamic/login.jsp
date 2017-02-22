<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>

<html>
<head>
    <title>Welcome to BrainNote!</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body>

<c:if test="${not empty success}">
    <p>${success}</p>
</c:if>

<div align="center">

    <form action="<c:url value="${pageContext.request.contextPath}/login" />" method="post">
        Login:<input type="text" name="username"><br>
        Password:<input type="password" name="password"><br>
        <input type="submit"><br>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />

    </form>
    <p style="color: red;">${result}</p>

    <button type="button" id="register">Register</button>

</div>


<script>
    $('#register').click(function () {
        window.location.href = "/register";
    });
</script>
</body>
</html>
