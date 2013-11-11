<%@ page trimDirectiveWhitespaces="true" %>
<%@ page pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:eval expression="@environment['project.version']" var="apprev"/>
<spring:url value="/resources-{p}" var="resourceUrl">
  <spring:param name="p" value="${apprev}"/>
</spring:url>

<spring:eval expression="@environment['envbase']" var="envbase"/>
