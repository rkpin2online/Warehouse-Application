$(document).ready(function () {
	//hide span
	$("#uomTypeError").hide();
	$("#uomModelError").hide();
	$("#descriptionError").hide();
	//declare flag
	var uomtypeError = false;
	var uomModelError = false;
	var descriptionError = false;
	//link events
	$("#uomType").change(function () {
		validate_uomType();
	});
	$("#uomModel").keyup(function () {
		$("#uomModel").val($("#uomModel").val().toUpperCase());
		validate_uomModel();
	});

	$("#description").keyup(function () {
		validate_description();
	});
	//define functions

	//------------Type-----------//
	function validate_uomType() {
		var val = $("#uomType").val();
		if (val == '') {
			$("#uomTypeError").html("Chooe <b>Uom Type</b>");
			$("#uomTypeError").css("color", "red");
			$("#uomTypeError").show();
			uomTypeError = false;
		} else {
			$("#uomTypeError").hide();
			uomTypeError = true;
		}
		return uomTypeError;
	}
	//---------Mode--------//
	function validate_uomModel() {
		var val = $("#uomModel").val();
		var exp = /^[A-Z]{5,25}$/;
		if (val == '') {
			$("#uomModelError").html("<b>Uom Mode Required</b>");
			$("#uomModelError").css("color", "red");
			$("#uomModelError").show();
			uomModelError = false;
		} else if (!exp.test(val)) {
			$("#uomModelError").html("<b>Uom Mode in only [5-25]chars</b>");
			$("#uomModelError").css("color", "red");
			$("#uomModelError").show();
			uomModelError = false;
		} else {
			$("#uomModelError").hide();
			uomModelError = true;
		}
		return uomModelError;
	}

	//-----Description--------//
	function validate_description() {
		var val = $("#description").val();

		if (val.length < 5 || val.length > 150) {
			$("#descriptionError").html("<b>Description Required [5-150] chars only</b>");
			$("#descriptionError").css("color", "red");
			$("#descriptionError").show();
			descriptionError = false;
		} else {
			$("#descriptionError").hide();
			descriptionError = true;
		}
		return descriptionError;
	}

	//---------on submit----------//
	$("#uomForm").submit(function () {
		validate_uomType();
		validate_uomModel();
		validate_description();
		if (uomTypeError && uomModelError && descriptionError)
			return true;
		else
			return false;
	})
});