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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="se.vgregion.portal.raindancenotifier.RaindanceNotifier"/>
<h3><fmt:message key="settings" /></h3>
<portlet:defineObjects />

<portlet:actionURL var="savePreferences" />

<portlet:actionURL var="formAction" />

<form:form modelAttribute="tyckTillSettings" htmlEscape="false" method="post" action="${formAction}">
  <table>
    <tr>
      <td>Test attribute:</td>
      <td>
        <form:input path="testAttribute" size="10" maxlength="10" />
        <br/>
        <form:errors path="testAttribute" cssStyle="color:red" />
      </td>
    </tr>
  </table>
  <input value=<fmt:message key="save" /> type="submit">
</form:form>