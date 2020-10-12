 $(document).ready(function () {
            $("#orderModeError").hide();
            $("#orderCodeError").hide();
            $("#orderTypeError").hide();
            $("#orderAcptError").hide();
            $("#descriptionError").hide();

            var orderModeError = false;
            var orderCodeError = false;
            var orderTypeError = false;
            var orderAcptError = false;
            var descriptionError = false;

            $('input[type="radio"][name="orderMode"]').change(function () {
                validate_orderMode();
            });
            $("#orderCode").keyup(function () {
                $(this).val($(this).val().toUpperCase());
                validate_orderCode();
            });
            $("#orderType").change(function () {
                validate_orderType();
            });

            $('input[type="checkbox"][name="orderAcpt"]').change(function () {
                validate_orderAcpt();
            });
            $("#description").keyup(function () {
                validate_description();
            });

            //---------//
            function validate_orderMode() {
                var len = $('input[type="radio"][name="orderMode"]:checked').length;
                if (len == 0) {
                    $("#orderModeError").html("Choose <b>Order Mode</b>");
                    $("#orderModeError").css("color", "red");
                    $("#orderModeError").show();
                    orderModeError = false;
                } else {
                    $("#orderModeError").hide();
                    orderModeError = true;
                }
                return orderModeError;
            }

            function validate_orderCode() {
                var val = $("#orderCode").val();
                var exp = /^[A-Z]{4,25}$/;
                if (val == '') {
                    $("#orderCodeError").html("Enter <b>Order Code</b>");
                    $("#orderCodeError").css("color", "red");
                    $("#orderCodeError").show();
                    orderCodeError = false;
                } else if (!exp.test(val)) {
                    $("#orderCodeError").html("Must be <b>4-25 Chars only</b>");
                    $("#orderCodeError").css("color", "red");
                    $("#orderCodeError").show();
                    orderCodeError = false;
                } else {
                	var loc='validatecode';
                	var id=0;
                   	if($("#id").val()!== undefined){ 
                		loc='../validatecode';
                		id=$("#id").val();
                	}
					            		
                	//AJAX-START
                	$.ajax({
                		url : loc,
                		data: {'code':val,'id':id},
                		success:function(resTxt){
                			if(resTxt!=''){
                				 $("#orderCodeError").html(resTxt);
                                 $("#orderCodeError").css("color", "red");
                                 $("#orderCodeError").show();
                                 orderCodeError = false;
                			}else{
                				$("#orderCodeError").hide();
                				orderCodeError = true;
                			}
                		}
                	});
                	//AJAX-END
                }
                return orderCodeError;
            }

            function validate_orderType() {
                var val = $("#orderType").val();
                if (val == '') {
                    $("#orderTypeError").html("Choose <b>Order Type</b>");
                    $("#orderTypeError").css("color", "red");
                    $("#orderTypeError").show();
                    orderTypeError = false;
                } else {
                    $("#orderTypeError").hide();
                    orderTypeError = true;
                }
                return orderTypeError;
            }

            function validate_orderAcpt() {
                var len = $('input[type="checkbox"][name="orderAcpt"]:checked').length;
                if (len == 0) {
                    $("#orderAcptError").html("Choose any one <b>Order Acpt</b>");
                    $("#orderAcptError").css("color", "red");
                    $("#orderAcptError").show();
                    orderAcptError = false;
                } else {
                    $("#orderAcptError").hide();
                    orderAcptError = true;
                }
                return orderAcptError;
            }

            function validate_description() {
                var val = $("#description").val();
                if (val.length < 5 || val.length > 150) {
                    $("#descriptionError").show();
                    $("#descriptionError").html("Must be <b>5-150 Chars only</b>");
                    $("#descriptionError").css("color", "red");
                    descriptionError = false;
                } else {
                    $("#descriptionError").hide();
                    descriptionError = true;
                }
                return descriptionError;
            }

            //----on submit----------//
            $("#orderMethodForm").submit(function () {
                validate_orderMode();
                validate_orderCode();
                validate_orderType();
                validate_orderAcpt();
                validate_description();
                if (orderModeError && orderCodeError && orderTypeError
                    && orderAcptError && descriptionError)
                    return true;
                else
                    return false;
            });


        });