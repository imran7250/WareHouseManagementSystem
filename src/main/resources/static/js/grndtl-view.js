// Add status coloring without changing Thymeleaf structure
      document.addEventListener('DOMContentLoaded', function() {
          const statusElements = document.querySelectorAll('.action-cell span');
          
          statusElements.forEach(element => {
              const status = element.textContent.trim();
              if (status === 'ACCEPTED') {
                  element.classList.add('status-badge', 'status-accepted');
              } else if (status === 'REJECTED') {
                  element.classList.add('status-badge', 'status-rejected');
              }
          });
      });