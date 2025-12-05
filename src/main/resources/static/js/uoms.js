$(document).ready(function(){
    // Store objects with original and lowercase model
    let uomModels = [];

    $("#uomForm").on("submit", function(e){
        // Only prevent default if validation fails
        const uomModel = $("#uomModel").val().trim();
        const uomModelLower = uomModel.toLowerCase();
        const uomType = $("#uomType").val();
        const description = $("#description").val().trim();

        let isValid = true;

        // Reset all errors
        $(".ajax-error").text("");
        $(".error-border").removeClass("error-border");

        // UOM Type validation
        if(uomType === "") {
            $("#uomType").addClass("error-border");
            isValid = false;
        }

        // UOM Model validation
        if(uomModel === "") {
            $("#uomModelError").text("UOM Model is required");
            $("#uomModel").addClass("error-border");
            isValid = false;
        } else if(uomModel.length < 3) {
            $("#uomModelError").text("UOM Model must be at least 3 characters long");
            $("#uomModel").addClass("error-border");
            isValid = false;
        } else if(!/^[a-zA-Z0-9\s]+$/.test(uomModel)) {
            $("#uomModelError").text("UOM Model must be alphanumeric");
            $("#uomModel").addClass("error-border");
            isValid = false;
        }

        // Description validation
        if(description === "") {
            // Create error span if it doesn't exist
            if ($("#descriptionError").length === 0) {
                $("#description").after('<span id="descriptionError" class="ajax-error"></span>');
            }
            $("#descriptionError").text("Please write the description");
            $("#description").addClass("error-border");
            isValid = false;
        } else if(description.length < 5) {
            if ($("#descriptionError").length === 0) {
                $("#description").after('<span id="descriptionError" class="ajax-error"></span>');
            }
            $("#descriptionError").text("Description must be at least 5 characters long");
            $("#description").addClass("error-border");
            isValid = false;
        }

        // If validation fails, prevent form submission
        if (!isValid) {
            e.preventDefault();
            return;
        }

        // Check for duplicates using AJAX call to server
        $.ajax({
            url: "/uom/validate",
            data: { model: uomModel },
            success: function(response) {
                if (response) {
                    // Model already exists, prevent form submission
                    $("#uomModelError").text("UOM Model '" + uomModel + "' already exists. Please use a different model.");
                    $("#uomModel").addClass("error-border");
                    e.preventDefault();
                } else {
                    // Model is unique, allow form to submit normally
                    // Remove the event handler and submit the form
                    $("#uomForm").off('submit').submit();
                }
            },
            error: function() {
                // If validation endpoint fails, allow form to submit
                $("#uomForm").off('submit').submit();
            }
        });

        // Prevent form submission until AJAX validation completes
        e.preventDefault();
    });

    // On input change, check duplicates live with original casing message
    $("#uomModel").on("input", function(){
        const modelVal = $(this).val().trim();
        const modelValLower = modelVal.toLowerCase();

        if(modelVal === ""){
            $("#uomModelError").text("");
            $("#uomModel").removeClass("error-border");
            return;
        }

        if(modelVal.length < 3){
            $("#uomModelError").text("UOM Model must be at least 3 characters long");
            $("#uomModel").addClass("error-border");
            return;
        }

        if(!/^[a-zA-Z0-9\s]+$/.test(modelVal)){ 
            $("#uomModelError").text("UOM Model must be alphanumeric");
            $("#uomModel").addClass("error-border");
            return;
        }

        const duplicateEntry = uomModels.find(m => m.lower === modelValLower);

        if(duplicateEntry){
            $("#uomModelError").text("UOM Model " + duplicateEntry.original + " is already exists please add other UOM Model");
            $("#uomModel").addClass("error-border");
        } else {
            $("#uomModelError").text("");
            $("#uomModel").removeClass("error-border");
        }
    });

    // On input change for description
    $("#description").on("input", function(){
        const descVal = $(this).val().trim();
        
        if(descVal === ""){
            if ($("#descriptionError").length > 0) {
                $("#descriptionError").text("");
            }
            $("#description").removeClass("error-border");
            return;
        }
        
        if(descVal.length < 5){
            if ($("#descriptionError").length === 0) {
                $("#description").after('<span id="descriptionError" class="ajax-error"></span>');
            }
            $("#descriptionError").text("Description must be at least 5 characters long");
            $("#description").addClass("error-border");
        } else {
            if ($("#descriptionError").length > 0) {
                $("#descriptionError").text("");
            }
            $("#description").removeClass("error-border");
        }
    });

    // Display server message if it exists
    const serverMessage = $("#serverMessage").text();
    if (serverMessage && serverMessage.trim() !== "") {
        // Create a success message element
        const messageElement = $('<div class="message success">' + serverMessage + '</div>');
        $(".form-container").prepend(messageElement);
        
        // Remove the message after 5 seconds
        setTimeout(function() {
            messageElement.fadeOut(function() {
                $(this).remove();
            });
        }, 5000);
    }
});