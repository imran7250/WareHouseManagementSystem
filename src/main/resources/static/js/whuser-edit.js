$(document).ready(function(){
    // Automatically set userFor based on userType selection
    $('input[name="userType"]').change(function() {
        const userForValue = $(this).val();
        $('input[name="userFor"]').val(userForValue);
    });
    
    // Show/hide other ID type field
    $('select[name="userIdType"]').change(function() {
        const selectedValue = $(this).val();
        const ifOtherInput = $('input[name="ifOther"]');
         
        if (selectedValue === 'OTHER') {
            ifOtherInput.prop('readonly', false);
            ifOtherInput.removeClass('readonly-input');
            ifOtherInput.attr('placeholder', 'Specify ID type');
        } else {
            ifOtherInput.prop('readonly', true);
            ifOtherInput.addClass('readonly-input');
            ifOtherInput.val('');
        }
    });
    
    // Initialize form based on current values
    const userTypeVal = $('input[name="userType"]:checked').val();
    if (userTypeVal) {
        $('input[name="userFor"]').val(userTypeVal);
    }
    
    const idTypeVal = $('select[name="userIdType"]').val();
    if (idTypeVal === 'OTHER') {
        $('input[name="ifOther"]').prop('readonly', false);
        $('input[name="ifOther"]').removeClass('readonly-input');
    }
});