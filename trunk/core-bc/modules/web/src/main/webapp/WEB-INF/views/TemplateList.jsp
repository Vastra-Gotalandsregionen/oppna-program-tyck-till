<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TyckTill - Administration</title>

    <script type="text/javascript" src="${deployPath}/tycktill/resources/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${deployPath}/tycktill/resources/js/jquery-ui-1.8.6.custom.min.js"></script>

    <script type="text/javascript">
        var isShowInDialog = true;

        jQuery(document).ready(function() {
            $("#modalDiv").dialog({
                modal: true,
                autoOpen: false,
                height: 600,
                width: 610,
                position: 'center',
                draggable: true,
                resizeable: true,
                show: 'slide',
                hide: {effect: 'fade', duration: 1500},
                title: ''
            });

        });

        function openDialog(url, args, title) {
            $("#modalDiv").dialog({title: title});
            $("#modalDiv").dialog("open");
            $("#modalDialog").load(url, args, function() {
                initForm();
            });
            return false;
        }
    </script>

    <style type="text/css">
        @import "${deployPath}/tycktill/resources/style/modalStyle.css";
        @import "${deployPath}/tycktill/resources/style/style.css";
    </style>

</head>
<body>

<div id="modalDiv">
    <div id="modalDialog">
    </div>
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
                       onclick="openDialog('KontaktaOss', 'formName=${formTemplate.name}', '${formTemplate.title}');">View</a>
                </th>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <th align="right" colspan="10">
                <a href="TemplateAdd">Add</a>
            </th>
        </tr>
        </tfoot>
    </table>

</div>

</body>
</html>