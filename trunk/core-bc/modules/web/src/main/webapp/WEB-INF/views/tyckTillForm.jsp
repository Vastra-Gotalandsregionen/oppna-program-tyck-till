<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: david
  Date: Nov 18, 2010
  Time: 11:10:26 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title></head>
<body>

<h5>${mainHeading}</h5>

<p>${leadText}</p>

<form:form commandName="userFeedback">

    <div id="webpageContentSubcategories">
        <form:radiobutton path="caseSubject" label="${caseSubjects_contentRelated.label}" />

        <form:checkbox path="missingContent" label="Missing Content"/>
        <form:checkbox path="wrongContent" label="Wrong content"/>
        <form:checkbox path="cannotFindInformation" label="Can not find info"/>
    </div>

    <div id="webpageFunctionSubcategories">
        <form:radiobutton path="caseSubject" label="${caseSubjects_functionRelated.label}" />

        <form:checkbox path="pageDoesNotExist"/>
        <form:checkbox path="gotErrorMessage"/>
        <form:checkbox path="pageDoesNotLoad"/>
        <form:checkbox path="doesNotUnderstandFunction"/>
    </div>

    <div id="otherSubcategories"/>

    <form:input path="message" />

    <input value="Save" type="submit">
</form:form>

</body>
</html>