document.addEventListener('DOMContentLoaded', function() {
          // Add animation to form elements
          const formElements = document.querySelectorAll('.form-group');
          formElements.forEach((element, index) => {
              element.style.animation = `fadeIn 0.5s ease-out ${index * 0.1}s forwards`;
              element.style.opacity = '0';
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