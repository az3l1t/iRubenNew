$(document).ready(function() {
    console.log('Document is ready');

    // Отправляем запрос для получения новостей
    $.ajax({
        url: 'http://localhost:8015/news',
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

    document.getElementById('logout').addEventListener('click', function() {
        console.log('Logout button clicked');
        localStorage.removeItem('authToken');
        // Перенаправление на страницу входа или домашнюю страницу
        window.location.href = 'index.html'; // Замените на нужный URL
    });

    // Обработчики для всех элементов, которые должны перенаправлять на страницу игры
    const gameButtons = document.querySelectorAll('#guess-btn, .nav-item[href="#guess"]');
    console.log('Game buttons found:', gameButtons);

    gameButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // Предотвращаем стандартное действие ссылки
            console.log('Guess button clicked');

            const token = localStorage.getItem('authToken'); // Получаем токен из localStorage
            console.log('Token:', token);

            if (token) {
                $.ajax({
                    url: '/game',
                    type: 'GET',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + token); // Добавляем токен в заголовок запроса
                        console.log('Sending request to /game with token:', token);
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
                console.warn('Token aутентификации не найден.');
                alert('Токен аутентификации не найден. Пожалуйста, войдите в систему.');
            }
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
            console.log('Token:', token);

            if (token) {
                $.ajax({
                    url: '/alphabet',
                    type: 'GET',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + token); // Добавляем токен в заголовок запроса
                        console.log('Sending request to /alphabet with token:', token);
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
                console.warn('Token aутентификации не найден.');
                alert('Токен aутентификации не найден. Пожалуйста, войдите в систему.');
            }
        });
    });
});