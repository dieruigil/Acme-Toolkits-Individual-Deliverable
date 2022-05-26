<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="inventor.chimpum.form.label.code" path="code"/>
	<acme:input-moment readonly="true" code="inventor.chimpum.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="inventor.chimpum.form.label.title" path="title"/>
	<acme:input-textbox code="inventor.chimpum.form.label.description" path="description"/>
	<acme:input-moment code="inventor.chimpum.form.label.start-date" path="startDate"/>
	<acme:input-moment code="inventor.chimpum.form.label.finish-date" path="finishDate"/>
	<acme:input-money code="inventor.chimpum.form.label.budget" path="budget"/>
	<acme:input-textbox code="inventor.chimpum.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.chimpum.form.button.create" action="/inventor/chimpum/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:submit code="inventor.chimpum.form.button.update" action="/inventor/chimpum/update"/>
			<acme:submit code="inventor.chimpum.form.button.delete" action="/inventor/chimpum/delete"/>
			<acme:button code="inventor.chimpum.form.button.artifact" action="/inventor/artifact/list-with-chimpum?chimpumId=${chimpumId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>