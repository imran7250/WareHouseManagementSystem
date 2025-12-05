$(document).ready(function(){
    $("#shipmentCode").on("blur", function(){
        var codeVal = $(this).val().trim();
        if(codeVal !== ""){
            $.ajax({
                url: "/st/validate",  // Remove the Thymeleaf comment syntax
                data: { code: codeVal },
                success: function(response){
                    if(response !== " "){
                        $("#shipmentCodeAjaxError").text(response);
                        $("#shipmentCode").addClass("error-field");
                    } else {
                        $("#shipmentCodeAjaxError").text("");
                        $("#shipmentCode").removeClass("error-field");
                    }
                },
                error: function() {
                    $("#shipmentCodeAjaxError").text("Error validating shipment code");
                }
            });
        } else {
            $("#shipmentCodeAjaxError").text("");
            $("#shipmentCode").removeClass("error-field");
        }
    });
    
    // Prevent form submission if there's a validation error
    $("#shipmentForm").on("submit", function(e) {
        if ($("#shipmentCodeAjaxError").text() !== "") {
            e.preventDefault();
            $("#shipmentCode").focus();
        }
    });
});