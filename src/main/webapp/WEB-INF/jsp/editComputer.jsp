<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="../header.jsp" />
<script type="text/javascript" src="validationForm.js">

</script>
<section id="main">

	<h1><spring:message code="label.editcomputer"/></h1>
	<h2><spring:message code="label.editingcomputer"/> ${computer.getName()}</h2>
	<div id="message">${message}</div>
	<form:form action="updateComputer" method="POST" modelAttribute="ComputerDto" name="computeredition">
		<fieldset>
			<input type="hidden" value="${computer.getId()}" name="hiddenid">
			<input type="hidden" value="" name="action" id="actionInput">
			<div class="clearfix">
				<label for="name"><spring:message code="label.computername"/></label>
				<div class="input">
					<form:input path="name" id="nameInput" value="${computer.getName()}"/>
					<form:errors path="name" cssStyle="color: red;" />
					<span class="help-inline"><spring:message code="label.helpname"/></span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced"><spring:message code="label.introductiondate"/></label>
				<div class="input">
					<form:input path="introducedDate" id="introducedInput" value="${computer.getIntroducedDate()}"/>
					<form:errors path="introducedDate" cssStyle="color: red;" />
					<span class="help-inline"><spring:message code="label.helpdate"/></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued"><spring:message code="label.discontinueddate"/></label>
				<div class="input">
					<form:input path="discontinuedDate" id="discontinuedInput" value="${computer.getDiscontinuedDate()}" />
					<form:errors path="discontinuedDate" cssStyle="color: red;" />
					<span class="help-inline"><spring:message code="label.helpdate"/></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company"><spring:message code="label.companyname"/></label>
				<div class="input">
					<form:select id="companyInput"  path="companyid" items="${listOfCompanies}" itemValue="id" itemLabel="name" />
					<form:errors path="companyid" cssStyle="color: red;" />
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="<spring:message code='button.edit.title'/>" class="btn primary" name="updateButton" >
			<input type="submit" value="<spring:message code='button.delete.title'/>" class="btn danger" name="updateButton" >
			<a href="dashboard?main=accueil" class="btn"><spring:message code="label.cancel"/></a>
		</div>
	</form:form>
</section>

<jsp:include page="../footer.jsp" />