<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TyckTill - Administration</title>

    <script type="text/javascript" src="../resources/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../resources/js/jquery-ui-1.8.6.custom.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $("#modalDiv").dialog({
                modal: true,
                autoOpen: false,
                height: 670,
                width: 610,
                draggable: true,
                resizeable: true,
                title: 'Tyck till'
            });
            loadjscssfile("../resources/style/alternateStyle.css", "css");
        });

        function loadjscssfile(filename, filetype) {
            if (filetype == "js") { //if filename is a external JavaScript file
                var fileref = document.createElement('script')
                fileref.setAttribute("type", "text/javascript")
                fileref.setAttribute("src", filename)
            }
            else if (filetype == "css") { //if filename is an external CSS file
                var fileref = document.createElement("link")
                fileref.setAttribute("rel", "stylesheet")
                fileref.setAttribute("type", "text/css")
                fileref.setAttribute("href", filename)
            }
            if (typeof fileref != "undefined")
                document.getElementsByTagName("head")[0].appendChild(fileref)
        }

        function openDialog(url) {
            $("#modalDiv").dialog("open");
            $("#modalIFrame").attr('src', url);
            return false;
        }
    </script>

    <style type="text/css">
        @import "../resources/style/modalStyle.css";
        @import "../resources/style/style.css";
    </style>

</head>
<body>
<div id="modalDiv">
        <iframe id="modalIFrame"
                width="550"
                height="600"
                marginWidth="0"
                marginHeight="30"
                frameBorder="0"
                scrolling="auto">
        </iframe>
    </div>
<div>
    <h3>Tillgängliga kontakt formulär</h3>

    <table cellpadding="6" rules="groups" frame="box">
        <thead>
        <tr>
            <th>Name</th>
            <th>Title</th>
            <th>Innehåll</th>
            <th>Funktion</th>
            <th>Egen</th>
            <th>Övrigt</th>
            <th>Användar kontakt</th>
            <th>Skärmdumpar</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${templateList}" var="formTemplate">
            <tr>
                <td>${formTemplate.name}</td>
                <td>${formTemplate.title}</td>
                <td align="center">${formTemplate.showContent}</td>
                <td align="center">${formTemplate.showFunction}</td>
                <td align="center">${formTemplate.showCustom ? formTemplate.customCategory.name : formTemplate.showCustom}</td>
                <td align="center">${formTemplate.showOther}</td>
                <td align="center">${formTemplate.showContact}</td>
                <td align="center">${formTemplate.showAttachment}</td>
                <th><a href="TemplateEdit?templateId=${formTemplate.id}">Edit</a></th>
                <th><a href="#"
                       onclick="javascript: openDialog('../KontaktaOss?formName=${formTemplate.name}');">View</a>
                </th>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <th align="right" colspan="10">
                <a href="TemplateEdit">Add</a>
            </th>
        </tr>
        </tfoot>
    </table>

</div>

</body>
</html>