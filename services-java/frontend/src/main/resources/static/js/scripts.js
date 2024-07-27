$(document).ready(function() {
    // Обработчик клика по кнопке "Войти"
    $('#login-button').click(function() {
        $('#form-buttons').hide();
        $('#dynamic-form').empty().show();
        createLoginForm();
    });

    // Обработчик клика по кнопке "Зарегистрироваться"
    $('#register-button').click(function() {
        $('#form-buttons').hide();
        $('#dynamic-form').empty().show();
        createRegisterForm();
    });

    function createLoginForm() {
        addBackButton();
        addField('Введите ник', 'text', 'username', function() {
            addField('Введите пароль', 'password', 'password', function() {
                addButton('Войти', 'submit');
            });
        });

        // Удаление предыдущих обработчиков submit перед добавлением нового
        $('#dynamic-form').off('submit').on('submit', function(e) {
            e.preventDefault();
            const username = $('input[name="username"]').val();
            const password = $('input[name="password"]').val();
            authenticateUser(username, password);
        });
    }

    function createRegisterForm() {
        addBackButton();
        addField('Введите имя', 'text', 'first_name', function() {
            addField('Введите фамилию', 'text', 'last_name', function() {
                addField('Введите ник', 'text', 'username', function() {
                    addField('Введите пароль', 'password', 'password', function() {
                        addButton('Зарегистрироваться', 'submit');
                    });
                });
            });
        });

        // Удаление предыдущих обработчиков submit перед добавлением нового
        $('#dynamic-form').off('submit').on('submit', function(e) {
            e.preventDefault();
            const firstName = $('input[name="first_name"]').val();
            const lastName = $('input[name="last_name"]').val();
            const username = $('input[name="username"]').val();
            const password = $('input[name="password"]').val();
            registerUser(firstName, lastName, username, password);
        });
    }

    function addField(placeholder, type, name, callback) {
        let field = $(`<div class="form-group"><input type="${type}" class="form-control mt-2" name="${name}" placeholder="${placeholder}" required></div>`);
        $('#dynamic-form').append(field);
        setTimeout(callback, 500);
    }

    function addButton(text, type) {
        let button = $(`<button type="${type}" class="btn btn-primary btn-block mt-2">${text}</button>`);
        $('#dynamic-form').append(button);
    }

    function addBackButton() {
        let backButton = $('<button type="button" id="back-button" class="btn btn-secondary btn-block">Назад</button>');
        $('#dynamic-form').append(backButton);
        backButton.show();
        backButton.click(function() {
            $('#dynamic-form').hide();
            $('#form-buttons').show();
        });
    }

    function registerUser(firstName, lastName, username, password) {
        $.ajax({
            url: 'http://localhost:8005/register',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ firstname: firstName, lastname: lastName, username: username, password: password }),
            success: function(response) {
                console.log('Registration successful:', response);
                // Сохраните токен в localStorage
                if (response.token) {
                    localStorage.setItem('authToken', response.token);
                }
                // Отправляем запрос для успешного перехода
                redirectToSuccessPage();
            },
            error: function(error) {
                console.error('Registration error:', error);
                // Добавьте уведомление об ошибке
                alert('Ошибка регистрации. Пожалуйста, попробуйте еще раз.');
            }
        });
    }

    function authenticateUser(username, password) {
        $.ajax({
            url: 'http://localhost:8005/authenticate',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ username: username, password: password }),
            success: function(response) {
                console.log('Authentication successful:', response);
                // Сохраните токен в localStorage
                if (response.token) {
                    localStorage.setItem('authToken', response.token);
                }
                // Отправляем запрос для успешного перехода
                redirectToSuccessPage();
            },
            error: function(error) {
                console.error('Authentication error:', error);
                // Добавьте уведомление об ошибке
                alert('Ошибка аутентификации. Пожалуйста, проверьте свои данные.');
            }
        });
    }

    function redirectToSuccessPage() {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/success", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                window.location.href = "/success";
            }
        };
        xhr.send();
    }

});
