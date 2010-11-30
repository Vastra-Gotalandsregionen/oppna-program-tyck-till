<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TyckTill</title>

    <style type="text/css">
        <%@ include file="/style/modalStyle.css"%>
        <%@ include file="/style/style.css"%>
    </style>

    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.8.6.custom.min.js"></script>
    <script type="text/javascript" src="/js/layout-effects.js"></script>
</head>
<body>


<div>
    <p class="${userFeedback.breadcrumb != null ? 'show' : 'hide'}">${userFeedback.breadcrumb}</p>

    <h1>${template.title}</h1>

    <div class="leadtext">${template.description}</div>

    <form:form commandName="userFeedback" enctype="multipart/form-data">
        <input type="hidden" id="formTemplateId" name="formTemplateId" value="${template.id}" />

        <div class="breadcrumb">
            <form:hidden path="breadcrumb"/>
        </div>

        <div class="subject">
            <span>Vad handlar ditt ärende om?</span><br/>

            <div id="webpageContentCategory" class="${template.showContent ? 'show' : 'hide'}">

                <form:radiobutton id="contentCase" path="caseCategory" value="${template.contentCategory.category}"
                                  label="${template.contentCategory.category}"/><br/>

                <div id="contentSubCase" class="subselect">
                    <form:checkboxes cssClass="checkbox" path="caseSubCategory" items="${template.contentCategory.subCategories}" delimiter="<br/>"/>
                </div>
            </div>

            <div id="webpageFunctionCategory" class="${template.showFunction ? 'show' : 'hide'}">
                <form:radiobutton id="functionCase" path="caseCategory" value="${template.functionCategory.category}"
                                  label="${template.functionCategory.category}"/><br/>

                <div id="functionSubCase" class="subselect">
                    <form:checkboxes cssClass="checkbox" path="caseSubCategory" items="${template.functionCategory.subCategories}" delimiter="<br/>"/>
                </div>
            </div>

            <div id="customCategory" class="${template.showCustom ? 'show' : 'hide'}">
                <form:radiobutton id="customCase" path="caseCategory" value="${template.customCategory.name}"
                                  label="${template.customCategory.name}"/><br/>

                <div id="customSubCase" class="subselect">
                    <form:radiobuttons path="caseSubCategory"
                                       items="${template.customCategory.customSubCategories}"
                                       itemLabel="name"
                                       itemValue="name"
                                       delimiter="<br/>"/>
                </div>
            </div>

            <div id="otherCategory" class="${template.showOther ? 'show' : 'hide'}">
                <form:radiobutton id="otherCase" path="caseCategory" value="${template.otherCategory.category}"
                                  label="${template.otherCategory.category}"/>
            </div>
        </div>

        <div class="message">
            <div>
                <span class="msglabel">Förklara ditt ärende med egna ord:</span><br/>
                <form:textarea path="message"/>
            </div>
        </div>

        <div class="${template.showContact ? 'show' : 'hide'}">
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

        <div class="${template.showAttachment ? 'show' : 'hide'}">
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
                    <input type="file" name="file1" value="Ladda upp fil"/>
                    <input type="file" name="file2" value="Ladda upp fil"/>
                </div>
            </div>
        </div>

        <hr/>

        <input value="Skicka" type="submit">
    </form:form>

</div>


</body>
</html>