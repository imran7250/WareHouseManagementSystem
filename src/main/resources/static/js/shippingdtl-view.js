// Add status coloring without changing Thymeleaf structure
document.addEventListener('DOMContentLoaded', function() {
    const statusElements = document.querySelectorAll('.action-cell span');
    
    statusElements.forEach(element => {
        const status = element.textContent.trim();
        if (status === 'RECEIVED') {
            element.classList.add('status-badge', 'status-accepted');
        } else if (status === 'RETURNED') {
            element.classList.add('status-badge', 'status-rejected');
        }
    });
});