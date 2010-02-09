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

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.portlet.*"%>
<%@ page import="java.net.URLEncoder" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<script language="javascript" >
document.write("<iframe src='<%=portletConfig.getInitParameter("TyckTillErrorFormURL") %>?<%=request.getAttribute("errorFormParams")  %>&javasscript=yes' width='100%' 	height='600px' 	frameborder='0' scrolling='no'>  <p>Din webbläsare stödjer ej IFrames.</p></iframe>");
</script>
<noscript>
<iframe 
    src="<%=portletConfig.getInitParameter("TyckTillErrorFormURL") %>?<%=request.getAttribute("errorFormParams")  %>&javasscript=no" 
    width="100%" 
    height="600px"
    frameborder="0"
    scrolling="no">
  <p>Din webbläsare stödjer ej IFrames.</p>
</iframe>
</noscript>
