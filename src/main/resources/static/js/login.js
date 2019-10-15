function checkForm() {
    var form = document.querySelector('#form')
    var inputFields = document.querySelectorAll('.form-control')
    var loginButton = document.querySelector('#loginButton')
    form.addEventListener('keyup', function(e) {
        var disabled = false
        inputFields.forEach(function(input, index) {
            if (input.value === '' || !input.value.replace(/\s/g, '').length) {
                disabled = true
            }
        })
        if (disabled) {
            loginButton.setAttribute('disabled', 'disabled')
        } else {
            loginButton.removeAttribute('disabled')
        }
    })
}



