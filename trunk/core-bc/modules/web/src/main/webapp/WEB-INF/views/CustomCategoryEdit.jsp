<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: david
  Date: Dec 3, 2010
  Time: 10:11:28 AM
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title>Custom category</title>

    <script type="text/javascript" src="resources/js/layout-effects.js"></script>

    <script type="text/javascript">
        jQuery(document).ready(function() {
//            showhideInit();
        });

    </script>

</head>
<body>

<script type="text/javascript">
<c:import url="resources/js/layout-effects.js" />
</script>

<h3>Konfigurera en egen kategori</h3>

<form:form commandName="formTemplate" action="CustomCategoryUpdate">
    <form:hidden path="id"/>
    <table cellpadding="6" rules="groups" frame="box">
        <thead>
        <tr>
            <th>Kategori</th>
            <th>&nbsp;</th>
            <th colspan="4" align="left">Ändra mottagare</th>
        </tr>
        <tr>
            <td><form:input path="customCategory.name"/></td>
            <td></td>
            <td>
                <form:checkbox path="customCategory.backend.activeBackend"
                               cssClass="defaultBackendActive" onclick="showhide(this, 'defaultBackend');"/>
            </td>
            <td>
                <div class="defaultBackend backendUsd" title="USD">
                    <form:checkbox path="customCategory.backend.activeUsd" cssClass="backendInput"/>
                    <form:input size="6" path="customCategory.backend.usd"/>
                </div>
            </td>
            <td>
                <div class="defaultBackend backendPivotal" title="Pivotal">
                    <form:checkbox path="customCategory.backend.activePivotal" cssClass="backendInput"/>
                    <form:input size="6" path="customCategory.backend.pivotal"/>
                </div>
            </td>
            <td>
                <div class="defaultBackend backendMbox" title="E-post">
                    <form:checkbox path="customCategory.backend.activeMbox" cssClass="backendInput"/>
                    <form:input size="25" path="customCategory.backend.mbox"/>
                </div>
            </td>
        </tr>
        </thead>

        <tbody>
        <tr>
            <th>Under kategori</th>
            <th>&nbsp;</th>
            <th colspan="4" align="left">Ändra mottagare</th>
        </tr>
        <c:forEach items="${formTemplate.customCategory.customSubCategories}" var="subCategory" varStatus="loop">
            <tr>
                <td>
                    <form:input path="customCategory.customSubCategories[${loop.index}].name"/>
                </td>
                <td></td>
                <td>
                    <form:checkbox path="customCategory.customSubCategories[${loop.index}].backend.activeBackend"
                                   cssClass="backendActive" onclick="showhide(this, 'backend${loop.index}');"/>
                </td>
                <td>
                    <div class="backend${loop.index} backendUsd" title="USD">
                        <form:checkbox path="customCategory.customSubCategories[${loop.index}].backend.activeUsd"
                                       cssClass="backendInput"/>
                        <form:input size="6" path="customCategory.customSubCategories[${loop.index}].backend.usd"/>
                    </div>
                </td>
                <td>
                    <div class="backend${loop.index} backendPivotal" title="PivotalTracker">
                        <form:checkbox path="customCategory.customSubCategories[${loop.index}].backend.activePivotal"
                                       cssClass="backendInput"/>
                        <form:input size="6" path="customCategory.customSubCategories[${loop.index}].backend.pivotal"/>
                    </div>
                </td>
                <td>
                    <div class="backend${loop.index} backendMbox" title="E-post">
                        <form:checkbox path="customCategory.customSubCategories[${loop.index}].backend.activeMbox"
                                       cssClass="backendInput"/>
                        <form:input size="25" path="customCategory.customSubCategories[${loop.index}].backend.mbox"/>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>

        <tfoot>
        <tr>
            <td colspan="6"><input type="submit" value="Fortsätt"/></td>
        </tr>
        </tfoot>
    </table>


</form:form>

</body>

</html>