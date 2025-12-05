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
        const form = document.getElementById('soForm');
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

                // Validate Customer
                const customer = document.getElementById('customer');
                const customerError = document.getElementById('customerError');
                if (!customer.value) {
                    customerError.textContent = 'Customer selection is required';
                    customer.classList.add('input-error');
                    isValid = false;
                } else {
                    customerError.textContent = '';
                    customer.classList.remove('input-error');
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

                // Validate Stock Mode
                const stockModeInputs = document.querySelectorAll('input[name="stockMode"]');
                const stockModeError = document.getElementById('stockModeError');
                let stockModeSelected = false;
                stockModeInputs.forEach(input => {
                    if (input.checked) stockModeSelected = true;
                });
                if (!stockModeSelected) {
                    stockModeError.textContent = 'Stock mode is required';
                    isValid = false;
                } else {
                    stockModeError.textContent = '';
                }

                // Validate Stock Source
                const stockSource = document.getElementById('stockSource');
                const stockSourceError = document.getElementById('stockSourceError');
                if (!stockSource.value) {
                    stockSourceError.textContent = 'Stock source is required';
                    stockSource.classList.add('input-error');
                    isValid = false;
                } else {
                    stockSourceError.textContent = '';
                    stockSource.classList.remove('input-error');
                }

                if (!isValid) {
                    e.preventDefault();
                }
            });
        }
    });