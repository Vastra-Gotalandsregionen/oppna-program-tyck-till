<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: david
  Date: Nov 24, 2010
  Time: 11:11:10 AM
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>TyckTill - Administration</title>

    <script type="text/javascript" src="resources/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui-1.8.6.custom.min.js"></script>
    <script type="text/javascript" src="resources/js/layout-effects.js"></script>

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
            $("#modalDialog").load(url, function() {
                showhideBackendInit();
            });
//            $("#modalDialog").load(url, form.serialize(), function() {
//                showhideBackendInit();
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
<!--spring:url value="resources/js/jquery-1.4.2.min.js" /-->

<h1>${formTemplate.id == null ? 'Skapa nytt kontaktformulär' : 'Ändra kontaktformulär'}</h1>

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
                    <form:checkbox path="showCustom" label="Visa en egen kategori"/>
                    <input type="button" value="Ändra" onclick="openDialog('CustomCategoryEdit')"/>
                </span>
                <br/><br/>

                <span class="name" style="text-align:right;">${formTemplate.customCategory.name}</span>
                <span class="value">
                    <table>
                        <tr>
                            <td></td>
                            <td>
                                <div class="backendUsd">
                                    <span class="backend">
                                    ${(formTemplate.customCategory.backend.activeBackend && formTemplate.customCategory.backend.activeUsd) ? formTemplate.customCategory.backend.usd : ''}
                                    </span>
                                </div>
                            </td>
                            <td>
                                <div class="backendPivotal">
                                    <span class="backend">
                                    ${(formTemplate.customCategory.backend.activeBackend && formTemplate.customCategory.backend.activePivotal) ? formTemplate.customCategory.backend.pivotal : ''}
                                    </span>
                                </div>
                            </td>
                            <td>
                                <div class="backendMbox">
                                    <span class="backend">
                                    ${(formTemplate.customCategory.backend.activeBackend && formTemplate.customCategory.backend.activeMbox) ? formTemplate.customCategory.backend.mbox : ''}
                                    </span>
                                </div>
                            </td>
                        </tr>
                        <c:forEach items="${formTemplate.customCategory.customSubCategories}" var="subCategory"
                                   varStatus="loop">
                            <tr>
                                <td>
                                    <div class="subCategory">-- ${subCategory.name}</span>
                                </td>
                                <td>
                                    <div class="subCategory backendUsd">
                                        <div class="backend">
                                            ${(subCategory.backend.activeBackend && subCategory.backend.activeUsd) ? subCategory.backend.usd : ''}
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="subCategory backendPivotal">
                                        <div class="backend">
                                            ${(subCategory.backend.activeBackend && subCategory.backend.activePivotal) ? subCategory.backend.pivotal : ''}
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="subCategory backendMbox">
                                        <div class="backend">
                                            ${(subCategory.backend.activeBackend && subCategory.backend.activeMbox) ? subCategory.backend.mbox : ''}
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </span>
            </div>
            <div class="prop">
                <span class="value">
                    <form:checkbox path="showContent" label="Visa kategorin innehåll"/>
                    &nbsp;
                    <b>${contentCategory.name}</b>
                </span>

                <%--<span class="name" style="text-align:right;">${contentCategory.name}</span>--%>
                <%--<span class="value">--%>
                <%--<c:forEach items="${contentCategory.subCategories}" var="subCategory" varStatus="loop">--%>
                    <%--<span class="subCategory">-- ${subCategory.value}</span>--%>
                    <%--<br/>--%>
                <%--</c:forEach>--%>
                <%--</span>--%>
            </div>
            <div class="prop">
                <span class="value">
                    <form:checkbox path="showFunction" label="Visa kategorin funktioner"/>
                    &nbsp;
                    <b>${functionCategory.name}</b>
                </span>

                <%--<span class="name" style="text-align:right;">${functionCategory.name}</span>--%>
                <%--<span class="value">--%>
                <%--<c:forEach items="${functionCategory.subCategories}" var="subCategory" varStatus="loop">--%>
                    <%--<span class="subCategory">-- ${subCategory.value}</span>--%>
                    <%--<br/>--%>
                <%--</c:forEach>--%>
                <%--</span>--%>
            </div>
            <div class="prop">
                <span class="value">
                    <form:checkbox path="showOther" label="Visa kategorin övrigt"/>
                    &nbsp;
                    <b>${otherCategory.name}</b>
                </span>

                <%--<span class="name" style="text-align:right;">${otherCategory.name}</span><br/>--%>
                <%--<span class="value">--%>
                    <%--<c:forEach items="${otherCategory.subCategories}" var="subCategory" varStatus="loop">--%>
                        <%--<span class="subCategory">-- ${subCategory.value}</span>--%>
                        <%--<br/>--%>
                    <%--</c:forEach>--%>
                <%--</span>--%>
            </div>
            <div class="prop">
                <span class="value">
                    <form:checkbox path="showContact" label="Visa användarkontakt"/><br/>

                    <div class="contactMethods">Vilka alternativ skall användaren ges</div>
                    <div class="contactMethod"><form:checkbox path="showContactByEmail"
                                                              label="Kontakt via e-post"/></div>
                    <div class="contactMethod"><form:checkbox path="showContactByPhone"
                                                              label="Kontakt via telefon"/></div>
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
                        <div class="backendUsd" title="USD">
                            <form:checkbox path="defaultBackend.activeUsd" cssClass="backendInput"/>
                            <form:input size="6" path="defaultBackend.usd"/>
                        </div>
                    </td>
                    <td>
                        <div class="backendPivotal" title="Pivotal">
                            <form:checkbox path="defaultBackend.activePivotal" cssClass="backendInput"/>
                            <form:input size="6" path="defaultBackend.pivotal"/>
                        </div>
                    </td>
                    <td>
                        <div class="backendMbox" title="E-post">
                            <form:checkbox path="defaultBackend.activeMbox" cssClass="backendInput"/>
                            <form:input size="25" path="defaultBackend.mbox"/>
                        </div>
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