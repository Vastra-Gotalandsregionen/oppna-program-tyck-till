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
<head><title>Simple jsp page</title></head>
<body>

<h3>Konfigurera en egen kategori</h3>

<form:form commandName="formTemplate" action="CustomCategoryUpdate">
    <form:hidden path="id"/>
    <table cellpadding="6" rules="groups" frame="box">
        <thead>
        <tr>
            <th></th>
            <th>Kategori</th>
            <th>&nbsp;</th>
            <th>Bas USD</th>
            <th>Bas Pivotal</th>
            <th>Bas Mailbox</th>
        </tr>
        <tr>
            <td></td>
            <td><form:input path="customCategory.name"/></td>
            <td></td>
            <td><form:input path="customCategory.backend.usd"/></td>
            <td><form:input path="customCategory.backend.pivotal"/></td>
            <td><form:input path="customCategory.backend.mbox"/></td>
        </tr>
        </thead>

        <tbody>
        <tr>
            <th>Id</th>
            <th>Under kategori</th>
            <th>&nbsp;</th>
            <th>Usd</th>
            <th>Pivotal</th>
            <th>Mailbox</th>
        </tr>
        <c:forEach items="${formTemplate.customCategory.customSubCategories}" var="subCategory" varStatus="loop">
            <tr>
                <td>${subCategory.id}</td>
                <td>
                    <input id="customCategory.customSubCategories[${loop.index}].name"
                           name="customCategory.customSubCategories[${loop.index}].name"
                           type="text" value="${subCategory.name}"/>
                </td>
                <td></td>
                <td>
                    <input id="customCategory.customSubCategories[${loop.index}].backend.usd"
                           name="customCategory.customSubCategories[${loop.index}].backend.usd"
                           type="text" value="${subCategory.backend.usd}"/>
                </td>
                <td>
                    <input id="customCategory.customSubCategories[${loop.index}].backend.pivotal"
                           name="customCategory.customSubCategories[${loop.index}].backend.pivotal"
                           type="text" value="${subCategory.backend.pivotal}"/>
                </td>
                <td>
                    <input id="customCategory.customSubCategories[${loop.index}].backend.mbox"
                           name="customCategory.customSubCategories[${loop.index}].backend.mbox"
                           type="text" value="${subCategory.backend.mbox}"/>
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