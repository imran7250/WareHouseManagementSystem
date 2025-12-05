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
      
      function validateOrderCode() {
          const orderCode = document.getElementById('orderCode').value;
          const validationMsg = document.getElementById('orderCodeValidation');
          
          if (orderCode) {
              fetch('/OrderMethod/validate?orderCode=' + encodeURIComponent(orderCode))
                  .then(response => response.text())
                  .then(message => {
                      validationMsg.textContent = message;
                      if (message) {
                          validationMsg.className = 'validation-message';
                          validationMsg.innerHTML = `<i class="fas fa-exclamation-circle"></i> ${message}`;
                      } else {
                          validationMsg.className = 'validation-message valid';
                          validationMsg.innerHTML = `<i class="fas fa-check-circle"></i> Order code is available`;
                      }
                  })
                  .catch(error => {
                      console.error('Error:', error);
                  });  
          } else {
              validationMsg.textContent = '';
              validationMsg.className = 'validation-message';
          }
      }