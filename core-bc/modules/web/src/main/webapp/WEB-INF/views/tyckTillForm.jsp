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
<style type="text/css">
    <%@ include file="/style/style.css"%>
</style>

<body>

<h5>${mainHeading}</h5>

<p>${leadText}</p>

<div>
    <form:form commandName="userFeedback" enctype="multipart/form-data">

        <div id="webpageContentCategory">
            <form:radiobutton path="caseSubject" label="${subject_content.label}"/>
        </div>
        <div>
            <form:checkbox path="missingContent" label="Saknar innehåll"/>
            <form:checkbox path="wrongContent" label="Fel innehåll"/>
            <form:checkbox path="cannotFindInformation" label="Hittar inte information"/>
        </div>

        <div id="webpageFunctionCategory">
            <form:radiobutton path="caseSubject" label="${subject_function.label}"/>
        </div>
        <div>
            <form:checkbox path="pageDoesNotExist" label="Sidan finns inte"/>
            <form:checkbox path="gotErrorMessage" label="Felmeddelande"/>
            <form:checkbox path="pageDoesNotLoad" label="Sidan laddas inte"/>
            <form:checkbox path="doesNotUnderstandFunction" label="Förstår inte funktionen"/>
        </div>

        <div id="healthcareCategory">
            <form:radiobutton path="caseSubject" label="${subject_healthcare.label}"/>
        </div>
        <div>
            <form:radiobuttons path="healthcareCategory" items="${healthcareCategories}"/>
        </div>

        <div id="otherCategory">
            <form:radiobutton path="caseSubject" label="${subject_other.label}"/>
        </div>

        <div>
            <span>Förklara ditt ärende med egna ord:</span>
            <form:textarea path="message"/>
        </div>

        <div>
            Vill du bli kontaktad?
            <form:checkbox path="shouldContactUser" label="Ja, tack"/>

            <form:radiobuttons path="contactOption" items="${contactOptions}"/>

            <span>Ditt namn</span> <form:input path="userName"/>

            <span>Din e-postadress</span> <form:input path="userEmail"/>

            <span>Ditt telefonnummer</span><form:input path="userPhonenumber"/>
        </div>

        <div>
            Bifoga en skärmdump, så vi kan se det du ser
            <form:checkbox path="attachScreenDump" label="Jag vill bifoga en skärmdump"/>

            <input type="file" name="file" value="Ladda upp fil"/>
        </div>


        <input value="Save" type="submit">
    </form:form>
</div>

</body>
</html>