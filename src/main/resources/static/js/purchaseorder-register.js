document.addEventListener('DOMContentLoaded', function() {
           // Add focus effects to inputs
           const inputs = document.querySelectorAll('input, textarea, select');
           inputs.forEach(input => {
               input.addEventListener('focus', function() {
                   this.parentElement.classList.add('focused');
               });
               
               input.addEventListener('blur', function() {
                   this.parentElement.classList.remove('focused');
               });
           });
           
           // Auto-hide message after 5 seconds
           const message = document.querySelector('.alert');
           if (message) {
               setTimeout(function() {
                   message.style.display = 'none';
               }, 5000);
           }
           
           // Form validation
           const form = document.getElementById('poForm');
           if (form) {
               form.addEventListener('submit', function(e) {
                   let isValid = true;
                   
                   // Validate Order Code
                   const orderCode = document.getElementById('orderCode');
                   const orderCodeError = document.getElementById('orderCodeError');
                   if (!orderCode.value.trim()) {
                       orderCodeError.textContent = 'Order code is required';
                       orderCode.classList.add('input-error');
                       isValid = false;
                   } else {
                       orderCodeError.textContent = '';
                       orderCode.classList.remove('input-error');
                   }
                   
                   // Validate Shipment Type
                   const shipmentType = document.getElementById('shipmentType');
                   const shipmentTypeError = document.getElementById('shipmentTypeError');
                   if (!shipmentType.value) {
                       shipmentTypeError.textContent = 'Shipment type is required';
                       shipmentType.classList.add('input-error');
                       isValid = false;
                   } else {
                       shipmentTypeError.textContent = '';
                       shipmentType.classList.remove('input-error');
                   }
                   
                   // Validate Reference Number
                   const refNumber = document.getElementById('refNumber');
                   const refNumberError = document.getElementById('refNumberError');
                   if (!refNumber.value.trim()) {
                       refNumberError.textContent = 'Reference number is required';
                       refNumber.classList.add('input-error');
                       isValid = false;
                   } else {
                       refNumberError.textContent = '';
                       refNumber.classList.remove('input-error');
                   }
                   
                   if (!isValid) {
                       e.preventDefault();
                   }
               });
           }
       });     