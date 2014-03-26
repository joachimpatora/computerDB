<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@ taglib tagdir="/WEB-INF/tags" prefix="paging" %>
<jsp:include page="../header.jsp" />

<section id="main">
	<div id="message">${message}</div>
	<h1><span style="float: right">
    <a href="?lang=en">en</a>|<a href="?lang=fr">fr</a>
	</span></h1>
	<h1 id="homeTitle">${totalNbOfComp} <spring:message code="label.dashboard.totalnb"/></h1>
	
	
	<div id="actions">
		<form action="dashboard" method="POST" id="searchform">
			<input type="search" id="searchbox" name="search"
				value="${!empty param.search ? param.search : ''}" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="<spring:message code="button.filterbyname"/>"
				class="btn primary">
		</form>
		<a class="btn success" id="add" href="addComputer"><spring:message code="label.addcomputer"/></a>
	</div>
	
	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<th><a href="?orderBy=${ orderBy == 'orderByNameAsc' ? 'orderByNameDesc' : 'orderByNameAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}${!empty param.lang ? '&lang='.concat(param.lang) : ''}"><spring:message code="label.computername"/></a></th>
				<th><a href="?orderBy=${ orderBy == 'orderByIntroAsc' ? 'orderByIntroDesc' : 'orderByIntroAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}${!empty param.lang ? '&lang='.concat(param.lang) : ''}"><spring:message code="label.introductiondate"/></a></th>
				<!-- Table header for Discontinued Date -->
				<th><a href="?orderBy=${ orderBy == 'orderByOutroAsc' ? 'orderByOutroDesc' : 'orderByOutroAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}${!empty param.lang ? '&lang='.concat(param.lang) : ''}"><spring:message code="label.discontinueddate"/></a></th>
				<!-- Table header for Company -->
				<th><a href="?orderBy=${ orderBy == 'orderByCompanyAsc' ? 'orderByCompanyDesc' : 'orderByCompanyAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}${!empty param.lang ? '&lang='.concat(param.lang) : ''}"><spring:message code="label.company"/></a></th>
			</tr>
		</thead>
		
		<tbody>
			
			<c:forEach items="${listOfComputers}" var="lstcomp">
				<tr>
					<td><a href="editComputer?compId=${lstcomp.getId()}" onclick="">${lstcomp.getName()}</a></td>
					<td>${lstcomp.getIntroducedDate()}</td>
					<td>${lstcomp.getDiscontinuedDate()}</td>
					<td>${lstcomp.getCompanyname()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<paging:Pagination></paging:Pagination>
		
</section>

<jsp:include page="../footer.jsp" />
