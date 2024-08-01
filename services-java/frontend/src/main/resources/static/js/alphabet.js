$(document).ready(function() {
    let currentWordIndex = 0;
    let words = [];
    let armenianWords = [];
    let maxExp = 0; // Изначально 0, будет обновлено при загрузке
    let lastUnlockedLetterIndex = -1; // Изначально нет разблокированных букв
    let completedLetters = []; // Массив для отслеживания пройденных букв

    // Функция для обновления приветственного сообщения
    function updateWelcomeMessage(username, exp) {
        $('#welcome-message').html(`
            Добро пожаловать, <span>${username}</span>.<br>
            На данный момент ваш опыт равен <span>${exp}</span>.
        `);
    }

    // Получение имени пользователя из localStorage
    const username = localStorage.getItem('username');

    if (username) {
        // Запрос к серверу для получения опыта пользователя
        $.ajax({
            url: `http://localhost:8005/${username}`,
            method: 'GET',
            success: function(response) {
                const exp = response.exp;
                maxExp = exp;
                updateWelcomeMessage(username, exp);

                // Запрос на получение букв
                $.ajax({
                    url: 'http://localhost:8020/graphql',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        query: `
                            query {
                                getAllLetters {
                                    character
                                }
                            }
                        `
                    }),
                    success: function(response) {
                        console.log('GraphQL response:', response);

                        if (response.data && response.data.getAllLetters) {
                            const letters = response.data.getAllLetters;
                            displayLetters(letters);
                        } else {
                            console.error('Unexpected response structure:', response);
                            alert('Ошибка получения букв.');
                        }
                    },
                    error: function(error) {
                        console.error('Error fetching letters:', error);
                        alert('Ошибка получения букв.');
                    }
                });
            },
            error: function() {
                console.error('Ошибка получения опыта пользователя.');
                alert('Ошибка получения вашего опыта.');
            }
        });
    } else {
        $('#welcome-message').html('<p>Имя пользователя не найдено.</p>');
    }

    function displayLetters(letters) {
        const container = $('.alphabet-container');
        container.empty(); // Очистка контейнера перед добавлением новых букв

        letters.forEach((letter, index) => {
            // Присваиваем каждой букве номер (index)
            const isDisabled = index > maxExp; // Блокируем буквы, которые не доступны пользователю
            const letterElement = $(`<div class="letter-block ${isDisabled ? 'disabled' : ''}" data-index="${index}">${letter.character}</div>`);
            container.append(letterElement);

            // Добавляем обработчик клика только для активных букв
            if (!isDisabled) {
                letterElement.on('click', function() {
                    showLetterModal(letter.character, index); // Передаем номер буквы
                });
            }
        });
    }


    function showLetterModal(character, index) {
        const token = localStorage.getItem('authToken');
        if (token) {
            $.ajax({
                url: 'http://localhost:8020/graphql',
                method: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                data: JSON.stringify({
                    query: `
                        query {
                            findById(character: "${character}") {
                                character
                                listOfRussianWords
                                listOfArmenianWords
                            }
                        }
                    `
                }),
                success: function(response) {
                    const letter = response.data.findById;
                    if (letter) {
                        $('#letterModalLabel').text(`Буква: ${letter.character}`);
                        $('#letterCharacter').text(letter.character);
                        $('#letterWord').text(letter.listOfRussianWords.join(', '));
                        $('#letterSentence').text('');

                        // Сохраняем данные для игры
                        $('#startGameButton').off('click').on('click', function() {
                            startGame(letter.listOfArmenianWords, letter.listOfRussianWords, index); // Передаем номер буквы
                        });

                        $('#letterModal').modal('show');
                    } else {
                        alert('Буква не найдена.');
                    }
                },
                error: function() {
                    alert('Ошибка при получении данных о букве.');
                }
            });
        } else {
            alert('Токен аутентификации не найден. Пожалуйста, войдите в систему.');
        }
    }


    function startGame(armenianWordsList, russianWordsList, letterIndex) {
        $('#letterModal').modal('hide');
        armenianWords = armenianWordsList;
        words = russianWordsList;
        currentWordIndex = 0;
        lastUnlockedLetterIndex = letterIndex; // Сохраняем номер последней разблокированной буквы
        $('#game-container').show();
        showNextWord();
    }


    function showNextWord() {
        console.log('maxExp:', maxExp); // Логирование текущего уровня опыта
        console.log('lastUnlockedLetterIndex:', lastUnlockedLetterIndex); // Логирование последнего номера буквы

        if (currentWordIndex < armenianWords.length) {
            $('#current-word').text(armenianWords[currentWordIndex]);
        } else {
            $('#game-container').hide();
            alert('Поздравляем! Вы угадали все слова.');

            const username = localStorage.getItem('username');
            if (username) {
                // Проверяем, является ли текущий номер буквы следующим доступным
                if (maxExp === lastUnlockedLetterIndex) {
                    $.ajax({
                        url: `http://localhost:8005/addExp/${username}`,
                        method: 'GET',
                        success: function() {
                            // Опыт увеличен, обновляем данные
                            maxExp++;
                            updateWelcomeMessage(username, maxExp);

                            // Обновляем доступные буквы
                            $.ajax({
                                url: 'http://localhost:8020/graphql',
                                method: 'POST',
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    query: `
                                        query {
                                            getAllLetters {
                                                character
                                            }
                                        }
                                    `
                                }),
                                success: function(response) {
                                    const letters = response.data.getAllLetters;
                                    displayLetters(letters);
                                },
                                error: function(error) {
                                    console.error('Error fetching letters:', error);
                                    alert('Ошибка получения букв.');
                                }
                            });
                        },
                        error: function() {
                            alert('Ошибка увеличения опыта.');
                        }
                    });
                } else {
                    console.log('Опыт не увеличен, так как буква не соответствует следующему номеру.');
                }
            }
        }
    }

    $('#game-form').on('submit', function(event) {
        event.preventDefault();
        const userGuess = $('#guess').val().trim();
        if (userGuess === words[currentWordIndex]) {
            $('#result').html('<p class="text-success">Правильно!</p>');
            currentWordIndex++;
            $('#guess').val('');
            showNextWord();
        } else {
            $('#result').html('<p class="text-danger">Попробуйте снова.</p>');
        }
    });

    $('#logout').on('click', function() {
        localStorage.removeItem('authToken');
        window.location.href = 'index.html'; // Замените на нужный URL
    });
});
