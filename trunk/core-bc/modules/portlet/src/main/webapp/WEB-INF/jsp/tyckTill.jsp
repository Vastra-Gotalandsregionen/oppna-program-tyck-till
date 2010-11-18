<%--

    Copyright 2010 Västra Götalandsregionen

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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<style type="text/css">
    <%@ include file="/style/style.css"%>
</style>

<portlet:actionURL var="saveFeedbackForm" />

<portlet:actionURL var="formAction" />
<div class="module-content accordion">
<h5>Vad handlar ditt �rende om ?</h5>
<form:form modelAttribute="userFeedback" htmlEscape="false" method="post" action="${formAction}">
  <table>
    <tr>
      <td>Test attribute:</td>
      <td><form:input path="testAttribute" size="10" maxlength="10" /> <br />
      <form:errors path="testAttribute" cssStyle="color:red" /></td>
    </tr>
    <tr>
      <td>Test box</td>
      <td><form:checkbox path="testBoolAttribute"/></td>
    </tr>
    <tr>
      <td>Test radiobutton</td>
      <td>Option one<form:radiobutton path="testRadioChannel" value="Option1"/> <br/>
          Option two<form:radiobutton path="testRadioChannel" value="Option2"/> 
      </td>
    </tr>
  </table>
  <input value=<fmt:message key="save" /> type="submit">
</form:form>
</div>

