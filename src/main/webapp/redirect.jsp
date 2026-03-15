<%--
Views should be stored under the WEB-INF folder so that
they are not accessible except through controller process.

This JSP is here to provide a redirect to the controller.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    response.sendRedirect(request.getContextPath() + "/login");
%>
