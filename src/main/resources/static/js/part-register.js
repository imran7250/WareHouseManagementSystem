$(document).ready(function(){
    // Basic validation for part code
    $("#partCode").on("blur", function(){
        var partCodeVal = $(this).val().trim();
        if(partCodeVal === ""){
            $("#partCodeError").text("Part code is required");
        } else {
            $("#partCodeError").text("");
        }
    });
    
    // Basic validation for part cost
    $("#partCost").on("blur", function(){
        var partCostVal = $(this).val().trim();
        if(partCostVal === ""){
            $("#partCostError").text("Base cost is required");
        } else if(isNaN(partCostVal) || parseFloat(partCostVal) <= 0){
            $("#partCostError").text("Please enter a valid cost");
        } else {
            $("#partCostError").text("");
        }
    });
    
    // Basic validation for currency
    $("#partCurrency").on("change", function(){
        var currencyVal = $(this).val();
        if(currencyVal === ""){
            $("#partCurrencyError").text("Please select a currency");
        } else {
            $("#partCurrencyError").text("");
        }
    });
    
    // Prevent form submission if there are validation errors
    $("#partForm").on("submit", function(e) {
        var hasError = false;
        
        // Check part code
        if($("#partCode").val().trim() === ""){
            $("#partCodeError").text("Part code is required");
            hasError = true;
        }
        
        // Check part cost
        var partCostVal = $("#partCost").val().trim();
        if(partCostVal === ""){
            $("#partCostError").text("Base cost is required");
            hasError = true;
        } else if(isNaN(partCostVal) || parseFloat(partCostVal) <= 0){
            $("#partCostError").text("Please enter a valid cost");
            hasError = true;
        }
        
        // Check currency
        if($("#partCurrency").val() === ""){
            $("#partCurrencyError").text("Please select a currency");
            hasError = true;
        }
        
        if(hasError){
            e.preventDefault();
            // Focus on first error field
            if($("#partCodeError").text() !== ""){
                $("#partCode").focus();
            } else if($("#partCostError").text() !== ""){
                $("#partCost").focus();
            } else {
                $("#partCurrency").focus();
            }
        }
    });
});