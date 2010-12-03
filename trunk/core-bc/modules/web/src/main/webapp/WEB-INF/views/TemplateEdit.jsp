<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: david
  Date: Nov 24, 2010
  Time: 11:11:10 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TyckTill - Administration</title>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.1/jquery-ui.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#modalDiv").dialog({
                modal: true,
                autoOpen: false,
                height: 600,
                width: 600,
                draggable: true,
                resizeable: true,
                title: 'Tyck till'
            });
            loadjscssfile("style/alternateStyle.css", "css");
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
            var form = $("#formTemplate");
            $("#modalDiv").dialog("open");
            $("#modalIFrame").load(url, form.serialize());
            return false;
        }


    </script>

    <style type="text/css">
        <%@ include file="/style/style.css"%>
    </style>
    <link rel="stylesheet" type="text/css" href="../../style/modalStyle.css" title="MyStyle">

</head>
<body>
<h1>${formTemplate.id == null ? 'Skapa nytt kontakt formulär' : 'Ändra kontakt formulär'}</h1>

<div id="modalDiv">
    <div id="modalIFrame">

    </div>
</div>

<form:form commandName="formTemplate">
    <form:hidden path="id"/>
    <div class="prop">
        <span class="name">Formulärets namn</span>
        <span class="value"><form:input path="name" size="50"/></span>
        <span class="error"><form:errors path="name" htmlEscape="false" cssClass="errors"/></span>
    </div>

    <div class="prop">
        <span class="name">Titel</span>
        <span class="value"><form:input path="title" size="50"/></span>
        <span class="error"><form:errors path="title" htmlEscape="false" cssClass="errorBox"/></span>
    </div>

    <div class="prop">
        <span class="name">Inledning</span>
        <span class="value"><form:textarea htmlEscape="false" path="description" cols="50" rows="10"/></span>
        <span class="error"><form:errors path="description" htmlEscape="false"/></span>
    </div>

    <div>
        <span class="name">Konfigurera vilka delar av formuläret som skall visas</span>

        <span class="value">
        <div class="prop">
            <span class="value"><form:checkbox path="showContent" label="Visa innehålls kategorin"/></span><br/>

            <ul>
                <li><span class="category">${contentCategory.name}</span><span class="action"><a
                        href="">Backend</a></span></li>
                <c:forEach items="${contentCategory.subCategories}" var="subCategory" varStatus="loop">
                    <li><span class="subCategory">-- ${subCategory.value}</span></li>
                </c:forEach>
            </ul>
        </div>
        <div class="prop">
            <span class="value"><form:checkbox path="showFunction" label="Visa funktions kategorin"/></span><br/>

            <ul>
                <li><span class="category">${functionCategory.name}</span><span class="action"><a
                        href="">Backend</a></span></li>
                <c:forEach items="${functionCategory.subCategories}" var="subCategory" varStatus="loop">
                    <li><span class="subCategory">-- ${subCategory.value}</span></li>
                </c:forEach>
            </ul>
        </div>
        <div class="prop">
            <span class="value"><form:checkbox path="showCustom" label="Visa en egen kategori"/></span>
            <span class="value"><input type="button" value="Ändra"
                                       onclick="openDialog('/tycktill/KontaktaOss/CustomCategoryEdit')"/></span>
            <br/><br/>

            <span class="name">${formTemplate.customCategory.name}</span><br/>
            <span class="value">
            <c:forEach items="${formTemplate.customCategory.customSubCategories}" var="subCategory" varStatus="loop">
                <span>-- ${subCategory.name}</span><br/>
            </c:forEach>
        </div>
        <div class="prop">
            <span class="value"><form:checkbox path="showOther" label="Visa övrigt kategorin"/></span><br/>

            <ul>
                <li><span class="category">${otherCategory.name}</span><span class="action"><a
                        href="">Backend</a></span></li>
                <c:forEach items="${otherCategory.subCategories}" var="subCategory" varStatus="loop">
                    <li><span class="subCategory">-- ${subCategory.value}</span></li>
                </c:forEach>
            </ul>
        </div>
        <div class="prop">
            <span class="value">
                <form:checkbox path="showContact" label="Visa användar kontakt"/><br/>

                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>Vilka alternativ skall användaren ges</span><br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox path="showContactByEmail"
                                                                                     label="Kontakt via e-post"/><br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox path="showContactByPhone"
                                                                                     label="Kontakt via telefon"/>
            </span>
        </div>
        <div class="prop">
            <span class="value"><form:checkbox path="showAttachment"
                                               label="Skall användaren ges möjligheten att ladda upp filer (tex. en skärmdump)"/></span>
        </div>
        </span>
    </div>

    <div class="prop">
        <span class="name">Backend</span>
        <span class="value">
            <span class="name">USD</span>
            <span class="value"><form:input path="defaultBackend.usd"/> (Ange projekt id)</span><br/>

            <span class="name">PivotalTracker</span>
            <span class="value"><form:input path="defaultBackend.pivotal"/> (Ange projekt id)</span><br/>

            <span class="name">Grupp brevlåda</span>
            <span class="value"><form:input path="defaultBackend.mbox"/> (Ange mail adress)</span>
        </span>
        <span class="error"><form:errors path="defaultBackend" htmlEscape="false"/></span>
    </div>

    <div>
        <input type="submit"/>
    </div>

    <hr/>

</form:form>
</body>
</html>