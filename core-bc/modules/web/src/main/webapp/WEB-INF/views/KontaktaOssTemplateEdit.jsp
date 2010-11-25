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
<h1>${formTemplate.id == null ? 'Add new template' : 'Edit template'}</h1>

<form:form commandName="formTemplate">
    <form:hidden path="id"/>
    <div class="prop">
        <span class="name">Name:</span>
        <span class="value"><form:input path="name" size="50"/></span>
        <span class="error"><form:errors path="name" htmlEscape="false" cssClass="errors"/></span>
    </div>

    <div class="prop">
        <span class="name">Title:</span>
        <span class="value"><form:input path="title" size="50"/></span>
        <span class="error"><form:errors path="title" htmlEscape="false" cssClass="errorBox"/></span>
    </div>

    <div class="prop">
        <span class="name">Description:</span>
        <span class="value"><form:textarea htmlEscape="false" path="description" cols="50" rows="10"/></span>
        <span class="error"><form:errors path="description" htmlEscape="false"/></span>
    </div>

    <div>
        <div>Optional parts of the form</div>

        <div class="prop">
            <span class="name">Show content:</span>
            <span class="value"><form:checkbox path="showContent"/></span>
        </div>
        <div class="prop">
            <span class="name">Show function:</span>
            <span class="value"><form:checkbox path="showFunction"/></span>
        </div>
        <div class="prop">
            <span class="name">Show healthcare:</span>
            <span class="value"><form:checkbox path="showHeathcareSubject"/></span>
        </div>
        <div class="prop">
            <span class="name">Show contact:</span>
            <span class="value"><form:checkbox path="showContact"/></span>
        </div>
        <div class="prop">
            <span class="name">Show attachment:</span>
            <span class="value"><form:checkbox path="showAttachment"/></span>
        </div>
    </div>

    <div class="prop">
        <span class="name">Backend:</span>
        <span class="value">
        <form:select path="backend">
            <form:option value=""/>
            <form:option value="USD"/>
            <form:option value="Pivotaltracker"/>
        </form:select>
        </span>
        <span class="error"><form:errors path="backend" htmlEscape="false"/></span>
    </div>

    <div>
        <input type="submit"/>
    </div>
</form:form>
</body>
</html>