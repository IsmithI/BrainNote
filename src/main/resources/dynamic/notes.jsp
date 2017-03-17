<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--Google fonts--%>
<link href='//fonts.googleapis.com/css?family=Amarante' rel='stylesheet'>

<html>
<head>
    <title>Your notes</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!--    <script src="http://cloud.tinymce.com/stable/tinymce.min.js?apiKey=lxalycrthi7ctx37q8glnbs4x7t2kin0fqtasllnodvj6z1k"></script>-->
    <script src="/static/js/tinymce/tinymce.min.js"></script>
    <link href="<c:url value="/static/style.css" />" rel="stylesheet">

</head>
<body>


<div id="titleLabel">
    <p>Hello, ${login}!</p>
</div>


<div id="notebook_create_input">
    <input type="text" id="notebook_name" value="" name="name">
    <div id="submit_create_notebook"></div>
</div>
<div id="create_notebook"></div>
<div id="save_changes"></div>
<div id="logout"></div>


<script>
    $("#create_notebook").click(function () {
        $("#notebook_create_input").animate({
            right: 90
        }, 500, function () {

        });
    });

    $('#save_changes').click(function () {
        saveText();
    });

    $('#logout').click(function () {
        window.location = "/logout";
    });

    $('#submit_create_notebook').on('click', function () {
        $(this).attr("disabled", "disabled");
        var notebookName = $('#notebook_name').val();
        $.post("/notes/create", {name: notebookName}, function () {
            window.location.reload(true);
        });
    });
</script>

<div id="notebooks">

    <c:if test="${not empty notebook_list}">
        <c:forEach var="notebook" items="${notebook_list}">

            <div class="notebook_wrapper">
                <div class="notebook_container">
                    <h2>${notebook.name}</h2><br>
                    <p>Pages: ${notebook.pageNum}</p><br>
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
                                            menubar: false,
                                            height: 300,
                                            theme: 'modern',
                                            plugins: [
                                                'advlist autolink lists link image charmap print preview hr anchor pagebreak',
                                                'searchreplace wordcount visualblocks visualchars code fullscreen',
                                                'insertdatetime media nonbreaking save table contextmenu directionality',
                                                'emoticons template paste textcolor colorpicker textpattern imagetools codesample toc'
                                            ],
                                            toolbar1: 'undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
                                            toolbar2: 'imageupload | forecolor backcolor emoticons | codesample',
                                            image_advtab: true,
                                            setup: function (editor) {
                                                editor.addButton('imageupload', {
                                                    text: "Upload image",
                                                    icon: false,
                                                    onclick: function () {
                                                        $('#imageChooser_${page.id}').click();
                                                    }
                                                });
                                            },
                                            content_css: [
                                                '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
                                                '//www.tinymce.com/css/codepen.min.css'
                                            ]
                                        });
                                    </script>

                                    <form class="page_text_form" id="#page_text_form_${page.id}"
                                          action="/notes/save_pages" method="post">
                                            <textarea name="text" id="page_text_${page.id}"
                                                      title="Page text">${page.text}
                                            </textarea>
                                        <input type="hidden" value="${page.id}" name="id">
                                    </form>
                                    <form id="uploadImage_${page.id}" method="POST" enctype="multipart/form-data"
                                          action="/notes/upload" style="visibility: hidden;">
                                        <input type="file" id="imageChooser_${page.id}" style="visibility: hidden;"
                                               name="uploadImage"/>
                                        <input type="hidden" value="${page.id}" name="pageId">
                                    </form>
                                    <script>
                                        $(function () {
                                            $("#imageChooser_${page.id}").change(function () {
//                                                $("#uploadImage").submit();
                                                var form = document.getElementById("uploadImage_${page.id}");
                                                var fd = new FormData(form);

                                                var xhr = new XMLHttpRequest();
                                                xhr.open("POST", "/notes/upload", true);
                                                xhr.onreadystatechange = function () {
                                                    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                                                        var xhr_image = new XMLHttpRequest();

                                                        xhr_image.open("POST", "/notes/image/last?pageId=${page.id}", true);
                                                        xhr_image.onreadystatechange = function () {
                                                            if (xhr_image.readyState === XMLHttpRequest.DONE && xhr_image.status === 200) {
                                                                var command = "<img src='/notes/image?imageId=" + xhr_image.responseText + "' />";
                                                                tinymce.activeEditor.execCommand('mceInsertContent', false,
                                                                    command);
                                                            }
                                                        };

                                                        xhr_image.send();
                                                    }
                                                };
                                                xhr.send(fd);
                                            });
                                        });
                                    </script>

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
                    <div class="options_button" onclick="savePagesAndText(${notebook.id})"
                         style="background: url('<c:url value="/static/images/buttons/addPageButton64.png"/>');
                                 width: 64px; height: 64px;">
                    </div>
                    <div class="options_button"
                         style="background: url('<c:url value="/static/images/buttons/notebookPrefs64.png"/>');
                                 width: 64px; height: 64px;">
                    </div>
                    <div class="options_button"
                         style="background: url('<c:url value="/static/images/buttons/deleteNotebook64.png"/>');
                                 width: 64px; height: 64px;" onclick="deleteNotebook(${notebook.id})">
                    </div>
                </div>

                <script> //TODO replace this with xmlhttprequest
                savePagesAndText = function (id) {
                    var xhr = new XMLHttpRequest();
                    xhr.open("post", "/notes/add_page_" + id, true);
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                            saveText();

                            var xhr_page_num = new XMLHttpRequest();
                            var url = "/notes/pageNum_" + id;
                            xhr_page_num.open("post", url, true);
                            xhr_page_num.onreadystatechange = function () {
                                if (xhr_page_num.readyState === XMLHttpRequest.DONE && xhr_page_num.status === 200) {
                                    $("#pageNum_" + id).text("Pages: " + xhr_page_num.responseText);
                                }
                            };
                            xhr_page_num.send();

                        }
                    };

                    xhr.send();
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
        document.getElementById("notebook_name").value = "Name: " + date.getHours() + ":"
            + date.getMinutes() + " " + date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();


    });

    function showPage(pageId, notebookId) {
        $(".page_wrapper_" + notebookId).hide();
        $("#page_wrapper_" + pageId).show();
    }

    function deleteNotebook(id) {
        $.post("/notes/delete_" + id, function () {
            savePagesAndText();
            window.location.reload(true);
        });
    }

    function saveText() {
        var forms = $(".page_text_form");
        $(forms).each(function (i, e) {
            var fd = new FormData(e);
            var xhr = new XMLHttpRequest();
            xhr.open("post", "/notes/save_pages", true);

            xhr.send(fd);
        });
    }
</script>
</body>
</html>



