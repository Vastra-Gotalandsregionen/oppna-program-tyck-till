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

<!-- TODO Fixa urler till REST-gränssnittet -->
<!-- TODO If/when deploying as a portlet we must remove all jsp-tags -->
<!-- TODO fixa så att det går att köra utan mus... tabindex etc -->

<html xmlns:xf="http://www.w3.org/2002/xforms"
    xmlns="http://www.w3.org/1999/xhtml"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ev="http://www.w3.org/2001/xml-events"
    xml:lang="sv" lang="sv">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
 			
 	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.6.0/build/reset-fonts-grids/reset-fonts-grids.css"/>
	<link rel="stylesheet" type="text/css" href="../style/style.css"/>
	
	<fmt:setBundle basename="messages"/>
	
	<title><fmt:message key="incidentreport.pagetitle"/></title>
	
	<xf:model id="main-model">
		<!-- Main instance - holds incidentReport data that is sent to the server -->
            <xf:instance id="incidentReport-instance">
                <incidentReport xmlns="">
                	<reportType>error</reportType>
                	<errorType/>
	                	
                	<timeStamp/>
                	<defaultErrorMessage><%=(request.getParameter("errorMessage") == null)? "": request.getParameter("errorMessage") %></defaultErrorMessage>
                	<description></description>
                	<feedback>
	                	<sendFeedback>true</sendFeedback>
	                	<feedbackBySms activated="false">
	                		<phoneNumber></phoneNumber>
	                	</feedbackBySms>
	                	<feedbackByPhone activated="false">
	                		<phoneNumber></phoneNumber>
	                	</feedbackByPhone>
	                	<feedbackByMail activated="false">
	                		<email></email>
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
            <xf:bind nodeset="defaultErrorMessage" relevant="if (. = '') then false() else true()" readonly="true()"/>
            
            <xf:bind nodeset="feedback/sendFeedback" type="xs:boolean" />
            
            <xf:bind nodeset="feedback/feedbackBySms/@activated" type="xs:boolean" 
            	readonly="/incidentReport/feedback/sendFeedback = 'false'" calculate="if(/incidentReport/feedback/sendFeedback ='false') then 'false' else . "/>
            <xf:bind nodeset="feedback/feedbackByMail/@activated" type="xs:boolean" 
            	readonly="/incidentReport/feedback/sendFeedback = 'false'" calculate="if(/incidentReport/feedback/sendFeedback ='false') then 'false' else . "/>
            <xf:bind nodeset="feedback/feedbackByPhone/@activated" type="xs:boolean" 
            	readonly="/incidentReport/feedback/sendFeedback = 'false'" calculate="if(/incidentReport/feedback/sendFeedback ='false') then 'false' else . "/>
            
            <xf:bind nodeset="screenShot" relevant="/incidentReport/defaultErrorMessage = ''"/>
            <xf:bind nodeset="screenShot/sendScreenShot" type="xs:boolean" readonly="false()" />
            
            <xf:bind nodeset="screenShot/files/file" type="xs:base64Binary" 
            	readonly="/incidentReport/screenShot/sendScreenShot = 'false'" />  <!-- This does not seem to work initially? -->
           
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
                	<errorType value="someOtherError">
                		<label><fmt:message key="incidentreport.errorTypes.someMiscError.label"/></label>
                	</errorType>
                	<errorType value="someOtherError2">
	                	<label><fmt:message key="incidentreport.errorTypes.someMiscError.label"/></label>
                	</errorType>
                </errorTypes>
            </xf:instance>
            
            <!-- A template that is inserted into the main incidentReport instance if the 
            user wants to add another file under screenShot --> 
            <xf:instance id="file-template">
            	<file filename="" mediatype="" size="" xmlns=""/>
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
            	<xf:bind nodeset="add-upload-trigger" readonly="false()"/>
            	<xf:bind nodeset="remove-upload-trigger" relevant="count(instance('incidentReport-instance')/screenShot/files/file) &gt; 1"/>
            </xf:bind>
            
             <!-- Submission of the main instance --> 
            <xf:submission id="submitIncidentReport"
				action="<%=request.getRequestURL().toString().replace(request.getServletPath(), "") %>/resource/incidentReport"
				method="post"
				replace="none" instance="incidentReport-instance">
				<xf:load resource="<%=request.getRequestURL() + "?" + request.getQueryString()%>"
						            show="replace" ev:event="xforms-submit-done"/>
			</xf:submission>
        </xf:model>
</head>
<body> 
	<div id="custom-doc">
		<div id="hd"></div>
		<div id="bd">
			<xf:group ref="reportType"><!-- Group on reportType means that the heading 
			will only be shown when the reportType is relevant (active), 
			i.e. when there are no defaultErrorMessage -->
				<h2><fmt:message key="incidentreport.reportTypes.heading"/></h2>
			</xf:group>
				<!-- The reportType will only be shown when there are no defaultErrorMessage 
				(see 'relevant' attr for reportType in the bind section above) -->
				<xf:select1 ref="reportType" appearance="full">
					<p>
						        <xf:itemset nodeset="instance('reportTypes-instance')/reportType" >
						            <xf:label ref="label"  class="newline"/>
						        	<xf:value ref="@value"/>
								</xf:itemset>
								<!-- when this value changes the sendScreenShot checkbox should be updated -->
								<xf:setvalue ev:event="xforms-value-changed" 
								 	ref="instance('incidentReport-instance')/screenShot/sendScreenShot" 
								 	value="if(/incidentReport/reportType ='criticism') then 'false' else 'true'"/> 
								 	 
					</p>		     
				</xf:select1>
				
				
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
					<xf:output ref="defaultErrorMessage">
						<xf:label><fmt:message key="incidentreport.description.auto"/></xf:label>
					</xf:output>
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
				          	<xf:trigger appearance="minimal">
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
		            	<xf:trigger appearance="minimal">
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
					<p>
						<xf:submit submission="submitIncidentReport">
							<xf:label><fmt:message key="incidentreport.submit.label"/></xf:label>
						</xf:submit> 
						
						<!-- xf:reset is unfortunately not supported: http://forge.ow2.org/tracker/index.php?func=detail&aid=303946&group_id=168&atid=350207 
						otherwise this would have been a nicer way to do this than a load
						 <xf:trigger>
					        <xf:label><fmt:message key="incidentreport.cancel.label"/></xf:label>
					        <xf:reset ev:event="DOMActivate" model="main-model"/>
					      </xf:trigger> -->
					     <xf:trigger>
						    <xf:label><fmt:message key="incidentreport.cancel.label"/></xf:label>
						    <xf:load resource="<%=request.getRequestURL() + "?" + request.getQueryString()%>"
						            show="replace" ev:event="DOMActivate"/>
						 </xf:trigger> 
					</p>
				</div>
			</div>
		</div>
	</div>
</body> 

</html>