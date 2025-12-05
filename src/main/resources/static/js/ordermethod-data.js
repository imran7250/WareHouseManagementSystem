document.addEventListener('DOMContentLoaded', function() {
    // Add animation to table rows
    const tableRows = document.querySelectorAll('tbody tr');
    tableRows.forEach((row, index) => {
        row.style.animation = `fadeIn 0.5s ease-out ${index * 0.05}s forwards`;
        row.style.opacity = '0';
    });
    
    // Add styles for the animation
    const style = document.createElement('style');
    style.textContent = `
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    `;
    document.head.appendChild(style);
});