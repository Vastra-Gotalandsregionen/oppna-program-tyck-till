<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: david
  Date: Dec 3, 2010
  Time: 10:11:28 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Custom category</title>


</head>
<body>

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
                <span class="defaultBackend backendUsd" title="USD">
                    <form:checkbox path="customCategory.backend.activeUsd" cssClass="backendInput"/>
                    <form:input path="customCategory.backend.usd"/>
                </span>
            </td>
            <td>
                <span class="defaultBackend backendPivotal" title="Pivotal">
                    <form:checkbox path="customCategory.backend.activePivotal" cssClass="backendInput"/>
                    <form:input path="customCategory.backend.pivotal"/>
                </span>
            </td>
            <td>
                <span class="defaultBackend backendMbox" title="E-post">
                    <form:checkbox path="customCategory.backend.activeMbox" cssClass="backendInput"/>
                    <form:input path="customCategory.backend.mbox"/>
                </span>
            </td>
        </tr>
        </thead>

        <tbody>
        <tr>
            <th>Under kategori</th>
            <th>&nbsp;</th>
            <th colspan="4" align="left">Ändra mottagare</th>
        </tr>
        <sp/>
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
                    <span class="backend${loop.index} backendUsd" title="USD">
                        <form:checkbox path="customCategory.customSubCategories[${loop.index}].backend.activeUsd"
                                       cssClass="backendInput"/>
                        <form:input path="customCategory.customSubCategories[${loop.index}].backend.usd"/>
                    </span>
                </td>
                <td>
                    <span class="backend${loop.index} backendPivotal" title="PivotalTracker">
                        <form:checkbox path="customCategory.customSubCategories[${loop.index}].backend.activePivotal"
                                       cssClass="backendInput"/>
                        <form:input path="customCategory.customSubCategories[${loop.index}].backend.pivotal"/>
                    </span>
                </td>
                <td>
                    <span class="backend${loop.index} backendMbox" title="E-post">
                        <form:checkbox path="customCategory.customSubCategories[${loop.index}].backend.activeMbox"
                                       cssClass="backendInput"/>
                        <form:input path="customCategory.customSubCategories[${loop.index}].backend.mbox"/>
                    </span>
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
<script type="text/javascript">
    jQuery(document).ready(function() {
        try {
            var cb = $('.defaultBackendActive');
            showhide(cb[0], 'defaultBackend');

            var cbSub = $('.backendActive');
            for (var i = 0; i < cbSub.length; i++) {
                showhide(cbSub[i], 'backend' + i);
            }
        } catch (e) {
            alert(e.message);
        }
    });

    function showhide(cb, class) {
        alert(cb);
        if (cb.checked) {
            jQuery('.' + class).show('fast');
        } else {
            jQuery('.' + class).hide('fast');
        }
    }
</script>

</body>

</html>