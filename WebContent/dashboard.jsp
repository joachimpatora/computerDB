<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="include/header.jsp" />

<section id="main">
	<h1 id="homeTitle">${totalNbOfComp}${listFound.size()} Computers found</h1>
	<div id="actions">
		<form action="Complist" method="POST" id="searchform">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<c:set var="edit" value="1"/>
		<c:set var="add" value="2"/>
		<form action="Controller" method="GET" id="buttonform">
			<a class="btn success" id="add" href="Controller?id=<c:out value="${add}"/>">Add Computer</a>
		</form>
		
	</div>
	
	
		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th>Computer Name</th>
					<th>Introduced Date</th>
					<!-- Table header for Discontinued Date -->
					<th>Discontinued Date</th>
					<!-- Table header for Company -->
					<th>Company</th>
				</tr>
			</thead>
			
			<tbody>
				<c:if test="${listFound != null}">
					<c:forEach items="${listFound}" var="lstfnd">
						<tr>
							<td><a href="Controller?id=<c:out value="1"/>&compId=${lstfnd.getId()}" onclick="">${lstfnd.getName()}</a></td>
							<td>${lstfnd.getIntroducedDate()}</td>
							<td>${lstfnd.getDiscontinuedDate()}</td>
							<td>${lstfnd.getCompany()}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:forEach items="${listOfComputers}" var="lstcomp">
					<tr>
						<td><a href="Controller?id=<c:out value="1"/>&compId=${lstcomp.getId()}" onclick="">${lstcomp.getName()}</a></td>
						<td>${lstcomp.getIntroducedDate()}</td>
						<td>${lstcomp.getDiscontinuedDate()}</td>
						<td>${lstcomp.getCompany()}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="pagination">
	        <c:if test="${currentPage != 1}">
	       	 	<a href="Complist?page=${currentPage - 1}">Previous</a>
	    	</c:if>
	    	
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage == i}">
                        ${i}
                    </c:when>
                    <c:otherwise>
                        <a href="Complist?page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
		       
            <c:if test="${currentPage < noOfPages}">
   				<a href="Complist?page=${currentPage + 1}">Next</a>
			</c:if>
    	</div>
</section>

<jsp:include page="include/footer.jsp" />
