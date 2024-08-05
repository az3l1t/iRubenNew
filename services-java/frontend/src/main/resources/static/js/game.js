$(document).ready(function() {
    const username = localStorage.getItem('username');
    let currentWordIndex = 0;
    let currentWordSetIndex = 0;
    let gameData = [];
    let maxValue = 0;
    let shuffledGameData = [];

    // Функция для получения maxValue
    function fetchMaxValue() {
        $.ajax({
            url: `http://localhost:8025/${username}`,
            method: 'GET',
            success: function(response) {
                maxValue = response.maxValue;
                fetchGameData(maxValue);
            },
            error: function() {
                $('#game-content').html('<p>Ошибка получения данных.</p>');
            }
        });
    }

    // Функция для получения данных игры
    function fetchGameData(until) {
        $.ajax({
            url: 'http://localhost:8020/graphql',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                query: `
                    query {
                        findAllGamesUntil(until: ${until}) {
                            listOfRussianWords,
                            listOfArmenianWords
                        }
                    }
                `
            }),
            success: function(response) {
                if (response.data && response.data.findAllGamesUntil) {
                    gameData = response.data.findAllGamesUntil;
                    shuffledGameData = shuffleArray(gameData);
                    displayNextWordSet();
                } else {
                    $('#game-content').html('<p>Нет данных для отображения.</p>');
                }
            },
            error: function() {
                $('#game-content').html('<p>Ошибка при загрузке данных.</p>');
            }
        });
    }

    // Функция для перемешивания массива
    function shuffleArray(array) {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]];
        }
        return array;
    }

    // Функция для отображения следующего набора слов
    function displayNextWordSet() {
        if (currentWordSetIndex >= shuffledGameData.length) {
            $('#game-content').html('<p>Поздравляем! Вы угадали все слова!</p>');
            return;
        }

        const currentSet = shuffledGameData[currentWordSetIndex];
        currentWordIndex = 0;

        $('#game-content').html(`
            <div class="word-container">
                <p class="word">Армянское слово: ${currentSet.listOfArmenianWords[currentWordIndex]}</p>
                <input type="text" id="user-answer" placeholder="Введите русское слово" class="form-control">
                <button id="submit-answer" class="btn btn-primary mt-2">Проверить</button>
                <div id="feedback"></div>
            </div>
        `);

        $('#submit-answer').click(function() {
            checkAnswer();
        });
    }

    // Функция для проверки ответа пользователя
    function checkAnswer() {
        const userAnswer = $('#user-answer').val().trim();
        const correctAnswer = shuffledGameData[currentWordSetIndex].listOfRussianWords[currentWordIndex];

        if (userAnswer === correctAnswer) {
            $('#feedback').html('<p class="text-success">Правильно!</p>');
            currentWordIndex++;
            if (currentWordIndex >= shuffledGameData[currentWordSetIndex].listOfRussianWords.length) {
                currentWordSetIndex++;
                displayNextWordSet();
            } else {
                $('#game-content').html(`
                    <div class="word-container">
                        <p class="word">Армянское слово: ${shuffledGameData[currentWordSetIndex].listOfArmenianWords[currentWordIndex]}</p>
                        <input type="text" id="user-answer" placeholder="Введите русское слово" class="form-control">
                        <button id="submit-answer" class="btn btn-primary mt-2">Проверить</button>
                        <div id="feedback"></div>
                    </div>
                `);
                $('#submit-answer').click(function() {
                    checkAnswer();
                });
            }
        } else {
            $('#feedback').html('<p class="text-danger">Попробуйте еще раз.</p>');
        }
    }

    // Обработчик нажатия на кнопку "Начать игру"
    $('#start-game').click(function() {
        $(this).hide(); // Скрыть кнопку "Начать игру"
        fetchMaxValue(); // Запрос на получение maxValue и старт игры
    });

    // Обработчик нажатия на кнопку "Выйти"
    $('#logout').on('click', function() {
        $.ajax({
            url: '/success', // Запрос на изменение страницы
            method: 'GET',
            success: function(response) {
                window.location.href = '/success'; // Перенаправление на success.html
            },
            error: function() {
                console.log('Ошибка при запросе изменения страницы.');
            }
        });
    });
});
