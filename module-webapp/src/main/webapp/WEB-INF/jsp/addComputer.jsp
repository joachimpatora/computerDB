<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<jsp:include page="../header.jsp" />
<section id="main">

	<h1><spring:message code="label.addcomputer"/></h1>
	<div id="message">${message}</div>
	<form:form action="addComputerResult" modelAttribute="ComputerDto" method="POST" >
		<fieldset>
			<div class="clearfix">
				<label for="name"><spring:message code="label.computername"/></label>
				<div class="input">
					<form:input id="nameInput" path="name"/>
					<form:errors path="name" cssStyle="color: red;" />
					<span class="help-inline"><spring:message code="label.helpname"/></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="introduced"><spring:message code="label.introductiondate"/></label>
				<div class="input">
					<form:input type="date" id="introducedInput" name="introducedDate" path="introducedDate" />
					<form:errors path="introducedDate" cssStyle="color: red;" />
					<span class="help-inline"><spring:message code="label.helpdate"/></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued"><spring:message code="label.discontinueddate"/></label>
				<div class="input">
					<form:input type="date" id="discontinuedInput" name="discontinuedDate" path="discontinuedDate"/>
					<form:errors path="discontinuedDate" cssStyle="color: red;" />
					<span class="help-inline"><spring:message code="label.helpdate"/></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company"><spring:message code="label.companyname"/></label>
				<div class="input">
					<form:select id="companyInput" items="${listOfCompanies}" itemValue="id" itemLabel="name" path="companyid" />
					<form:errors path="companyid" cssStyle="color: red;" />
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="<spring:message code='button.add.title'/>" class="btn primary" class="btn primary">
			<a href="dashboard" class="btn"><spring:message code="label.cancel"/></a>
		</div>
	</form:form>
</section>

<jsp:include page="../footer.jsp" />