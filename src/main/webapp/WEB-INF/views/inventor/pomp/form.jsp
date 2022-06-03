<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox readonly="${acme:anyOf(command, 'show, update, delete')}" code="inventor.pomp.form.label.code" path="code" placeholder="AB-12"/>
	<acme:input-moment readonly="true" code="inventor.pomp.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="inventor.pomp.form.label.name" path="name"/>
	<acme:input-textarea code="inventor.pomp.form.label.explanation" path="explanation"/>
	<acme:input-moment code="inventor.pomp.form.label.start-date" path="startDate"/>
	<acme:input-moment code="inventor.pomp.form.label.finish-date" path="finishDate"/>
	<acme:input-money code="inventor.pomp.form.label.expenditure" path="expenditure"/>
	<acme:input-textbox code="inventor.pomp.form.label.more-info" path="moreInfo"/>
	
	<jstl:choose>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.pomp.form.button.create" action="/inventor/pomp/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:submit code="inventor.pomp.form.button.update" action="/inventor/pomp/update"/>
			<acme:submit code="inventor.pomp.form.button.delete" action="/inventor/pomp/delete"/>
			<acme:button code="inventor.pomp.form.button.artifact" action="/any/artifact/list-with-pomp?pompId=${pompId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>