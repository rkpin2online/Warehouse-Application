$(document).ready(function () {
	//1.Hide span tag 
	$("#partCodeError").hide();
	$("#partWidthError").hide();
	$("#partLenError").hide();
	$("#partHghError").hide();
	$("#baseCostError").hide();
	$("#baseCurrError").hide();
	$("#descriptionError").hide();
	$("#uomError").hide();


	//2.define flag
	var partCodeError = false;
	var partWidthError = false;
	var partLenError = false;
	var partHghError = false;
	var baseCostError = false;
	var baseCurrError = false;
	var descriptionError = false;
	var uomError = false;

	$("#partCode").keyup(function () {
		$("#partCode").val($("#partCode").val().toUpperCase());
		validate_partCode();
	});

	$("#partWidth").keyup(function () {
		validate_partWidth();
	});
	$("#partLen").keyup(function () {
		validate_partLen();
	});

	$("#partHgh").keyup(function () {
		validate_partHgh();
	});

	$("#baseCost").keyup(function () {
		validate_baseCost();
	});

	$("#baseCurr").change(function () {
		validate_baseCurr();
	});
	
	$("#uom").change(function () {
		validate_uom();
	});


	$("#description").keyup(function () {
		validate_description();
	});
	//-----------function-------///
	function validate_partCode() {
		var val = $("#partCode").val();
		var exp = /^[a-zA-Z]{5,15}$/;
		if (val == '') {
			$("#partCodeError").html("<b>Part Code is Required</b>");
			$("#partCodeError").css("color", "red");
			$("#partCodeError").show();
			partCodeError = false;
		} else if (!exp.test(val)) {
			$("#partCodeError").html("<b>Alphanumeric only [5-15] </b>");
			$("#partCodeError").css("color", "red");
			$("#partCodeError").show();
			partCodeError = false;
		} else {
		 	//register 
			var loc = 'validatecode';
			var id = 0 ;
			
			if($("#id").val()!==undefined){ 
			    //edit
				loc = '../validatecode';
				id  = $("#id").val(); 
			}
			//AJAX START
			$.ajax({
				url : loc,
				data:{"code":val,"id":id},
				success:function(resTxt){
					if(resTxt!=''){
						$("#partCodeError").show();
						$("#partCodeError").html(resTxt);
						$("#partCodeError").css("color", "red");
						partCodeError = false;
					}else{
						$("#partCodeError").hide();
						partCodeError = true;
					}
				}
			});
			//AJAX END
		}
		return partCodeError;
	}

	function validate_partWidth() {
		var val = $("#partWidth").val();
		var exp = /^[1-9][0-9]*$/;
		if (val == '') {
			$("#partWidthError").html("<b>Width is Required</b>");
			$("#partWidthError").css("color", "red");
			$("#partWidthError").show();
			partWidthError = false;
		} else if (!exp.test(val)) {
			$("#partWidthError").html("<b>Only Numeric Value </b>");
			$("#partWidthError").css("color", "red");
			$("#partWidthError").show();
			partWidthError = false;
		} else {
			$("#partWidthError").hide();
			partWidthError = true;
		}
		return partWidthError;
	}



	function validate_partLen() {
		var val = $("#partLen").val();
		var exp = /^[1-9][0-9]*$/;
		if (val == '') {
			$("#partLenError").html("<b>Length is Required</b>");
			$("#partLenError").css("color", "red");
			$("#partLenError").show();
			partLenError = false;
		} else if (!exp.test(val)) {
			$("#partLenError").html("<b>Only Numeric Value </b>");
			$("#partLenError").css("color", "red");
			$("#partLenError").show();
			partLenError = false;
		} else {
			$("#partLenError").hide();
			partLenError = true;
		}
		return partLenError;
	}

	function validate_partHgh() {
		var val = $("#partHgh").val();
		var exp = /^[1-9][0-9]*$/;
		if (val == '') {
			$("#partHghError").html("<b>Height is Required</b>");
			$("#partHghError").css("color", "red");
			$("#partHghError").show();
			partHghError = false;
		} else if (!exp.test(val)) {
			$("#partHghError").html("<b>Only Numeric Value </b>");
			$("#partHghError").css("color", "red");
			$("#partHghError").show();
			partHghError = false;
		} else {
			$("#partHghError").hide();
			partHghError = true;
		}
		return partHghError;
	}

	function validate_baseCost() {
		var val = $("#baseCost").val();
		var exp = /^[1-9][0-9.]*$/;
		if (val == '') {
			$("#baseCostError").html("<b>Cost is Required</b>");
			$("#baseCostError").css("color", "red");
			$("#baseCostError").show();
			baseCostError = false;
		} else if (!exp.test(val)) {
			$("#baseCostError").html("<b>Only Numeric Value </b>");
			$("#baseCostError").css("color", "red");
			$("#baseCostError").show();
			baseCostError = false;
		} else {
			$("#baseCostError").hide();
			baseCostError = true;
		}
		return baseCostError;
	}

	function validate_baseCurr() {
		var val = $("#baseCurr").val();
		if (val == '') {
			$("#baseCurrError").html("<b>Choose Currency Type</b>");
			$("#baseCurrError").css("color", "red");
			$("#baseCurrError").show();
			baseCurrError = false;
		} else {
			$("#baseCurrError").hide();
			baseCurrError = true;
		}
		return baseCurrError;
	}

	function validate_description() {
		var val = $("#description").val();

		if (val.length < 5 || val.length > 150) {
			$("#descriptionError").html("<b>Description Required</b>");
			$("#descriptionError").css("color", "red");
			$("#descriptionError").show();
			descriptionError = false;
		} else {
			$("#descriptionError").hide();
			descriptionError = true;
		}
		return descriptionError;
	}

	function validate_uom() {
		var val = $("#uom").val();
		if (val == '') {
			$("#uomError").html("<b>Choose One UOM</b>");
			$("#uomError").css("color", "red");
			$("#uomError").show();
			uomError = false;
		} else {
			$("#uomError").hide();
			uomError = true;
		}
		return uomError;
	}

	$("#partForm").submit(function () {
		validate_partCode();
		validate_partWidth();
		validate_partLen();
		validate_partHgh();
		validate_baseCost();
		validate_baseCurr();
		validate_description();
		validate_uom();
		if (partCodeError && partWidthError && partLenError
				&& partHghError && baseCostError && baseCurrError 
				&& descriptionError && uomError) {
			return true;
		} else {
			return false;
		}
	});
});