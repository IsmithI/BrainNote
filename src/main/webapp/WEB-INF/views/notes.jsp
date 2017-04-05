<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--Google fonts--%>
<%--<link href='//fonts.googleapis.com/css?family=Amarante' rel='stylesheet'>--%>

<html>
<head>
    <title>Your notes</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!--    <script src="http://cloud.tinymce.com/stable/tinymce.min.js?apiKey=lxalycrthi7ctx37q8glnbs4x7t2kin0fqtasllnodvj6z1k"></script>-->
    <script src="/static/tinymce/tinymce.min.js"></script>
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
<div class="circleButton" id="create_notebook"></div>
<div class="circleButton" id="save_changes"></div>
<div class="circleButton" id="user_options"></div>

<div id="user_sidebar">
    <h3>
        <p>
            Username: ${login}
        </p>
        <p>
            Email: ${email}
        </p>

        <div class="regularButton" id="edit_password" style="display: inline-block">Edit password</div>

        <div class="regularButton" id="logout" style="width: 128px; display: inline-block">Log out</div>

        <form id="change_password" action="/notes/changePassword" method="post" style="margin-top: 10px">
            Enter your current password: <input type="password" name="old_password"> <br>
            Enter new password: <input type="password" name="new_password"> <br>
            Repeat new password: <input type="password" name="repeat_password"> <br>
            <input class="regularButton" type="submit" value="Change password">
        </form>
    </h3>
</div>

<script>
    var hidden = true;
    $("#create_notebook").click(function () {
        if (hidden) {
            $("#notebook_create_input").animate({
                right: 90
            }, 500, function () {
            });

            hidden = false;
            $("#create_notebook").css({
                "-webkit-transform": "rotate(45deg)",
                "-moz-transform": "rotate(45deg)",
                "transform": "rotate(45deg)"
            });
        }
        else {
            $("#notebook_create_input").animate({
                right: -500
            }, 500, function () {
            });

            hidden = true;
            $("#create_notebook").css({
                "-webkit-transform": "rotate(0deg)",
                "-moz-transform": "rotate(0deg)",
                "transform": "rotate(0deg)"
            });
        }
    });

    $('#save_changes').click(function () {
        saveText();

        var item = $("#save_indicator");

        item.animate({bottom: 48}, {duration: 500}).delay(750).animate({bottom: -48}, {duration: 500});
    });

    $('#logout').click(function () {
        window.location = "/logout";
    });

    $('#submit_create_notebook').on('click', function () {
        saveText();
        $(this).attr("disabled", "disabled");
        var notebookName = $('#notebook_name').val();
        $.post("/notes/create", {name: notebookName}, function () {
            window.location.reload(true);
        });
    });

    $('#change_password').hide();
    $('#edit_password').click(function () {
        $('#change_password').show();
    });

    var hidden = true;
    var sidebar = $('#user_sidebar');

    $('#user_options').click(function () {
        if (hidden) {
            sidebar.animate({top: 0}, {right: 0}, {duration: 500}); //show
            hidden = false;
        }
        else {
            sidebar.animate({top: -440}, {right: 0}, {duration: 500}); //hide
            hidden = true;
        }
    });

    $("#logout").click(function () {
        $.post("POST", "/logout");
    });
</script>

<div id="notebooks">

    <c:if test="${not empty notebook_list}">
        <c:forEach var="notebook" items="${notebook_list}">

            <div class="notebook_wrapper" id="notebook_wrapper_${notebook.id}"
                 style="background-color: ${notebook.color}">

                <div class="notebook_container">
                    <h2 style="width: 400px; height: 46px;">
                        <div style="width: auto; height: 46px; line-height: 46px; vertical-align: middle; display: inline-block;">
                                ${notebook.name}
                        </div>
                        <div id="notebook_prefs_${notebook.id}"
                             style="width: 46px; height: 46px; vertical-align: middle; display: inline-block;
                                     background: url(<c:url
                                     value="/static/images/buttons/notebookPrefs64.png"/>) no-repeat; float: right;">
                        </div>
                    </h2>
                    <br>

                    <div class="options_panel" id="panel_${notebook.id}"
                         style="
                         padding-top: 36px;
                         padding-left: 12px;
                         padding-right: 12px;
                        font-size: 22px;">

                        Notebook name: <br>
                        <form method="post" action="/notes/changeNotebookName">
                            <input type="text" value="${notebook.name}" name="name">
                            <input type="submit" value="Change">

                            <input type="hidden" value="${notebook.id}" name="id">
                        </form>
                        <div style="height: 48px; line-height: 48px; vertical-align: middle;">
                            <div style="display: inline-block;">Color:</div>
                            <span style="line-height: inherit; vertical-align: middle">
                                <div class="color_picker color_picker_${notebook.id}" style="background-color: #9E9E9E;"></div>
                                <div class="color_picker color_picker_${notebook.id}" style="background-color: #f08080"></div>
                                <div class="color_picker color_picker_${notebook.id}" style="background-color: #2e8b57"></div>
                                <div class="color_picker color_picker_${notebook.id}" style="background-color: #f5deb3"></div>
                            </span>
                        </div>


                        <input class="regularButton" type="button" id="delete_all_pages_${notebook.id}" value="Delete all pages"
                               style="color: white"/>
                        <input class="regularButton" type="button" id="delete_notebook_${notebook.id}" value="Delete this notebook"
                               style="color: white"/>
                    </div>
                    <hr>

                    <script>
                        var hidden_${notebook.id} = true;
                        var notebook_prefs = $('#notebook_prefs_${notebook.id}');

                        $(notebook_prefs).click(function () {
                            if (hidden_${notebook.id}) {
                                $('#panel_${notebook.id}').css({visibility: "visible"});
                                hidden_${notebook.id} = false;
                            }
                            else {
                                $('#panel_${notebook.id}').css({visibility: "hidden"});
                                hidden_${notebook.id} = true;
                            }
                        });

                        $('.color_picker_${notebook.id}').click(function () {
                            var color = $(this).css("background-color");
                            var id = ${notebook.id};
                            var notebook = $('#notebook_wrapper_${notebook.id}');

                            changeNotebookColor(id, color, notebook);
                        });

                        $('#delete_all_pages_${notebook.id}').click(function () {
                            var ids = [];
                            <c:forEach items="${notebook.pages}" var="page">
                            ids.push(${page.id});
                            </c:forEach>

                            $.post("/notes/pages/delete", {notebookId: ${notebook.id}, ids: ids}, function () {
                                window.location.reload();
                                saveText();
                            });
                        });

                        $('#delete_notebook_${notebook.id}').click(function () {
                            var id = ${notebook.id};
                            $.post("/notes/delete_" + id, function () {
                                window.location.reload();
                                saveText();
                            });
                        });
                    </script>


                    <div id="pages_navigation_${notebook.id}">
                        <c:if test="${not empty notebook.pages}">

                            <c:forEach items="${notebook.pages}" var="page" varStatus="status">
                                <div class="page_wrapper_${notebook.id}" id="page_wrapper_${page.id}"
                                ${status.first ? "" : "style = 'display: none';"}>

                                    <script>
                                        tinymce.init({
                                            selector: '#page_text_${page.id}',
                                            elementpath: false,
                                            statusbar: false,
                                            skin: "lightgray",
                                            themes: "modern",
                                            menubar: false,
                                            height: 364,
                                            theme: 'modern',
                                            plugins: [
                                                'advlist autolink lists link image charmap print preview hr anchor pagebreak',
                                                'searchreplace wordcount visualblocks visualchars code',
                                                'insertdatetime media nonbreaking save table contextmenu directionality',
                                                'emoticons template paste textcolor colorpicker textpattern imagetools codesample toc'
                                            ],
                                            toolbar1: 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
                                            toolbar2: 'imageupload | forecolor backcolor emoticons',
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
                                            <textarea class="editor" name="text" id="page_text_${page.id}">
                                                    ${page.text}
                                            </textarea>

                                        <input class="textarea_ids" id="pageId_${page.id}"
                                               type="hidden" value="${page.id}" name="id">
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
                                                                $("#upload_indicator").animate({bottom: -48}, {duration: 500});
                                                            }
                                                        };

                                                        xhr_image.send();
                                                    }
                                                };
                                                xhr.send(fd);
                                                $("#upload_indicator").animate({bottom: 48}, {duration: 500});
                                            });
                                        });
                                    </script>

                                </div>
                            </c:forEach>

                            <div class="navigation">
                                <c:forEach items="${notebook.pages}" var="page">
                                    <p class="page_num page_num_${notebook.id}" id="page_${page.page_n}_${notebook.id}"
                                       onclick='showPage(${page.id}, ${notebook.id}, ${page.page_n})'>
                                        <input type="checkbox" class="page_select" id="${page.id}"/>
                                            ${page.page_n}
                                    </p>
                                    <%--<script>--%>
                                        <%--console.log(${page.page_n});--%>
                                    <%--</script>--%>
                                </c:forEach>
                            </div>
                            <script>
                                $(document.getElementById("page_1_"+${notebook.id})).css({background : '#37E7DC'});
                            </script>

                        </c:if>
                    </div>
                </div>


                <div class="options">
                    <div class="options_button" onclick="savePagesAndText(${notebook.id})"
                         style="background: url('<c:url value="/static/images/buttons/addPageButton64.png"/>');
                                 width: 64px; height: 64px;">
                    </div>
                    <div class="options_button"
                         style="background: url('<c:url value="/static/images/buttons/deleteNotebook64.png"/>');
                                 width: 64px; height: 64px;" onclick="deletePages(${notebook.id})">
                    </div>

                </div>

                <script>
                    savePagesAndText = function (id) {
                        saveText();
                        $.post("/notes/add_page", {notebookId: id}, function () {
                            window.location.reload();
                        });
                    }
                </script>

            </div>
        </c:forEach>
    </c:if>
</div>

<div class="indicator" id="upload_indicator">
    Uploading image...
</div>

<div class="indicator" id="save_indicator">
    Saving changes...
</div>

<c:if test="${result ne null}">
    <div id="result">
            ${result}
    </div>
</c:if>


<script>

    $(document).ready(function () {
        var date = new Date();
        document.getElementById("notebook_name").value = "Name: " + date.getHours() + ":"
            + date.getMinutes() + " " + date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();

    });

    function showPage(pageId, notebookId, pageNumId) {
        console.log("Page shown")
        $(".page_wrapper_" + notebookId).hide();
        $("#page_wrapper_" + pageId).show();
        $(".page_num_" + notebookId).css({
            background: "#2d8ac7"
        });
        $("#page_" + pageNumId + "_" + notebookId).css({
            background: "#37E7DC"
        });
    }

    function deleteNotebook(id) {
        saveText();
        $.post("/notes/delete_" + id, function () {
            window.location = "/notes";
        });
    }

    function saveText() {
        var pages = $(".editor");
        var content = [];

        $(pages).each(function (i, e) {
            var id = $(e).attr('id');
            content[i] = tinyMCE.get(id).getContent();
        });

        var ids_input = $(".textarea_ids");
        var ids = [];

        $(ids_input).each(function (i, e) {
            var id = $(e).attr('id');
            ids[i] = $('#' + id).val();
        });


        for (var i = 0; i < content.length; i++) {
            var xhr = new XMLHttpRequest();

            var params = "text=" + content[i] + "&id=" + ids[i];

            xhr.open("POST", "/notes/save_pages?" + params, true);
            xhr.send();
        }

    }

    function deletePages(notebookId) {
        var ids = [];
        $('input:checked').each(function () {
            ids.push($(this).attr('id'));
        });

        var message = "Pages successfully deleted!";

        if (ids.length != 0) {
            $.ajax({
                type: "POST",
                url: "/notes/pages/delete?notebookId=" + notebookId,
                data: {
                    ids: ids
                },
                success: function () {
                    window.location.reload();
                    saveText();
                }
            });
        }
    }

    function changeNotebookColor(id, color, notebook) {
        $.ajax({
            type: "POST",
            url: "/notes/changeNotebookColor",
            data: {
                color: color,
                id: id
            },
            success: function () {
                $(notebook).css({
                    background: color
                });
            }
        });
    }

</script>
</body>
</html>

