<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <style type="text/css">
        <%@ include file="/style/style.css"%>
    </style>

</head>
<body>
    <h1>Edit</h1>

    <form:form commandName="formTemplate">
        <form:hidden path="id" />
        <div>
            Name: <form:input path="name" />
            <form:errors path="name" />
        </div>

        <div>
            Title:
            <form:input path="title" />
            <form:errors path="title" cssClass="errorBox" />
        </div>

        <div>
            Description: <form:textarea htmlEscape="false" path="description" />
            <form:errors path="description" />
        </div>

        <div>
            Show healthcare: <form:checkbox path="showHeathcareSubject" />
        </div>

        <div>
            Backend: 
            <form:select path="backend" >
                <form:option value="" />
                <form:option value="USD" />
                <form:option value="Pivotaltracker" />
            </form:select>
            <form:errors path="backend" />
        </div>

        <div>
            <input type="submit" />
        </div>
    </form:form>
</body>
</html>