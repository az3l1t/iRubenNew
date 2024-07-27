document.getElementById('logout').addEventListener('click', function() {
        localStorage.removeItem('authToken');
        // Перенаправление на страницу входа или домашнюю страницу
        window.location.href = 'index.html'; // Замените на нужный URL
    });
$(document).ready(function() {
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
        const newsContainer = $('#news-list');
        news.forEach(item => {
            const newsElement = $(`
                <div class="news-item">
                    <h3 class="news-title">${item.title}</h3>
                    <p class="news-info">${item.information}</p>
                    <img src="${item.linkToPhoto}" alt="${item.title}" class="news-image" />
                </div>
            `);
            newsContainer.append(newsElement);
        });
    }
});
