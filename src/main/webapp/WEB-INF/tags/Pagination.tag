<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ attribute name="currentPage" required="true" type="java.lang.Integer"%> --%>
<%-- <%@ attribute name="noOfPages" required="true" type="java.lang.Integer"%> --%>
<%-- <%@ attribute name="search" required="true" type="java.lang.String"%> --%>

<ul class="pagination">
      <c:if test="${currentPage != 1}">
     	 	<li><a href="Complist?page=${currentPage - 1}" onClick="this.form.search.value=${search}">Previous</a></li>
  		</c:if>
  	
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage == i}">
                    <li><a>${i}</a></li>
                </c:when>
                <c:otherwise>
                    <a href="Complist?page=${i}" onClick="this.form.search.value=${search}"><li>${i}</li></a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
     
        <c:if test="${currentPage < noOfPages}">
		<a href="Complist?page=${currentPage + 1}" onClick="this.form.search.value=${search }"><li>Next</li></a>
	</c:if>
</ul>