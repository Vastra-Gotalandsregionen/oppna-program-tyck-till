<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TyckTill - Administration</title>

    <style type="text/css">
        <%@ include file="/style/style.css"%>
    </style>

</head>
<body>

<div>
    <h1>Form template list</h1>

    <table>
        <tr>
            <th>Name</th>
            <th>Title</th>
            <th>Show Healthcare</th>
            <th>Backend</th>
            <th></th>
        </tr>

        <c:forEach items="${templateList}" var="formTemplate">
            <tr>
                <td>${formTemplate.name}</td>
                <td>${formTemplate.title}</td>
                <td>${formTemplate.showHeathcareSubject}</td>
                <td>${formTemplate.backend}</td>
                <td><a href="TemplateEditor?templateId=${formTemplate.id}">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="TemplateEditor">Add</a>
</div>

</body>
</html>