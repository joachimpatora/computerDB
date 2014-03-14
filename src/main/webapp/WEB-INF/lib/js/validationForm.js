function validationForm()
{
	if ($("#nameInput").val()!="") {
			$("#formAdd").submit();
	}
	else {
		$("#message").html("Un ordinateur doit poss&eacute;der au moins un nom.");
	}
}