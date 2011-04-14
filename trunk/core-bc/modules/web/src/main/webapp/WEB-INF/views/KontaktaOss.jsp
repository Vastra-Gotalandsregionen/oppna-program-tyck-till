<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html id="all_html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%--<script type="text/javascript" src="https://getfirebug.com/firebug-lite.js"></script>--%>

    <title>TyckTill</title>

    <script type="text/javascript" src="resources/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui-1.8.6.custom.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery.form.js"></script>
    <script type="text/javascript" src="resources/js/jquery.timer.js"></script>
    <script type="text/javascript" src="resources/js/layout-effects.js"></script>

    <script type="text/javascript">
        jQuery(document).ready(function() {
            initForm();
        });

        function showInDialogInit() {
            $('.title').hide();

            var options = {
                target:         '#userfeedback_form',
                success:        showResponse
            };
            $("#userFeedback").ajaxForm(options);
        }

        function showResponse(responseText, statusText, xhr, $form) {
            var isTack = $('#userfeedback_form').find('title').text() == 'Tack';
            if (isTack) {
                $.timer(1500, function() {
                    $("#modalDiv").dialog("close");
                });
            }
        }
    </script>

    <style type="text/css">
        @import "resources/style/modalStyle.css";
        @import "resources/style/style.css";
    </style>
</head>
<body id="all_body">


<div id="userfeedback_form">
    <div class="form-head">
        <h1 class="title">${template.title}</h1>

        <div class="leadtext">${template.description}</div>
    </div>


    <form:form commandName="userFeedback" enctype="multipart/form-data">
        <input type="hidden" id="formTemplateId" name="formTemplateId" value="${template.id}"/>
        <form:hidden path="breadcrumb"/>

        <div class="subject">
            <div class="leadtext strong">Vad handlar ditt ärende om?</div>

            <div id="customCategory" class="${template.showCustom ? 'show' : 'hide'}">
                <form:radiobutton id="customCase"
                                  path="caseCategoryId"
                                  value="${template.customCategory.id}"
                                  label="${template.customCategory.name}"/>

                <div id="customSubCase" class="subselect">
                    <form:radiobuttons path="caseSubCategoryIds"
                                       items="${template.customCategory.customSubCategories}"
                                       itemValue="id"
                                       itemLabel="name"
                                       delimiter=""/>
                </div>
            </div>


            <div id="webpageContentCategory" class="${template.showContent ? 'show' : 'hide'}">

                <form:radiobutton id="contentCase"
                                  path="caseCategoryId"
                                  value="${contentCategory.id}"
                                  label="${contentCategory.name}"/>

                <div id="contentSubCase" class="subselect">
                    <form:checkboxes cssClass="checkbox"
                                     path="caseSubCategoryIds"
                                     items="${contentCategory.subCategories}"
                                     delimiter=""/>
                </div>
            </div>
            <div id="webpageFunctionCategory" class="${template.showFunction ? 'show' : 'hide'}">
                <form:radiobutton id="functionCase"
                                  path="caseCategoryId"
                                  value="${functionCategory.id}"
                                  label="${functionCategory.name}"/>

                <div id="functionSubCase" class="subselect">
                    <form:checkboxes cssClass="checkbox"
                                     path="caseSubCategoryIds"
                                     items="${functionCategory.subCategories}"
                                     delimiter=""/>
                </div>
            </div>
            <div id="otherCategory" class="${template.showOther ? 'show' : 'hide'}">
                <form:radiobutton id="otherCase"
                                  path="caseCategoryId"
                                  value="${otherCategory.id}"
                                  label="${otherCategory.name}"/>

                <div id="otherSubCase" class="subselect">
                    <form:checkboxes cssClass="checkbox"
                                     path="caseSubCategoryIds"
                                     items="${otherCategory.subCategories}"
                                     delimiter=""/>
                </div>
            </div>
        </div>

        <div class="message-wrap">
            <div class="message">
                <div>
                    <span class="msglabel">Rubrik:</span><br class="msglabel"/>
                    <form:input path="caseTitle" title="Rubrik" cssClass="defaultRubrik" size="60"/>
                    <span class="error"><form:errors path="caseTitle" htmlEscape="false" cssClass="errorBox"/></span>
                </div>
            </div>

            <div class="message">
                <div>
                    <span class="msglabel">Beskriv ditt &auml;rende och ge g&auml;rna exempel:</span><br
                        class="msglabel"/>
                    <form:textarea path="caseMessage" title="Beskriv ditt ärende och ge gärna exempel"
                                   cssClass="defaultMessage" cols="60" rows="7"/>
                    <span class="error"><form:errors path="caseMessage" htmlEscape="false" cssClass="errorBox"/></span>
                </div>
            </div>

            <div class="${template.showContact ? 'show' : 'hide'}">
                <div class="form-item-wrap">
                    <form:checkbox id="shouldContactUser" path="userContact.shouldContactUser"
                                   cssClass="strong"
                                   label="Jag vill ha svar"/>

                    <div id="contactInfo" class="subselect contact-info">
                        <div class="${(template.showContactByEmail && template.showContactByPhone) ? 'show' : 'hide'}">
                            <form:radiobuttons id="contactOption"
                                               path="userContact.contactOption"
                                               items="${contactOptions}"
                                               cssClass="contactOptionClass"/>
                        </div>

                        <div>
                            <div class="form-item-wrap clearfix">
                                <label class="inline">Ditt namn</label>
                                <form:input path="userContact.userName"/> *
                                <span class="error">
                                    <form:errors path="userContact.userName"
                                                 htmlEscape="false"
                                                 cssClass="errorBox"/>
                                </span>
                            </div>

                            <div class="form-item-wrap clearfix">
                                <label for="userContact.contactMethod"
                                       class="inline ${(template.showContactByEmail && !template.showContactByPhone) ? 'show' : 'contact-mail'}">Din
                                    e-postadress</label>
                                <label for="userContact.contactMethod"
                                       class="inline ${(!template.showContactByEmail && template.showContactByPhone) ? 'show' : 'contact-phone'}">Ditt
                                    telefonnummer</label>
                                <form:input path="userContact.contactMethod"/> *
                            <span class="error">
                                <form:errors path="userContact.contactMethod" htmlEscape="false" cssClass="errorBox"/>
                            </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="${template.showAttachment ? 'show' : 'hide'}">
                <div class="form-item-wrap">
                    <div class="instruction">Du kan välja att bifoga en skärmdump, så vi kan se det du ser</div>

                    <form:checkbox id="attachScreenDump"
                                   path="attachScreenDump"
                                   cssClass="strong"
                                   label="Jag vill bifoga en skärmdump"/>
                    <div id="attachmentDetail">
                        <ol class="instruction">
                            <li>Aktivera sidan du vill bifoga bild på.</li>
                            <li>Tryck på PrtSc i övre högra hörnet på tangentborder.</li>
                            <li>Starta Microsoft Word och öppna Nytt dokument.</li>
                            <li>Klistra int skärmdumpen i worddokumentet (högerklicka och välj "Klistra in")</li>
                            <li>Spara dokumentet på datorn.</li>
                            <li>Bifoga dokumentet.</li>
                        </ol>
                        <input type="file" name="file1" value="Ladda upp fil"/>
                        <span class="${fileUploadError != null ? 'error' : ''}">${fileUploadError != null ? fileUploadError : ''}</span>
                    </div>
                </div>
            </div>

        </div>

        <div class="btn-wrap clearfix"><input value="Skicka" type="submit"> <span class="logo"><img src="resources/img/vgr_logo_vit.gif" alt="Västra Götalands Regionen" /></span></div>
    </form:form>
</div>

</body>
</html>