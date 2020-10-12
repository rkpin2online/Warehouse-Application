$(document).ready(function () {
	//1.hide error span            
	$("#orderCodeError").hide();
	$("#referenceNumberError").hide();
	$("#defaultStatusError").hide();
	$("#qualityCheckError").hide();
	$("#descriptionError").hide();

	//2.define flag variable           
	var orderCodeError = false;
	var referenceNumberError = false;
	var defaultStatusError = false;
	var qualityCheckError = false;
	var descriptionError = false;


	//link to event           


	$("#orderCode").keyup(function () {
		//5.call the function
		$("#orderCode").val($("#orderCode").val().toUpperCase());
		validate_orderCode();
	});
	$("#referenceNumber").keyup(function () {
		//5.call the function

		validate_referenceNumber();
	});
	$("#status").keyup(function () {
		//5.call the function

		validate_defaultStatus();
	});


	$('input[type="radio"][name="qualityCheck"]').change(function () {
		validate_qualityCheck();
	});

	$("#description").keyup(function () {
		//5.call the function
		validate_description();
	});
	//4.define the function        

	//-----Ref Number-------//
	function validate_referenceNumber() {
		var val = $("#referenceNumber").val();
		if (val == '') {
			$("#referenceNumberError").html("<b>Reference Number Required</b>");
			$("#referenceNumberError").css("color", "red");
			$("#referenceNumberError").show();
			referenceNumberError = false;
		} else {
			$("#referenceNumberError").hide();
			referenceNumberError = true;
		}
		return referenceNumberError;
	}
	//-----Status--------//
	function validate_defaultStatus() {
		var val = $("#status").val();
		if (val == '') {
			$("#defaultStatusError").html("<b>Default StatusE Required</b>");
			$("#defaultStatusError").css("color", "red");
			$("#defaultStatusError").show();
			defaultStatusError = false;
		} else {
			$("#defaultStatusError").hide();
			defaultStatusError = true;
		}
		return defaultStatusError;
	}
	//-----Code--------//
	function validate_orderCode() {
		var val = $("#orderCode").val();
		var exp = /^[A-Z.]{5,25}$/;
		if (val == '') {
			$("#orderCodeError").html("<b>Purchase Order Code Required</b>");
			$("#orderCodeError").css("color", "red");
			$("#orderCodeError").show();
			orderCodeError = false;
		} else if (!exp.test(val)) {
			$("#orderCodeError").html("<b>Purchase Order Code UpperCase only[5-25]chars</b>");
			$("#orderCodeError").css("color", "red");
			$("#orderCodeError").show();
			orderCodeError = false;
		} else {
			//AJAX START
			$.ajax({
				url : 'validatecode',
				data: {"code":val},
				success:function(resTxt) {
					if(resTxt!=''){
						$("#orderCodeError").show();
						$("#orderCodeError").html(resTxt);
						$("#orderCodeError").css("color", "red");
						orderCodeError = false;
					}else{
						$("#orderCodeError").hide();
						orderCodeError = true;
					}
				}

			});              	

			//AJAX END
		}//else end
		return orderCodeError;
	}



	//-----QTY--------//
	function validate_qualityCheck() {
		var len = $('input[type="radio"][name="qualityCheck"]:checked').length;
		if (len == 0) {
			$("#qualityCheckError").html("<b>Quality Check Required</b>");
			$("#qualityCheckError").css("color", "red");
			$("#qualityCheckError").show();
			qualityCheckError = false;
		} else {
			$("#qualityCheckError").hide();
			qualityCheckError = true;
		}
		return qualityCheckError;
	}
	//-----Description--------//
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

	//-----------form submission-------------/
	$("#purchaseOrderForm").submit(function () {
		validate_orderCode();
		validate_referenceNumber();
		validate_defaultStatus();
		validate_qualityCheck();
		validate_description();
		if (orderCodeError && qualityCheckError && referenceNumberError && 
				defaultStatusError && descriptionError) {
			return true;
		} else {
			return false;
		}
	});
});