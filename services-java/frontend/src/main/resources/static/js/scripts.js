$(document).ready(function() {
    let isSubmitting = false; // Флаг для предотвращения повторной отправки

    $('#login-button').click(function() {
        $('#form-buttons').hide();
        $('#dynamic-form').empty().show();
        createLoginForm();
    });

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

        $('#dynamic-form').off('submit').on('submit', function(e) {
            e.preventDefault();
            if (isSubmitting) return; // Предотвращаем повторную отправку
            isSubmitting = true;
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

        $('#dynamic-form').off('submit').on('submit', function(e) {
            e.preventDefault();
            if (isSubmitting) return; // Предотвращаем повторную отправку
            isSubmitting = true;
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
                console.log('Response from server:', response);
                if (response.token) {
                    localStorage.setItem('authToken', response.token);
                    console.log('Token saved to localStorage:', response.token);
                }
                if (response.username) {
                    localStorage.setItem('username', response.username);
                    console.log('Username saved to localStorage:', response.username);
                }
                fetchNewsAndRedirect();
            },
            error: function(error) {
                console.error('Registration error:', error);
                alert('Ошибка регистрации. Пожалуйста, попробуйте еще раз.');
                isSubmitting = false; // Разрешить повторные попытки
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
                console.log('Response from server:', response);
                if (response.token) {
                    localStorage.setItem('authToken', response.token);
                    console.log('Token saved to localStorage:', response.token);
                }
                if (response.username) {
                    localStorage.setItem('username', response.username);
                    console.log('Username saved to localStorage:', response.username);
                }
                fetchNewsAndRedirect();
            },
            error: function(error) {
                console.error('Authentication error:', error);
                alert('Ошибка аутентификации. Пожалуйста, проверьте свои данные.');
                isSubmitting = false; // Разрешить повторные попытки
            }
        });
    }


    function fetchNewsAndRedirect() {
        $.ajax({
            url: 'http://localhost:8015/news',
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('authToken')}`
            },
            success: function(news) {
                console.log('News fetched:', news);
                window.location.href = "/success";
            },
            error: function(error) {
                console.error('Error fetching news:', error);
                alert('Ошибка получения новостей.');
                isSubmitting = false; // Разрешить повторные попытки
            }
        });
    }
});
