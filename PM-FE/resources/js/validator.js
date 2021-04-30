
function Validator(options) {
    function validate(inputElement, errorElement, rule) {
        var errorMessage;
        // get all rules of the selector
        var rules = selectorRules[rule.selector];
        // check every single rule, find the first error
        for (var i = 0; i < rules.length; i++) {
            errorMessage = rules[i](inputElement.value);
            if (errorMessage) {
                break;
            }
        }

        if (errorMessage) {
            errorElement.innerText = errorMessage;
            inputElement.classList.add('invalid');
            inputElement.parentElement.querySelector('.fas').classList.add('error-display');
        } else {
            errorElement.innerText = '';
            inputElement.classList.remove('invalid');
            inputElement.parentElement.querySelector('.fas').classList.remove('error-display');
        }
        return !errorMessage; // return false if the input has error
    }

    var formElement = document.querySelector(options.form); // choose which form to validate
    var selectorRules = {};
    
    if (formElement) {
        // when submitting the form, check all rules of the input
        formElement.onsubmit = function (e) {
            e.preventDefault();
            var isFormValid = true;

            options.rules.forEach(function (rule) {
                var inputElement = formElement.querySelector(rule.selector); // find the input element
                var errorElement = inputElement.parentElement.querySelector(options.errorSelector);
                var isValid = validate(inputElement, errorElement, rule);
                if (!isValid) {
                    isFormValid = false;
                }
            });
            // if the form is valid, submit it
            if (isFormValid) {
                //Submit with Javascript
                if(typeof options.onSubmit === 'function') {
                    var enabledInputs = formElement.querySelectorAll('[name]');
                    console.log(enabledInputs);
                    var formValues = Array.from(enabledInputs).reduce(function(values, input) {
                        return (values[input.name] = input.value) && values;
                    }, {});
                    options.onSubmit(formValues);
                } else {
                // Submit with default action
                    formElement.submit();
                }
            }
        }



        // check each rule
        options.rules.forEach(function (rule) {
            // Save all rules for the input
            if (Array.isArray(selectorRules[rule.selector])) {
                selectorRules[rule.selector].push(rule.test);
            } else {
                selectorRules[rule.selector] = [rule.test];
            }

            var inputElement = formElement.querySelector(rule.selector); // find the input element
            var errorElement = inputElement.parentElement.querySelector(options.errorSelector);

            if (inputElement) {
                // when blur out of the input
                inputElement.onblur = function () {
                    validate(inputElement, errorElement, rule);
                }
                // when filling in the input
                inputElement.oninput = function () {
                    errorElement.innerText = '';
                    inputElement.classList.remove('invalid');
                }
            }
        });
    }
}

Validator.isRequired = function (selector, message) {
    return {
        selector: selector,
        // function test is used to check whether a value is null
        test: function (value) {
            return value.trim() ? undefined : message || 'Please fill in this field!';
        }
    }
}

Validator.isEmail = function (selector) {
    return {
        selector: selector,
        test: function (value) {
            const regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return regex.test(value.toLowerCase()) ? undefined : 'Please enter a valid email!'
        }
    }
}

Validator.isPassword = function (selector, minLength) {
    return {
        selector: selector,
        // function test is used to check whether a value is null
        test: function (value) {
            return value.length >= minLength ? undefined : `Password must have at least ${minLength} characters!`;
        }
    }
}

Validator.isConfirmed = function (selector, getPassword) {
    return {
        selector: selector,
        // function test is used to check whether confirmed password is the same as password
        test: function (value) {
            return value === getPassword() ? undefined : 'Those passwords didn’t match. Try again!';
        }
    }
}