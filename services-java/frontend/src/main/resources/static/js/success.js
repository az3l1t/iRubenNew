$(document).ready(function() {
    console.log('Document is ready');

    // Функция для получения опыта пользователя
    function fetchUserExperience(callback) {
        const username = localStorage.getItem('username');
        $.ajax({
            url: `http://localhost:8005/${username}`,
            method: 'GET',
            success: function(response) {
                const experience = response.exp;
                if (callback) callback(experience); // Вызов коллбека с опытом
            },
            error: function() {
                console.error('Ошибка получения опыта пользователя.');
                alert('Ошибка получения вашего опыта.');
            }
        });
    }

    // Отправляем запрос для получения новостей
    $.ajax({
        url: 'http://localhost:8000/news',
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('authToken')}`
        },
        success: function(news) {
            console.log('News fetched:', news);
            // Отображаем новости на странице
            displayNews(news);
        },
        error: function(error) {
            console.error('Error fetching news:', error);
            alert('Ошибка получения новостей.');
        }
    });

    function displayNews(news) {
        console.log('Displaying news:', news);
        const newsContainer = $('#news-list');
        news.forEach(item => {
            const newsElement = $(`
                <div class="news-item">
                    <h3 class="news-title">${item.title}</h3>
                    <p class="news-info">${item.information}</p>
                </div>
            `);
            newsContainer.append(newsElement);
        });
    }

    // Обработчик кнопки "Выйти"
    document.getElementById('logout').addEventListener('click', function() {
        console.log('Logout button clicked');
        localStorage.removeItem('authToken');
        localStorage.removeItem('username');
        // Перенаправление на страницу входа или домашнюю страницу
        window.location.href = 'index.html'; // Замените на нужный URL
    });

    // Обработчики для кнопок "Слова"
    const wordButtons = document.querySelectorAll('#words-btn, .nav-item[href="#words"]');
    console.log('Word buttons found:', wordButtons);

    wordButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // Предотвращаем стандартное действие ссылки
            console.log('Words button clicked');

            const token = localStorage.getItem('authToken'); // Получаем токен из localStorage
            if (token) {
                $.ajax({
                    url: '/words',
                    type: 'GET',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + token); // Добавляем токен в заголовок запроса
                    },
                    success: function() {
                        console.log('Successfully authenticated for words page');
                        // Перенаправляем на страницу слов
                        window.location.href = 'words'; // Относительный путь
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error('Error during words request:', textStatus, errorThrown);
                        alert('Ошибка при проверке аутентификации для страницы слов.');
                    }
                });
            } else {
                console.warn('Token аутентификации не найден.');
                alert('Токен аутентификации не найден. Пожалуйста, войдите в систему.');
            }
        });
    });

    // Обработчики для кнопок "Угадайка"
    const gameButtons = document.querySelectorAll('#guess-btn, .nav-item[href="#guess"]');
    console.log('Game buttons found:', gameButtons);

    gameButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // Предотвращаем стандартное действие ссылки
            console.log('Guess button clicked');

            fetchUserExperience(function(experience) {
                console.log("experience : " + experience)
                if (experience > 0) {
                    const token = localStorage.getItem('authToken'); // Получаем токен из localStorage
                    if (token) {
                        $.ajax({
                            url: '/game',
                            type: 'GET',
                            beforeSend: function(xhr) {
                                xhr.setRequestHeader('Authorization', 'Bearer ' + token); // Добавляем токен в заголовок запроса
                            },
                            success: function() {
                                console.log('Successfully authenticated for game');
                                // Перенаправляем на страницу игры
                                window.location.href = '/game'; // Относительный путь
                            },
                            error: function(jqXHR, textStatus, errorThrown) {
                                console.error('Error during game request:', textStatus, errorThrown);
                                alert('Ошибка при проверке аутентификации для игры.');
                            }
                        });
                    } else {
                        console.warn('Token аутентификации не найден.');
                        alert('Токен аутентификации не найден. Пожалуйста, войдите в систему.');
                    }
                } else {
                    alert('У вас недостаточно опыта для доступа к этому разделу.');
                }
            });
        });
    });

    // Обработчики для кнопок "Алфавит"
    const alphabetButtons = document.querySelectorAll('#alphabet-btn, .nav-item[href="#alphabet"]');
    console.log('Alphabet buttons found:', alphabetButtons);

    alphabetButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // Предотвращаем стандартное действие ссылки
            console.log('Alphabet button clicked');

            const token = localStorage.getItem('authToken'); // Получаем токен из localStorage
            if (token) {
                $.ajax({
                    url: '/alphabet',
                    type: 'GET',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + token); // Добавляем токен в заголовок запроса
                    },
                    success: function() {
                        console.log('Successfully authenticated for alphabet');
                        // Перенаправляем на страницу алфавита
                        window.location.href = '/alphabet'; // Относительный путь
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error('Error during alphabet request:', textStatus, errorThrown);
                        alert('Ошибка при проверке аутентификации.');
                    }
                });
            } else {
                console.warn('Token аутентификации не найден.');
                alert('Токен аутентификации не найден. Пожалуйста, войдите в систему.');
            }
        });
    });
});
