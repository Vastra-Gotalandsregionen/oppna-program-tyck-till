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



<div>
    <h1>${mainHeading}</h1>

    <div class="leadtext">${leadText}</div>

    <form:form commandName="userFeedback" enctype="multipart/form-data">

        <div class="subject">
            <span>Vad handlar ditt ärende om?</span><br/>

            <div id="webpageContentCategory">

                <form:radiobutton path="caseSubject" label="${subject_content.label}"/><br/>

                <div class="subselect">
                    <form:checkbox cssClass="checkbox" path="missingContent" label="Saknar innehåll"/><br/>
                    <form:checkbox cssClass="checkbox" path="wrongContent" label="Fel innehåll"/><br/>
                    <form:checkbox cssClass="checkbox" path="cannotFindInformation"
                                   label="Hittar inte information"/><br/>
                </div>
            </div>

            <div id="webpageFunctionCategory">
                <form:radiobutton path="caseSubject" label="${subject_function.label}"/><br/>

                <div class="subselect">
                    <form:checkbox path="pageDoesNotExist" label="Sidan finns inte"/><br/>
                    <form:checkbox path="gotErrorMessage" label="Felmeddelande"/><br/>
                    <form:checkbox path="pageDoesNotLoad" label="Sidan laddas inte"/><br/>
                    <form:checkbox path="doesNotUnderstandFunction" label="Förstår inte funktionen"/><br/>
                </div>
            </div>

            <div id="healthcareCategory">
                <form:radiobutton path="caseSubject" label="${subject_healthcare.label}"/><br/>

                <div class="subselect">
                    <form:radiobuttons path="healthcareCategory" items="${healthcareCategories}" delimiter="<br/>"/>
                </div>
            </div>

            <div id="otherCategory">
                <form:radiobutton path="caseSubject" label="${subject_other.label}"/>
            </div>
        </div>

        <div class="message">
            <div>
                <span class="msglabel">Förklara ditt ärende med egna ord:</span><br/>
                <form:textarea path="message"/>
            </div>
        </div>

        <div class="contact">
            <div>
                <span>Vill du bli kontaktad?</span>
                <form:checkbox path="shouldContactUser" label="Ja, tack"/><br/>

                <div>
                    <form:radiobuttons path="contactOption" items="${contactOptions}" delimiter="<br/>"/>
                </div>

                <span>Ditt namn</span> <form:input path="userName"/>

                <span class="contact-mail"><span>Din e-postadress</span> <form:input path="userEmail"/></span>

                <span class="conact-phone"><span>Ditt telefonnummer</span><form:input path="userPhonenumber"/></span>
            </div>
        </div>

        <div class="attachments">
            <div>
                <span>Bifoga en skärmdump, så vi kan se det du ser</span><br/>

                <form:checkbox path="attachScreenDump" label="Jag vill bifoga en skärmdump"/>
                <ol>
                    <li>Aktivera sidan du vill bifoga bild på.</li>
                    <li>Tryck på PrtSc i övre högra hörnet på tangentborder.</li>
                    <li>Starta Microsoft Word och öppna Nytt dokument.</li>
                    <li>Klistra int skärmdumpen i worddokumentet (högerklicka och välj "Klistra in")</li>
                    <li>Spara dokumentet på datorn.</li>
                    <li>Bifoga dokumentet.</li>
                </ol>
                <input type="file" name="file" value="Ladda upp fil"/>
            </div>
        </div>

        <hr/>

        <input value="Skicka" type="submit">
    </form:form>

</div>


</body>
</html>