// Add some interactive elements
document.addEventListener('DOMContentLoaded', function() {
    const formControls = document.querySelectorAll('.form-control');
    
    formControls.forEach(control => {
        // Add focus effect
        control.addEventListener('focus', function() {
            this.parentElement.classList.add('focused');
        });
        
        // Remove focus effect
        control.addEventListener('blur', function() {
            this.parentElement.classList.remove('focused');
        });
    });
    
    // Add animation to submit button
    const submitBtn = document.querySelector('.btn-success');
    submitBtn.addEventListener('mouseenter', function() {
        this.style.transform = 'translateY(-2px)';
    });
    
    submitBtn.addEventListener('mouseleave', function() {
        this.style.transform = 'translateY(0)';
    });
});