// Confirm delete action
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('delete-btn') || e.target.closest('.delete-btn')) {
            if (!confirm('Are you sure you want to delete this shipment type?')) {
                e.preventDefault();
            }
        }
    });
    
    // Auto-hide messages after 5 seconds
    setTimeout(function() {
        const messages = document.querySelectorAll('.message');
        messages.forEach(function(message) {
            message.style.opacity = '0';
            message.style.transition = 'opacity 0.5s ease';
            setTimeout(function() {
                message.style.display = 'none';
            }, 500);
        });
    }, 5000);