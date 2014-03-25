<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="queryParameters" required="false"
	type="java.util.Map" rtexprvalue="true"%>

<c:url value="./dashboard" var="variableURL">
	<c:forEach items="${queryParameters}" var="entry">
		<c:param name="${entry.key}" value="${entry.value}" />
	</c:forEach>
</c:url>

<ul class="pagination">
	<c:if test="${currentPage != 1}">
		<c:url value="${variableURL}" var="url">
			<c:param name="page" value="${currentPage - 1}" />
		</c:url>
		<li><a href="${url}">Previous</a></li>
	</c:if>

	<c:forEach begin="1" end="${noOfPages}" var="i">
		<c:choose>
			<c:when test="${currentPage == i}">
				<c:url value="${variableURL}" var="url">
					<c:param name="page" value="${currentPage}" />
				</c:url>
				<li><a>${i}</a></li>
			</c:when>
			<c:otherwise>
				<c:url value="${variableURL}" var="url">
					<c:param name="page" value="${i}" />
				</c:url>
				<a href="${url}"><li>${i}</li></a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:if test="${currentPage < noOfPages}">
		<c:url value="${variableURL}" var="url">
			<c:param name="page" value="${currentPage + 1}" />
		</c:url>
		<a href="${url}"><li>Next</li></a>
	</c:if>
</ul>