<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />
<script type="text/javascript" src="validationForm.js">

</script>
<section id="main">

	<h1>Edit Computer</h1>
	<h2>You are editing ${computer.getName()}</h2>
	<div id="message">${message}</div>
	<form action="EditControl" method="POST" id="formAdd" name="computeredition">
		<fieldset>
			<input type="hidden" value="${computer.getId()}" name="hiddenid">
			<input type="hidden" value="" name="action" id="actionInput">
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" id="nameInput" value="${computer.getName()}"/>
					<span class="help-inline">Required</span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introducedDate" id="introducedInput" value="${computer.getIntroducedDate()}"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinuedDate" id="discontinuedInput" value="${computer.getDiscontinuedDate()}" />
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company" id="companyInput">
						<option value="0">--</option>
						<c:forEach items="${listComp}" var="comp">
							<c:choose> 
								<c:when test="${comp.getName().trim().equals(computer.getCompany().trim())}">
									<option selected value="${comp.getId()}">${comp.getName()}</option>
								</c:when>
								<c:otherwise>
									<option value="${comp.getId()}">${comp.getName()}</option>
								</c:otherwise>
							</c:choose>-->
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<a href="#" class="btn primary" onclick='$("#actionInput").val("DELETE"); $("#formAdd").submit();'>Delete</a>
			or <a href="#" onclick='$("#actionInput").val("EDIT"); validationForm();' class="btn primary" >Edit</a>
			or <a href="Complist?main=accueil" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />