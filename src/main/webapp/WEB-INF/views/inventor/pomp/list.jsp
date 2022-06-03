<%@ page language="java" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.pomp.list.label.code" path="code"/>
	<acme:list-column code="inventor.pomp.list.label.creation-moment" path="creationMoment"/>
	<acme:list-column code="inventor.pomp.list.label.name" path="name"/>
</acme:list>

<acme:button code="inventor.pomp.form.button.create" action="/inventor/pomp/create"/>