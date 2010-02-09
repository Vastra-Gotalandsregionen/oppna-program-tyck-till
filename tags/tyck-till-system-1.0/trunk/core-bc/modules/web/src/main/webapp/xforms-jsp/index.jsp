<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%--

    Copyright 2009 Västra Götalandsregionen

      This library is free software; you can redistribute it and/or modify
      it under the terms of version 2.1 of the GNU Lesser General Public
      License as published by the Free Software Foundation.

      This library is distributed in the hope that it will be useful,
      but WITHOUT ANY WARRANTY; without even the implied warranty of
      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
      GNU Lesser General Public License for more details.

      You should have received a copy of the GNU Lesser General Public
      License along with this library; if not, write to the
      Free Software Foundation, Inc., 59 Temple Place, Suite 330,
      Boston, MA 02111-1307  USA

--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- TODO Ev fixa urlerna till REST-gränssnittet, som i dagsläget är "relativa" 
kontra denna jsp-sidas url (Orbeon verkar dock inte stödja riktiga relativa länkar 
så detta är "fulkodat" - se submission nedan) -->
<!-- TODO If/when deploying as a portlet we must remove all jsp-tags. Orbeon doesn't 
seem to support jsp in portlet mode (?)-->
<!-- TODO Check and fix the user interface according to the VGR guidelines (as much as possible, 
we still have to rely on javascript since Orbeons no-script-mode isn't working properly 
in the beta-version we're using), e.g. it should be possible to use without a mouse etc. -->
<!-- TODO when the page times out orbeon sends out it's default error message, including a link to orbeon's main page - 
see if this is configurable in Orbeon -->
<!-- TODO see if we can use css to make the upload-field bigger somehow -->


<%@page import="java.net.URLEncoder"%><html xmlns:xf="http://www.w3.org/2002/xforms"
    xmlns="http://www.w3.org/1999/xhtml"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ev="http://www.w3.org/2001/xml-events"
    xml:lang="sv" lang="sv">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
            
    <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.6.0/build/reset-fonts-grids/reset-fonts-grids.css"/>
    <link rel="stylesheet" type="text/css" href="../style/style.css"/>
    <script type="text/javascript">
      var closePopup= function()
        {
            window.close();
        }  
               
    </script>

    
    <fmt:setBundle basename="messages"/>
    
    <title><fmt:message key="incidentreport.pagetitle"/></title>
    
    <xf:model id="main-model">
        <!-- Main instance - holds incidentReport data that is sent to the server -->
            <xf:instance id="incidentReport-instance">
                <incidentReport xmlns="">
                    <reportType>error</reportType>
                    <errorType>${param.errorType}</errorType>
                    <timestamp>${param.timestamp}</timestamp>  
                    <browser>${header["user-agent"]}</browser>
                    <referer></referer>
                    <ip-address>${pageContext.request.remoteAddr}</ip-address>
                    <application-name>${param.context}</application-name>
                    <name-space>${param.namespace}</name-space>
                    <javascript-enabled>${param.javasscript}</javascript-enabled>
                    <report-method>${param.reportMethod}</report-method>
                    <report-email>${param.reportEmail}</report-email>
                    <userid>${param.userid}</userid>
                    <defaultErrorMessage>${param.errorMessage}</defaultErrorMessage>
                    <description></description>
                    <feedback>
                        <sendFeedback>true</sendFeedback>
                        <feedbackBySms activated="false">
                            <phoneNumber>${param.phoneNumber}</phoneNumber>
                        </feedbackBySms>
                        <feedbackByPhone activated="false">
                            <phoneNumber>${param.phoneNumber}</phoneNumber>
                        </feedbackByPhone>
                        <feedbackByMail activated="true">
                            <email>${param.email}</email>
                        </feedbackByMail>
                    </feedback>
                    <screenShot>
                        <sendScreenShot>true</sendScreenShot>
                        <files>
                            <file filename="" mediatype=""/>
                        </files>
                    </screenShot>   
                </incidentReport>
            </xf:instance>
            
            <!-- Bindings for the main instance -->
            <xf:bind nodeset="reportType" relevant="/incidentReport/defaultErrorMessage = ''"/>
            <xf:bind nodeset="timeStamp" type="xs:dateTime" calculate="if (. = '') then current-dateTime() else ." readonly="false()"/> 
            <xf:bind nodeset="defaultErrorMessage" relevant="if (. = '') then false() else true()" readonly="true()"/><!-- defaultErrorMessage is only relevant if it's not empty -->
            
            <xf:bind nodeset="feedback/sendFeedback" type="xs:boolean" />
            
            <xf:bind nodeset="feedback/feedbackBySms/@activated" type="xs:boolean" 
                readonly="/incidentReport/feedback/sendFeedback = 'false'" calculate="if(/incidentReport/feedback/sendFeedback ='false') then 'false' else . "/>
            <xf:bind nodeset="feedback/feedbackByMail/@activated" type="xs:boolean" 
                readonly="/incidentReport/feedback/sendFeedback = 'false'" calculate="if(/incidentReport/feedback/sendFeedback ='false') then 'false' else . "/>
            <xf:bind nodeset="feedback/feedbackByPhone/@activated" type="xs:boolean" 
                readonly="/incidentReport/feedback/sendFeedback = 'false'" calculate="if(/incidentReport/feedback/sendFeedback ='false') then 'false' else . "/>
            
            <xf:bind nodeset="feedback/feedbackBySms/phoneNumber" type="xs:string" 
                readonly="/incidentReport/feedback/feedbackBySms/@activated = 'false'"/>
            <xf:bind nodeset="feedback/feedbackByPhone/phoneNumber" type="xs:string" 
                readonly="/incidentReport/feedback/feedbackByPhone/@activated = 'false'"/>
            <xf:bind nodeset="feedback/feedbackByMail/email" type="xs:string" 
                readonly="/incidentReport/feedback/feedbackByMail/@activated = 'false'"/>
            
            <xf:bind nodeset="screenShot" relevant="/incidentReport/defaultErrorMessage = ''"/>
            <xf:bind nodeset="screenShot/sendScreenShot" type="xs:boolean" readonly="false()" />

            <!-- anyURI means the file will be uploaded to a temp directory upon submission -->            
            <xf:bind nodeset="screenShot/files/file" type="xs:anyURI" 
                readonly="/incidentReport/screenShot/sendScreenShot = 'false'" />  <!-- Note: "Readonly" on sendScreenShot does not seem to work initially for some reason.-->
           
           <!-- Instance that holds the different reportTypes -->
             <xf:instance id="reportTypes-instance">
                <reportTypes xmlns="">
                    <reportType value="error">
                        <label><fmt:message key="incidentreport.reportTypes.error.label"/></label>
                    </reportType>
                    <reportType value="suggestion">
                        <label><fmt:message key="incidentreport.reportTypes.suggestion.label"/></label>
                    </reportType>
                    <reportType value="criticism">
                        <label><fmt:message key="incidentreport.reportTypes.criticism.label"/></label>
                    </reportType>
                </reportTypes>
            </xf:instance>
             
            <!-- Instance that holds the different functional errorTypes --> 
            <xf:instance id="function-errorTypes-instance">
                <errorTypes xmlns="">
                    <errorType value="errorMessage">
                        <label><fmt:message key="incidentreport.errorTypes.errorMessage.label"/></label>
                    </errorType>
                    <errorType value="accessDenied">
                        <label><fmt:message key="incidentreport.errorTypes.accessDenied.label"/></label>
                    </errorType>
                    <errorType value="pageLoadError">
                        <label><fmt:message key="incidentreport.errorTypes.pageLoadError.label"/></label>
                    </errorType>
                    <errorType value="portalHangs">
                        <label><fmt:message key="incidentreport.errorTypes.portalHangs.label"/></label>
                    </errorType>
                </errorTypes>
            </xf:instance>
            
            <!-- Instance that holds the different layout errorTypes --> 
            <xf:instance id="layout-errorTypes-instance">
                <errorTypes xmlns="">
                    <errorType value="readabilityError">
                        <label><fmt:message key="incidentreport.errorTypes.readabilityError.label"/></label>
                    </errorType>
                    <errorType value="badContrastError">
                        <label><fmt:message key="incidentreport.errorTypes.badContrastError.label"/></label>
                    </errorType>
                    <errorType value="wrongButtonError">
                        <label><fmt:message key="incidentreport.errorTypes.wrongButtonError.label"/></label>
                    </errorType>
                </errorTypes>
            </xf:instance>
            
            <!-- Instance that holds the different misc errorTypes --> 
            <xf:instance id="misc-errorTypes-instance">
                <errorTypes xmlns="">
                    <errorType value="someMiscError">
                        <label><fmt:message key="incidentreport.errorTypes.someMiscError.label"/></label>
                    </errorType>
                </errorTypes>
            </xf:instance>
            
            <!-- A template that is inserted into the main incidentReport instance if the 
            user wants to add another file under screenShot --> 
            <xf:instance id="file-template">
                <file filename="" mediatype="" xmlns=""/>
            </xf:instance>
            
            <!-- Control instance that is used under screenShots --> 
            <xf:instance id="control-instance">
                <control xmlns="">
                    <add-upload-trigger/>
                    <remove-upload-trigger/>
                </control>
            </xf:instance>
            
             <!-- Bindings for the control instance that is used under screenShots --> 
            <xf:bind nodeset="instance('control-instance')">
                <xf:bind nodeset="add-upload-trigger" 
                    readonly="instance('incidentReport-instance')/screenShot/sendScreenShot = 'false'" />
                <xf:bind nodeset="remove-upload-trigger" 
                    readonly="instance('incidentReport-instance')/screenShot/sendScreenShot = 'false'" 
                    relevant="count(instance('incidentReport-instance')/screenShot/files/file) &gt; 1"/>
            </xf:bind>
            
             <!-- Submission of the main instance --> 
            <xf:submission id="submitIncidentReport"
                action="<%=request.getRequestURL().toString().replace(request.getServletPath(), "") %>/resource/incidentReport"
                method="post"
                replace="none" instance="incidentReport-instance">
                <xf:load resource="<%=request.getRequestURL().toString().replace(request.getServletPath(), "") %>/tack.jsp"
                                    show="replace" ev:event="xforms-submit-done"/>
            </xf:submission>
            
            <xf:submission id="submitIncidentReportAndClose"
                action="<%=request.getRequestURL().toString().replace(request.getServletPath(), "") %>/resource/incidentReport"
                method="post"
                replace="none" instance="incidentReport-instance">
                    <xf:load resource="<%=request.getRequestURL().toString().replace(request.getServletPath(), "") %>/tack.jsp" ev:event="xforms-submit-done"/>
            </xf:submission>
            
            
        </xf:model>
</head>
<body> 
    <div id="custom-doc">
        <div id="hd"></div>
        <div id="bd">
            <xf:group ref="reportType"><!-- Group on reportType means that the heading 
            will only be shown when the reportType is relevant (active), 
            i.e. when we're not in the "automatic send error message"-mode -->
                <h2><fmt:message key="incidentreport.reportTypes.heading"/></h2>
            </xf:group>
                <!-- The reportType will only be shown when there are no defaultErrorMessage 
                (see 'relevant' attr for reportType in the bind section above) -->
                <p>
                    <xf:select1 ref="reportType" appearance="full">
                    
                                <xf:itemset nodeset="instance('reportTypes-instance')/reportType" >
                                    <xf:label ref="label"  class="newline"/>
                                    <xf:value ref="@value"/>
                                </xf:itemset>
                                <!-- when this value changes the sendScreenShot checkbox should be updated -->
                                <xf:setvalue ev:event="xforms-value-changed" 
                                    ref="instance('incidentReport-instance')/screenShot/sendScreenShot" 
                                    value="if(/incidentReport/reportType ='criticism') then 'false' else 'true'"/> 
                                 
                    </xf:select1>
                </p>
                
            <h2><fmt:message key="incidentreport.errorTypes.heading"/></h2>         
                <div class="yui-gb"> 
                    <div class="yui-u first"> 
                       
                      <h3><fmt:message key="incidentreport.reportTypes.function.heading"/></h3>
                      <p>
                            <xf:select ref="errorType" appearance="full" navindex="8">
                                <xf:itemset nodeset="instance('function-errorTypes-instance')/errorType">
                                    <xf:label ref="label"/>
                                    <xf:value ref="@value"/>
                                </xf:itemset>
                            </xf:select>
                             
                       </p> 
                    </div> 
                    <div class="yui-u"> 
                        
                        <h3><fmt:message key="incidentreport.reportTypes.layout.heading"/></h3>
                        <p> 
                            <xf:select ref="errorType" appearance="full" navindex="8">
                                <xf:itemset nodeset="instance('layout-errorTypes-instance')/errorType">
                                    <xf:label ref="label"/>
                                    <xf:value ref="@value"/>
                                </xf:itemset>
                            </xf:select> 
                        </p> 
                    </div> 
                    <div class="yui-u"> 
                       
                        <h3><fmt:message key="incidentreport.reportTypes.misc.heading"/></h3>
                        <p> 
                            <xf:select ref="errorType" appearance="full" navindex="8">
                                <xf:itemset nodeset="instance('misc-errorTypes-instance')/errorType">
                                    <xf:label ref="label"/>
                                    <xf:value ref="@value"/>
                                </xf:itemset>
                            </xf:select> 
                        </p> 
                    </div> 
                </div> 
                
                <h2><fmt:message key="incidentreport.description.heading"/></h2>
                <p>
                    
                    <xf:group ref="defaultErrorMessage">
                        <div id="defaultErrorMessageDiv">
                            <xf:output ref=".">
                                <xf:label><b><fmt:message key="incidentreport.description.auto"/></b>&nbsp;</xf:label>
                            </xf:output>
                        </div>
                        <br/>
                    </xf:group>
                    
                </p>
                <p><xf:textarea ref="description"  incremental="true" /></p>
                
                
                <h2><fmt:message key="incidentreport.feedback.heading"/></h2>
                <p>             
                    <xf:input ref="feedback/sendFeedback" >
                    </xf:input> <fmt:message key="incidentreport.feedback.sendFeedback.label"/>
                </p>
                <div class="yui-gb"> 
                    <div class="yui-u first"> 
                        <p>
                            <xf:input ref="feedback/feedbackByMail/@activated" /> <fmt:message key="incidentreport.feedback.feedbackByMail.label"/>
                        </p>
                        <p>
                            <xf:input ref="feedback/feedbackByMail/email" /> 
                        </p>
                    </div>
                    <div class="yui-u">
                        <p>
                            <xf:input ref="feedback/feedbackBySms/@activated" /> <fmt:message key="incidentreport.feedback.feedbackBySms.label"/>
                        </p> 
                        <p>
                            <xf:input ref="feedback/feedbackBySms/phoneNumber" /> 
                        </p>
                    </div>
                    <div class="yui-u"> 
                        <p>
                            <xf:input ref="feedback/feedbackByPhone/@activated" /> <fmt:message key="incidentreport.feedback.feedbackByPhone.label"/>
                        </p>
                        <p>
                            <xf:input ref="feedback/feedbackByPhone/phoneNumber" /> 
                        </p>
                    </div>
                </div>                      
                
                <xf:group ref="screenShot">
                    <h2><fmt:message key="incidentreport.screenShot.heading"/></h2>
                    <p><xf:input ref="sendScreenShot"/> <fmt:message key="incidentreport.screenShot.sendScreenShot.label"/></p>
                    <p><fmt:message key="incidentreport.screenShot.infoText.1"/></p> 
                    <p><fmt:message key="incidentreport.screenShot.infoText.2"/></p> 
                    <p><fmt:message key="incidentreport.screenShot.infoText.3"/></p> 
                    <p><fmt:message key="incidentreport.screenShot.infoText.4"/></p> 
                    <p><fmt:message key="incidentreport.screenShot.infoText.5"/></p>            
                    <p><fmt:message key="incidentreport.screenShot.infoText.6"/>
                        <xf:repeat nodeset="files/file" id="file-repeat">
                               <br/>
                                <xf:upload ref=".">
                                    <xf:filename ref="@filename"/>
                                    <xf:mediatype ref="@mediatype"/>
                                </xf:upload> 
                                 
                        </xf:repeat>
                       <xf:group ref="instance('control-instance')/remove-upload-trigger">
                            <xf:trigger appearance="minimal" ref="instance('control-instance')/remove-upload-trigger">
                                <xf:label><fmt:message key="incidentreport.screenShot.remove.label"/></xf:label>
                                <xf:delete ev:event="DOMActivate" nodeset="instance('incidentReport-instance')/screenShot/files/file" at="last()"/>
                             </xf:trigger>
                          </xf:group>
                          <!-- 
                          Note: a bug in orbeon prevents us from having a delete link on each row. therefore only the last 
                          one can be deleted. 
                          See http://forge.ow2.org/tracker/index.php?func=detail&aid=305539&group_id=168&atid=350207 
                          -->
                    </p>
                    <p>
                     <xf:group ref="instance('control-instance')/add-upload-trigger">
                        <xf:trigger appearance="minimal" ref="instance('control-instance')/add-upload-trigger">
                            <xf:label><fmt:message key="incidentreport.screenShot.add.label"/></xf:label>
                            <xf:insert ev:event="DOMActivate" nodeset="instance('incidentReport-instance')/screenShot/files/file" at="last()" position="after" origin="instance('file-template')"/>
                        </xf:trigger>
                     </xf:group>
                    </p>
                </xf:group>
        </div>
        <div id="ft">
            <div id="yui-g"> 
                <div id="yui-u first"/>
                <div id="yui-u" style="text-align: right">
                    <br/>
                    <!-- Get the right buttons for each "mode", i.e. the default error-popup or when the user has pressed "Tyck till".
                    This is done by grouping on defaultErrorMessage and reportType, which are mutually exclusive. If defaultErrorMessage
                    is relevant then we are in the pop up-mode (when an error has occured in a portlet) -->
                    <xf:group ref="defaultErrorMessage">
                        <p>
                            <xf:submit submission="submitIncidentReportAndClose">
                                <xf:label><fmt:message key="incidentreport.submitandclose.label"/></xf:label>
                            </xf:submit> 
                            
                             <xf:trigger>
                                <xf:label><fmt:message key="incidentreport.cancel.label"/></xf:label>
                                <xf:load resource="javascript:closePopup()" ev:event="DOMActivate"/>
                             </xf:trigger> 
                        </p>
                    </xf:group>
                    <!-- If reportType is relevant then we are in the IFrame-mode, i.e. the user has
                    manually pressed "Tyck till" in the portal -->
                    <xf:group ref="reportType">
                        <p>
                            <xf:submit submission="submitIncidentReport">
                                <xf:label><fmt:message key="incidentreport.submit.label"/></xf:label>
                            </xf:submit> 
                            
                            <!-- Note: xf:reset is unfortunately not supported in Orbeon: http://forge.ow2.org/tracker/index.php?func=detail&aid=303946&group_id=168&atid=350207 
                            otherwise this would have been a nicer way to do this than a load-->
                             <xf:trigger>
                                <xf:label><fmt:message key="incidentreport.clean.label"/></xf:label>
                                <xf:load resource="<%=request.getRequestURL() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString().replaceAll("&", "&amp;")))%>"
                                        show="replace" ev:event="DOMActivate"/>
                             </xf:trigger> 
                        </p>
                    </xf:group>
                </div>
            </div>
        </div>
    </div>
</body> 

</html>