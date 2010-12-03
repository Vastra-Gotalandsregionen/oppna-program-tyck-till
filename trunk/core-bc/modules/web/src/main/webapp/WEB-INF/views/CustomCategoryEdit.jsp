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

Place your content here

<form:form commandName="formTemplate" action="/tycktill/KontaktaOss/CustomCategoryUpdate">
    <form:hidden path="id"/>
    <table>
        <thead>
        <tr>
            <th></th>
            <th>Category name</th>
            <th>Default contact</th>
        </tr>
        <tr>
            <td></td>
            <td><form:input path="customCategory.name"/></td>
            <td><form:input path="customCategory.backend.usd"/></td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>Id</th>
            <th>SubCategory</th>
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
                <td colspan="5"><input type="submit"/></td>
            </tr>
        </tfoot>
    </table>

    
</form:form>


</body>
</html>