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

<script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.6.custom.min.js"></script>
<script type="text/javascript" src="/js/layout-effects.js"></script>

<body>


<div>
    <h1>${mainHeading}</h1>

    <div class="leadtext">${leadText}</div>

    <form:form commandName="userFeedback" enctype="multipart/form-data">

        <div class="breadcrumb">
            <form:input path="breadcrumb" />
        </div>

        <div class="subject">
            <span>Vad handlar ditt ärende om?</span><br/>

            <div id="webpageContentCategory">

                <form:radiobutton id="contentCase" path="caseSubject" value="${subject_content.name}"
                                  label="${subject_content.label}"/><br/>

                <div id="contentSubCase" class="subselect">
                    <form:checkbox cssClass="checkbox" path="missingContent" label="Saknar innehåll"/><br/>
                    <form:checkbox cssClass="checkbox" path="wrongContent" label="Fel innehåll"/><br/>
                    <form:checkbox cssClass="checkbox" path="cannotFindInformation"
                                   label="Hittar inte information"/><br/>
                </div>
            </div>

            <div id="webpageFunctionCategory">
                <form:radiobutton id="functionCase" path="caseSubject" value="${subject_function.name}"
                                  label="${subject_function.label}"/><br/>

                <div id="functionSubCase" class="subselect">
                    <form:checkbox path="pageDoesNotExist" label="Sidan finns inte"/><br/>
                    <form:checkbox path="gotErrorMessage" label="Felmeddelande"/><br/>
                    <form:checkbox path="pageDoesNotLoad" label="Sidan laddas inte"/><br/>
                    <form:checkbox path="doesNotUnderstandFunction" label="Förstår inte funktionen"/><br/>
                </div>
            </div>

            <div id="healthcareCategory">
                <form:radiobutton id="healthCase" path="caseSubject" value="${subject_healthcare.name}"
                                  label="${subject_healthcare.label}"/><br/>

                <div id="healthSubCase" class="subselect">
                    <form:radiobuttons path="healthcareCategory" items="${healthcareCategories}" delimiter="<br/>"/>
                </div>
            </div>

            <div id="otherCategory">
                <form:radiobutton id="otherCase" path="caseSubject" value="${subject_other.name}"
                                  label="${subject_other.label}"/>
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
                <form:checkbox id="shouldContactUser" path="shouldContactUser" label="Ja, tack"/><br/>

                <div id="contactInfo" class="subselect">
                    <div>
                        <form:radiobuttons id="contactOption" path="contactOption" items="${contactOptions}"
                                           cssClass="contactOptionClass" delimiter="<br/>"/>
                    </div>

                    <div>
                        <span style="float:left"><span>Ditt namn</span> <form:input path="userName"/></span>

                        <span style="float: left;" class="contact-mail"><span>Din e-postadress</span> <form:input
                                path="userEmail"/></span>

                        <span style="float: left;" class="contact-phone"><span>Ditt telefonnummer</span><form:input
                                path="userPhonenumber"/>
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <div class="attachments">
            <div>
                <span>Bifoga en skärmdump, så vi kan se det du ser</span><br/>

                <form:checkbox id="attachScreenDump" path="attachScreenDump" label="Jag vill bifoga en skärmdump"/>
                <div id="attachmentDetail">
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
        </div>

        <hr/>

        <input value="Skicka" type="submit">
    </form:form>

</div>


</body>
</html>