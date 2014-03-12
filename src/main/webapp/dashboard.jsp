<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="paging" %>
<jsp:include page="include/header.jsp" />

<section id="main">
	<div id="message">${message}</div>
	<h1 id="homeTitle">${totalNbOfComp} Computers found</h1>
	
	<div id="actions">
		<form action="Complist" method="POST" id="searchform">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<c:set var="add" value="2"/>
		<form action="PrintScrController" method="GET" id="buttonform">
			<a class="btn success" id="add" href="PrintScrController?id=<c:out value="${add}"/>">Add Computer</a>
		</form>
		
	</div>
	
	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<th><a href="Complist?orderBy=Name">Computer Name</a></th>
				<th><a href="Complist?orderBy=IntroDate">Introduced Date</a></th>
				<!-- Table header for Discontinued Date -->
				<th><a href="Complist?orderBy=OutroDate">Discontinued Date</a></th>
				<!-- Table header for Company -->
				<th><a href="Complist?orderBy=Company">Company</a></th>
			</tr>
		</thead>
		
		<tbody>
			
			<c:forEach items="${listOfComputers}" var="lstcomp">
				<tr>
					<td><a href="PrintScrController?id=<c:out value="1"/>&compId=${lstcomp.getId()}" onclick="">${lstcomp.getName()}</a></td>
					<td>${lstcomp.getIntroducedDate()}</td>
					<td>${lstcomp.getDiscontinuedDate()}</td>
					<td>${lstcomp.getCompany()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<paging:Pagination></paging:Pagination>
		
</section>

<jsp:include page="include/footer.jsp" />
