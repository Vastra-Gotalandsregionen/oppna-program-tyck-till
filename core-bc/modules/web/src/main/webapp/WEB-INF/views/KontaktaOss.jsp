<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TyckTill</title>

    <script type="text/javascript" src="${deployPath}/tycktill/resources/js/layout-effects.js"></script>
    <script type="text/javascript" src="${deployPath}/tycktill/resources/js/jquery.form.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            var options = {
                target:         '#userfeedback_form',
                beforeSubmit:   showRequest,
                success:        showResponse
            };

            $("#userFeedback").ajaxForm(options);
        });

        function showRequest(formData, jqForm, options) {
            // formData is an array; here we use $.param to convert it to a string to display it
            // but the form plugin does this for you automatically when it submits the data
            var queryString = $.param(formData);

            // jqForm is a jQuery object encapsulating the form element.  To access the
            // DOM element for the form do this:
            // var formElement = jqForm[0];

//            alert('About to submit: \n\n' + queryString);

            // here we could return false to prevent the form from being submitted;
            // returning anything other than false will allow the form submit to continue
            return true;
        }
        function showResponse(responseText, statusText, xhr, $form) {
            // for normal html responses, the first argument to the success callback
            // is the XMLHttpRequest object's responseText property

            // if the ajaxForm method was passed an Options Object with the dataType
            // property set to 'xml' then the first argument to the success callback
            // is the XMLHttpRequest object's responseXML property

            // if the ajaxForm method was passed an Options Object with the dataType
            // property set to 'json' then the first argument to the success callback
            // is the json data object returned by the server

//            alert('status: ' + statusText + '\n\nresponseText: \n' + responseText +
//                    '\n\nThe output div should have already been updated with the responseText.');
        }
    </script>

    <style type="text/css">
        @import "${deployPath}/tycktill/resources/style/modalStyle.css";
        @import "${deployPath}/tycktill/resources/style/style.css";
    </style>
</head>
<body>


<div id="userfeedback_form">
    <p class="${userFeedback.breadcrumb != null ? 'show' : 'hide'}">${userFeedback.breadcrumb}</p>


    <h1>${template.title}</h1>

    <div class="leadtext">${template.description}</div>

    <form:form commandName="userFeedback" enctype="multipart/form-data">
        <%--<form:errors path="caseTitle"/>--%>


        <input type="hidden" id="formTemplateId" name="formTemplateId" value="${template.id}"/>

        <div class="breadcrumb">
            <form:hidden path="breadcrumb"/>
        </div>

        <div class="subject">
            <span>Vad handlar ditt ärende om?</span><br/>

            <div id="webpageContentCategory" class="${template.showContent ? 'show' : 'hide'}">

                <form:radiobutton id="contentCase"
                                  path="caseCategoryId"
                                  value="${contentCategory.id}"
                                  label="${contentCategory.name}"/><br/>

                <div id="contentSubCase" class="subselect">
                    <form:checkboxes cssClass="checkbox"
                                     path="caseSubCategoryIds"
                                     items="${contentCategory.subCategories}"
                                     delimiter="<br/>"/>
                </div>
            </div>
            <div id="webpageFunctionCategory" class="${template.showFunction ? 'show' : 'hide'}">
                <form:radiobutton id="functionCase"
                                  path="caseCategoryId"
                                  value="${functionCategory.id}"
                                  label="${functionCategory.name}"/><br/>

                <div id="functionSubCase" class="subselect">
                    <form:checkboxes cssClass="checkbox"
                                     path="caseSubCategoryIds"
                                     items="${functionCategory.subCategories}"
                                     delimiter="<br/>"/>
                </div>
            </div>

            <div id="customCategory" class="${template.showCustom ? 'show' : 'hide'}">
                <form:radiobutton id="customCase"
                                  path="caseCategoryId"
                                  value="${template.customCategory.id}"
                                  label="${template.customCategory.name}"/><br/>

                <div id="customSubCase" class="subselect">
                    <form:radiobuttons path="caseSubCategoryIds"
                                       items="${template.customCategory.customSubCategories}"
                                       itemValue="id"
                                       itemLabel="name"
                                       delimiter="<br/>"/>
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
                                     delimiter="<br/>"/>
                </div>
            </div>
        </div>

        <div><br/></div>

        <div class="message">
            <div>
                <span class="msglabel">Ärende titel:</span><br/>
                <form:input path="caseTitle" size="60"/>
                <span class="error">*<form:errors path="caseTitle" htmlEscape="false" cssClass="errorBox"/></span>
            </div>
        </div>

        <div class="message">
            <div>
                <span class="msglabel">Förklara ditt ärende med egna ord:</span><br/>
                <form:textarea path="caseMessage" cols="60" rows="7"/>
                <span class="error"><form:errors path="caseMessage" htmlEscape="false" cssClass="errorBox"/></span>
            </div>
        </div>

        <div><br/></div>

        <div class="${template.showContact ? 'show' : 'hide'}">
            <div>
                <span></span>
                <form:checkbox id="shouldContactUser" path="userContact.shouldContactUser"
                               label="Jag vill ha svar"/><br/>

                <div id="contactInfo" class="subselect">
                    <div class="${(template.showContactByEmail && template.showContactByPhone) ? 'show' : 'hide'}">
                        <form:radiobuttons id="contactOption"
                                           path="userContact.contactOption"
                                           items="${contactOptions}"
                                           cssClass="contactOptionClass"/>
                    </div>

                    <div>
                        <span style="float:left">
                            <span>Ditt namn</span>
                            <form:input path="userContact.userName"/> *
                            <span class="error"><form:errors path="userContact.userName" htmlEscape="false"
                                                             cssClass="errorBox"/></span>
                        </span>

                        <span style="float: left;">
                            <span style="float: left; padding-top: 3px;"
                                  class="${(template.showContactByEmail && !template.showContactByPhone) ? 'show' : 'contact-mail'}">Din e-postadress</span>
                            <span style="float: left; padding-top: 3px;"
                                  class="${(!template.showContactByEmail && template.showContactByPhone) ? 'show' : 'contact-phone'}">Ditt telefonnummer</span>
                            <span style="float: left;"
                                  class="${(!template.showContactByEmail || !template.showContactByPhone) ? 'show' : 'contact-method-input'}"><form:input
                                    path="userContact.contactMethod"/> *</span>
                            <span class="error"><form:errors path="userContact.contactMethod" htmlEscape="false"
                                                             cssClass="errorBox"/></span>
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <div><br/></div>

        <div class="${template.showAttachment ? 'show' : 'hide'}">
            <div>
                <span>Bifoga en skärmdump, så vi kan se det du ser</span><br/>

                <form:checkbox id="attachScreenDump" path="attachScreenDump" label="Jag vill bifoga en skärmdump"/>
                <div>${fileUploadError != null ? fileUploadError : ''}</div>
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

        <input value="Skicka" type="submit"> <input type="button" value="AjaxSubmit" onclick="submitUserFeedback();"/>
    </form:form>

</div>


</body>
</html>