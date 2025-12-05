// Wait for DOM to be fully loaded before attaching event listener
   document.addEventListener('DOMContentLoaded', function() {
       // Simple form validation
       document.querySelector('form').addEventListener('submit', function(e) {
           let isValid = true;
           const shipmentMode = document.getElementById('shipmentMode');
           
           if (!shipmentMode.value) {
               isValid = false;
               shipmentMode.style.borderColor = '#e74c3c';
           } else {
               shipmentMode.style.borderColor = '#ddd';
           }
           
           if (!isValid) {
               e.preventDefault();
               alert('Please select a shipment mode.');
           }
       });
   });