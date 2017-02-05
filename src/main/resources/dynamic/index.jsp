<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<html>
  <head>
    <title>Welcome to BrainNote!</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  </head>
  <body>

  <div align="center">

    <form action="<c:url value="/login" />" method="get">
      Login:<input type="text" name="login"><br>
      Password:<input type="password" name="password"><br>
      <input type="submit"><br>
    </form>
    <p style="color: red;">${result}</p>

    <button type="button" id="register">Register</button>

  </div>


  <script>
    $('#register').click(function () {
        window.location.href='/register';
    });
  </script>
  </body>
</html>
