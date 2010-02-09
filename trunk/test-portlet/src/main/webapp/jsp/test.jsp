<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />

<a href="<portlet:actionURL><portlet:param name="throwExceptionInAction" value="true"/></portlet:actionURL>">Testa att kasta exception i action-fasen</a>
<br/><a href="<portlet:renderURL><portlet:param name="throwExceptionInView" value="true"/></portlet:renderURL>">Testa att kasta ett exception i view-fasen</a>


