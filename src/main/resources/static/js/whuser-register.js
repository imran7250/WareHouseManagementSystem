$(document).ready(function(){
    // Automatically set userFor based on userType selection
    $('input[name="userType"]').change(function() {
        const userForValue = $(this).val();
        let userForText = '';
        
        if (userForValue === 'Vendor') {
            userForText = 'Purchase';
        } else if (userForValue === 'Customer') {
            userForText = 'Sale';
        }
        
        $('#userFor').val(userForText);
    });
    
    // Show/hide other ID type field
    $('select[name="userIdType"]').change(function() {
        const selectedValue = $(this).val();
        const ifOtherInput = $('#ifOther');
        
        if (selectedValue === 'OTHER') {
            ifOtherInput.prop('readonly', false);
            ifOtherInput.removeClass('readonly-input');
            ifOtherInput.attr('placeholder', 'Specify ID type');
            ifOtherInput.attr('required', true);
        } else {
            ifOtherInput.prop('readonly', true);
            ifOtherInput.addClass('readonly-input');
            ifOtherInput.val('');
            ifOtherInput.removeAttr('required');
        }
    });
    
    // Enhanced Validation for Email with AJAX duplicate check
    $('#userEmail').on('blur', function() {
        const value = $(this).val().trim();
        const emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
        
        if (!value) {
            return;
        }
        
        if (!emailRegex.test(value)) {
            return;
        }
        
        // AJAX call to check if email already exists
        $.ajax({
            url: '/whUser/checkEmail',
            type: 'GET',
            data: { email: value },
            success: function(response) {
                if (response.exists) {
                    $('#userEmailError').text('This email is already registered. Please use a different email.');
                    $('#userEmail').addClass('input-error');
                } else {
                    $('#userEmailError').text('');
                    $('#userEmail').removeClass('input-error');
                }
            },
            error: function() {
                console.log('Error checking email availability');
            }
        });
    });
});