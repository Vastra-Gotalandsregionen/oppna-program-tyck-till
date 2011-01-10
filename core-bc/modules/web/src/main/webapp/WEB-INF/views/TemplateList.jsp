<%--

    Copyright 2010 Västra Götalandsregionen

      This library is free software; you can redistribute it and/or modify
      it under the terms of version 2.1 of the GNU Lesser General Public
      License as published by the Free Software Foundation.

      This library is distributed in the hope that it will be useful,
      but WITHOUT ANY WARRANTY; without even the implied warranty of
      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
      GNU Lesser General Public License for more details.

      You should have received a copy of the GNU Lesser General Public
      License along with this library; if not, write to the
      Free Software Foundation, Inc., 59 Temple Place, Suite 330,
      Boston, MA 02111-1307  USA

--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>TyckTill - Administration</title>

    <script type="text/javascript" src="resources/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui-1.8.6.custom.min.js"></script>
    <script type="text/javascript" src="resources/js/tycktill-dialog.js"></script>

    <script type="text/javascript">
        jQuery(document).ready(function() {
            jQuery("#modalDiv").dialog({
                modal: true,
                autoOpen: false,
                height: 600,
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
                height: 600,
                width: 610});
            jQuery("#modalDiv").dialog("open");
            jQuery("#modalDialog").load(url, args, function() {
                showInDialogInit();
                initForm();
            });
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
                <th><a href="TemplateEdit?templateId=${formTemplate.id}">Ändra</a></th>
                <th><a href="#"
                       onclick="openDialog('KontaktaOss', 'formName=${formTemplate.name}', '${formTemplate.title}');">Pröva</a>
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