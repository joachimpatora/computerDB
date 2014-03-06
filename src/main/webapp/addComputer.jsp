<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />
<script type="text/javascript" src="validationForm.js">

</script>
<section id="main">

	<h1>Add Computer</h1>
	<div id="message">${message}</div>
	<form action="Controller" id="formAdd" method="POST" >
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" id="nameInput" name="name" />
					<span class="help-inline">Required</span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" id="introducedInput" name="introducedDate" />
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" id="discontinuedInput" name="discontinuedDate" />
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company" id="companyInput">
						<option value="0">--</option>
						<c:forEach items="${listComp}" var="comp">
							<option value="${comp.getId()}">${comp.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<a href="#" onclick="validationForm();" class="btn primary">Add</a>
			or <a href="Complist" class="btn">Cancel</a>
<!-- 			<input type="submit" value="Add" class="btn primary"> -->
<!-- 			or <a href="Complist" class="btn">Cancel</a> -->
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />