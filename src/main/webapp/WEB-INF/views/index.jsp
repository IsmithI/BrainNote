<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--Google fonts--%>
<%--<link href='//fonts.googleapis.com/css?family=Amarante' rel='stylesheet'>--%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"/>


<html>
<head>

    <title>Welcome to BrainNote!</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <link href="<c:url value="/static/style.css" />" rel="stylesheet">
</head>
<body style="overflow-x: auto; overflow-y: hidden;">

<div id="wrapper">

    <div id="header">

        <div class="form" id="login-form" style="top: 100px; right: 65px;">

            <h2>Log in</h2>

            <c:url value="/login" var="loginUrl"/>

            <form action="${loginUrl}" method="post">
                <p class="input">Login:<input type="text" name="j_login"><br>
                </p>
                <p class="input">Password:<input type="password" name="j_password"><br>
                    <input type="submit" value="Take some notes!"><br>
                </p>
            </form>
        </div>

    </div>

    <div id="register">
        <h1 style="text-align: center; padding-top: 64px;">Create new account</h1>
        <div>
            <form id="register_form" action="/register" method="post">
                <p>Login: <input type="text" name="username"><br></p>
                <p>Password: <input type="password" name="password"><br></p>
                <p>Repeat password: <input type="password" name="password_repeat"><br></p>
                <p>Email: <input type="email" name="email"><br></p>

                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form>
            <button id="confirm_register" type="button" style="margin-left: 160px; margin-top: -8px;">Create an
                account
            </button>
            <p id="info" style="font-size: 18px; color: #ff6b2c;" align="center"></p>


        </div>
    </div>

    <button type="button" class="button_tag" id="button_register">
        <p>Register</p>
    </button>

    <button type="button" id="back">Back to login</button>

    <c:if test="${result ne null}">
        <div id="result">
                ${result}
        </div>
    </c:if>

</div>
<script>
    $(document).ready(function () {
        $('#register').hide();

        $("#confirm_register").click(function () {
            var password = $('input[name=password]').val();
            var password_repeat = $('input[name=password_repeat]').val();
            var username = $('input[name=username]').val();

            if (password != password_repeat)
                $("#info").text("Passwords don't match!");
            else if (username == "" || hasWhiteSpaces(username))
                $("#info").text("Login must not have spaces or be empty!");
            else $("#register_form").submit();
        });

        //animate register appear
        $('#button_register').click(function () {
            $('#register').show();

            $("#header").animate({
                right: "+=2000"
            }, 1000, function () {
            });

            $("#button_register").animate({
                right: "-=300"
            }, 500, function () {
            });

            $("#back").animate({
                right: "-60"
            }, 500, function () {
            });

            $("#register").animate({
                top: 0,
                left: $(window).width() / 2 - $("#register").width() / 2
            }, 1000, function () {
            });
        });

        //animate register disappear
        $("#back").click(function () {
            $("#header").animate({
                right: "50%"
            }, 1000, function () {
            });

            $("#button_register").animate({
                right: "-60"
            }, 500, function () {
            });

            $("#back").animate({
                right: "-300"
            }, 500, function () {
            });

            $("#register").animate({
                top: 2000,
                left: $(window).width() / 2 - $("#register").width() / 2
            }, 1000, function () {
                $('#register').hide();
            });
        });

        window.addEventListener("keydown", function (e) {
            // space, page up, page down and arrow keys:
            if ([32, 33, 34, 37, 38, 39, 40].indexOf(e.keyCode) > -1) {
                e.preventDefault();
            }
        }, false);
    });

    function hasWhiteSpaces(s) {
        return /\s/g.test(s);
    }


</script>
</body>
</html>
