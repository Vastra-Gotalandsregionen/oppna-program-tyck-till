<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    <script type="text/javascript" src="resources/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui-1.8.6.custom.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $("#modalDiv").dialog({
                modal: true,
                autoOpen: false,
                height: 500,
                width: 810,
                draggable: true,
                resizeable: true,
                title: 'Tyck till'
            });
        });

        function openDialog(url) {
            var form = $("#formTemplate");
            $("#modalDiv").dialog("open");
            $("#modalDialog").load(url, form.serialize());
            return false;
        }
    </script>

    <style type="text/css">
        @import "resources/style/modalStyle.css";
        @import "resources/style/style.css";
    </style>

</head>
<body>
<!--spring:url value="resources/js/jquery-1.4.2.min.js" /-->

<h1>${formTemplate.id == null ? 'Skapa nytt kontakt formulär' : 'Ändra kontakt formulär'}</h1>

<div id="modalDiv">
    <div id="modalDialog">
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
            <span class="value">
                <form:checkbox path="showContent" label="Visa innehålls kategorin"/>
                <input type="button" value="Backend" onclick="javascript: alert('Inte implementerad ännu')"/>
            </span>
            <br/><br/>

            <span class="name" style="text-align:right;">${contentCategory.name}</span>
            <span class="value">
            <c:forEach items="${contentCategory.subCategories}" var="subCategory" varStatus="loop">
                <span class="subCategory">-- ${subCategory.value}</span>
                <br/>
            </c:forEach>
            </span>
        </div>
        <div class="prop">
            <span class="value">
                <form:checkbox path="showFunction" label="Visa funktions kategorin"/>
                <input type="button" value="Backend" onclick="javascript: alert('Inte implementerad ännu')"/>
            </span>
            <br/><br/>

            <span class="name" style="text-align:right;">${functionCategory.name}</span>
            <span class="value">
            <c:forEach items="${functionCategory.subCategories}" var="subCategory" varStatus="loop">
                <span class="subCategory">-- ${subCategory.value}</span>
                <br/>
            </c:forEach>
            </span>
        </div>
        <div class="prop">
            <span class="value">
                <form:checkbox path="showCustom" label="Visa en egen kategori"/>
                <input type="button" value="Ändra" onclick="openDialog('CustomCategoryEdit')"/>
            </span>
            <br/><br/>

            <span class="name" style="text-align:right;">${formTemplate.customCategory.name}</span>
            <span class="value">
            <c:forEach items="${formTemplate.customCategory.customSubCategories}" var="subCategory" varStatus="loop">
                <span class="subCategory">-- ${subCategory.name}</span>
                <span class="subCategory">${subCategory.backend.usd}</span>
                <span class="subCategory">${subCategory.backend.pivotal}</span>
                <span class="subCategory">${subCategory.backend.mbox}</span>
                <br/>
            </c:forEach>
            </span>
        </div>
        <div class="prop">
            <span class="value">
                <form:checkbox path="showOther" label="Visa övrigt kategorin"/>
                <input type="button" value="Backend" onclick="javascript: alert('Inte implementerad ännu')"/>
            </span>
            <br/><br/>

            <span class="name" style="text-align:right;">${otherCategory.name}</span><br/>
            <span class="value">
                <c:forEach items="${otherCategory.subCategories}" var="subCategory" varStatus="loop">
                    <span class="subCategory">-- ${subCategory.value}</span>
                    <br/>
                </c:forEach>
            </span>
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
        <span class="name">Mottagare</span>
        <span class="value">
            <table>
                <tr>
                    <td>
                        <span class="backendUsd" title="USD">
                                <form:checkbox path="defaultBackend.activeUsd" cssClass="backendInput"/>
                                <form:input path="defaultBackend.usd"/>
                        </span>
                    </td>
                    <td>
                        <span class="backendPivotal" title="Pivotal">
                                <form:checkbox path="defaultBackend.activePivotal" cssClass="backendInput"/>
                                <form:input path="defaultBackend.pivotal"/>
                        </span>
                    </td>
                    <td>
                        <span class="backendMbox" title="E-post">
                                <form:checkbox path="defaultBackend.activeMbox" cssClass="backendInput"/>
                                <form:input path="defaultBackend.mbox"/>
                        </span>
                    </td>
                </tr>
            </table>
        </span>
        <span class="error"><form:errors path="defaultBackend" htmlEscape="false"/></span>
    </div>

    <div>
        <input type="submit" value="Spara"/>
        <input type="button" onclick="javascript: window.location.href = 'TemplateList'" value="Avbryt">
    </div>

    <br/>
    <br/>
    <br/>

</form:form>
</body>
</html>