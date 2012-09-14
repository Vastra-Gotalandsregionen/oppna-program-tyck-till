<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>TyckTill - Administration</title>

    <script type="text/javascript" src="resources/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui-1.8.6.custom.min.js"></script>
    <script type="text/javascript" src="resources/js/tycktill-dialog.js"></script>

    <script type="text/javascript">
        jQuery(document).ready(function() {

            jQuery("#modalDiv").dialog({
                                           modal: true,
                                           autoOpen: false,
                                           height: 630,
                                           width: 610,
                                           draggable: true,
                                           resizeable: true,
                                           show: 'slide',
                                           hide: {effect: 'fade', duration: 1500},
                                           title: ''
                                       });
        });

        function openDialog(url, args, title) {
            jQuery("#modalDiv").dialog({
                                           title: title,
                                           height: 650,
                                           width: 610});
            jQuery("#modalDiv").dialog("open");
            jQuery("#modalDialog").load(url, args, function() {
                //showInDialogInit();
                initForm();
            });
            return false;
        }
        function openIFrameDialog(url, args, title) {
            jQuery("#modalDiv").dialog({
                                           modal: true,
                                           autoOpen: false,
                                           height: 600,
                                           width: 600,
                                           draggable: true,
                                           resizeable: true,
                                           show: 'slide',
                                           hide: {effect: 'fade', duration: 1500},
                                           title: ''
                                       });
            jQuery("#modalDiv").dialog("open");
            jQuery("#iFrameDialog").attr('src', url + '?' + args);
//            jQuery("#iFrameDialog").load(url, args, function() {
//                //showInDialogInit();
//                initForm();
//            });
            return false;
        }
    </script>

    <style type="text/css">
        @import "resources/style/modalStyle.css";
        @import "resources/style/style.css";
    </style>

</head>
<body>

<div id="modalDiv">
    <iframe id="iFrameDialog" height="100%" width="100%" marginheight="500px" marginwidth="500px" frameborder="0"
            scrolling="auto" src="">
    </iframe>
    <div id="modalDialog">
    </div>
</div>

<div>
    <h3>Kontakta Oss-formulär</h3>

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
                <td align="center" class="${formTemplate.showContent ? 'checked' : 'unchecked'}">&nbsp;</td>
                <td align="center" class="${formTemplate.showFunction ? 'checked' : 'unchecked'}">&nbsp;</td>
                <td align="center">${formTemplate.showCustom ? formTemplate.customCategory.name : ''}</td>
                <td align="center" class="${formTemplate.showOther ? 'checked': 'unchecked'}">&nbsp;</td>
                <td align="center" class="${formTemplate.showContact ? 'checked' : 'unchecked'}">&nbsp;</td>
                <td align="center" class="${formTemplate.showAttachment ? 'checked' : 'unchecked'}">&nbsp;</td>
                <th>
                    <a href="TemplateEdit?templateId=${formTemplate.id}">Ändra</a>
                </th>
                <th>
                    <a class="iframe-dialog-link" href="#" onclick="openIFrameDialog('KontaktaOss', 'formName=${formTemplate.name}&userId=${userId}&breadcrumb=test%20av%20${formTemplate.name}', '');">Pröva</a>
                </th>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <th align="right" colspan="10">
                <a href="TemplateAdd">Lägg till</a>
            </th>
        </tr>
        </tfoot>
    </table>

</div>

</body>
</html>