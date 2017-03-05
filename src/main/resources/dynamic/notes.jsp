<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Your notes</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!--    <script src="http://cloud.tinymce.com/stable/tinymce.min.js?apiKey=lxalycrthi7ctx37q8glnbs4x7t2kin0fqtasllnodvj6z1k"></script>-->
    <script src="/static/js/tinymce/tinymce.min.js"></script>
    <link href="<c:url value="/static/style.css" />" rel="stylesheet">

</head>
<body>

<h1>Hello, ${login}!</h1>

<div id="notebook_panel" class="panel">
    <form action="/notes/create" method="post">
        <input type="text" id="notebook_name" value="" name="name">
        <input type="submit" value="Create notebook">
    </form>
    <input type="button" id="save_changes" value="Save changes"/>
    <a href="/logout">Logout</a>
</div>

<div id="sidebar">

</div>
<div id="sidebar_bookmark">

</div>

<script>
    $("#sidebar_bookmark").click(function () {
        $("#sidebar_bookmark").animate({
           top: 300
        }, 500, function () {

        });
        $("#sidebar").animate({
            top: 0
        }, 500, function () {

        });
    });
</script>

<div id="notebooks">

    <c:if test="${not empty notebook_list}">
        <c:forEach var="notebook" items="${notebook_list}">

            <div class="notebook_wrapper">
                <div class="notebook_container">
                    <h2>${notebook.name}</h2><br>
                    Pages: ${notebook.pageNum}<br>
                    <hr>
                    <div id="pages_navigation_${notebook.id}">
                        <c:if test="${not empty notebook.pages}">

                            <c:forEach items="${notebook.pages}" var="page">
                                <div class="page_wrapper_${notebook.id}" id="page_wrapper_${page.id}">

                                    <script>
                                        $("#page_wrapper_${page.id}").hide();

                                        tinymce.init({
                                            selector: '#page_text_${page.id}',
                                            elementpath: false,
                                            statusbar: false,
                                            skin: "lightgray",
                                            themes: "modern",
                                            menubar: false
                                        });
                                    </script>

                                    <form class="page_text_form" id="#page_text_form_${page.id}"
                                          action="/notes/save_pages"
                                          method="post">
                                <textarea name="text"
                                          id="page_text_${page.id}" title="Page text">${page.text}
                                </textarea>
                                        <input type="hidden" value="${page.id}" name="id">
                                    </form>

                                </div>
                            </c:forEach>

                            <div class="navigation">
                                <c:forEach items="${notebook.pages}" var="page">
                                    <p class="page_num" id="page_${page.page_n}"
                                       onclick="showPage(${page.id}, ${notebook.id})">
                                            ${page.page_n}
                                    </p>
                                </c:forEach>
                            </div>

                        </c:if>
                    </div>
                </div>


                <div class="options">
                    <div class="options_button" id="edit_notebook_${notebook.id}"
                         style="background: url('<c:url value="/static/images/buttons/editNotebookButton64.png"/>');
                                 width: 64px; height: 64px;">
                    </div>
                    <div class="options_button" onclick="savePagesAndText()"
                         style="background: url('<c:url value="/static/images/buttons/addPageButton64.png"/>');
                                 width: 64px; height: 64px;">
                    </div>
                    <div class="options_button"
                         style="background: url('<c:url value="/static/images/buttons/notebookPrefs64.png"/>');
                                 width: 64px; height: 64px;">
                    </div>
                    <div class="options_button"
                         style="background: url('<c:url value="/static/images/buttons/deleteNotebook64.png"/>');
                                 width: 64px; height: 64px;">
                    </div>
                </div>

                <script>
                    savePagesAndText = function () {
                        $.post("/notes/add_page_${notebook.id}");
                        saveText()
                    }
                </script>

            </div>

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

        $('#save_changes').click(function () {
            saveText()
        });

        saveText = function () {
            var forms = $(".page_text_form");
            $(forms).each(function () {
                $(this).submit();
            });
        }
    });

    function showPage(pageId, notebookId) {
        $(".page_wrapper_" + notebookId).hide();
        $("#page_wrapper_" + pageId).show();
    }
</script>
</body>
</html>



