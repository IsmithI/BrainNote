<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true"%>

<html>
<head>
    <title>Your notes</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://cloud.tinymce.com/stable/tinymce.min.js?apiKey=lxalycrthi7ctx37q8glnbs4x7t2kin0fqtasllnodvj6z1k"></script>

</head>
<body>

<h1>Hello, !</h1>

<div id="notebook_panel" class="panel">
    <p>
    <form action="/create_notebook" method="post">
        <input type="text" id="notebook_name" value="" name="name">
        <input type="submit" value="Create notebook">
    </form>
    </p>
    <button type="button" id="delete_notebook">Delete selected</button>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>

<div id="notebooks">

    <c:if test="${not empty notebook_list}">
        <c:forEach var="notebook" items="${notebook_list}">
            <div class="notebook_container" style="background-color: yellow; width: 300px; height: 500px;">
                    ${notebook.role}<br>
                Pages: ${notebook.pageNum}<br>
                <hr>
                <ul id="pages_navigation_${notebook.id}">
                    <c:if test="${not empty notebook.pages}">
                        <c:forEach items="${notebook.pages}" var="page">
                            <li id="page_${page.page_n}"><a href="">${page.page_n}</a></li>

                            <script>
                                tinymce.init({
                                    selector: '#page_text_${page.id}'
                                });
                            </script>

                            <form id="#page_text_form_${page.id}" action="/notes/save_page" method="post">
                            <textarea rows="5" cols="10" role="text"
                                      id="page_text_${page.id}">${page.text}</textarea>
                                <input type="hidden" value="${page.id}" role="id">
                            </form>

                            <script>
                                $(document).ready(function () {
                                    submitPageSave = function () {
                                        document.getElementById("#page_text_form_${page.id}").submit();
                                    }
                                });
                            </script>

                        </c:forEach>
                        <input type="button" onclick="submitPageSave()" value="Save changes"/>


                    </c:if>
                </ul>


                <form action="/notes/add_page_${notebook.id}" method="post">
                    <input type="submit" value="Add new page">
                </form>
                <input type="button" value="Edit" id="edit_notebook_${notebook.id}">

            </div>
            <br>

            <script>
                $('#pages_navigation_${notebook.id}').hide();

                $('#edit_notebook_${notebook.id}').click(function () {
                    $('#pages_navigation_${notebook.id}').show();
                });


            </script>
        </c:forEach>

    </c:if>
</div>


<script>
    $(document).ready(function () {

        var date = new Date();
        document.getElementById("notebook_name").value = date.getHours() + ":"
            + date.getMinutes() + " " + date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();

    });
</script>
</body>
</html>



