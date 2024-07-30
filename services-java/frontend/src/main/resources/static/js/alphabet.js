$(document).ready(function() {
    // Отправляем запрос на получение букв
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
            console.log('Letters fetched:', response);
            const letters = response.data.getAllLetters;
            displayLetters(letters);
        },
        error: function(error) {
            console.error('Error fetching letters:', error);
            alert('Ошибка получения букв.');
        }
    });

    function displayLetters(letters) {
        const container = $('.alphabet-container');
        letters.forEach(letter => {
            const letterElement = $(`<div class="letter-block">${letter.character}</div>`);
            container.append(letterElement);

            // Добавляем обработчик клика
            letterElement.on('click', function() {
                showLetterModal(letter.character);
            });
        });
    }

    function showLetterModal(character) {
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
                            getLetter(character: "${character}") {
                                character
                                script
                                sound
                                image
                                word
                                sentence
                            }
                        }
                    `
                }),
                success: function(response) {
                    const letter = response.data.getLetter;
                    // Заполняем модальное окно данными из ответа сервера
                    $('#letterModalLabel').text(`Буква: ${letter.character}`);
                    $('#letterCharacter').text(letter.character);
                    $('#letterScript').text(letter.script);
                    $('#letterSound').attr('src', letter.sound);
                    $('#letterImage').attr('src', letter.image);
                    $('#letterWord').text(letter.word);
                    $('#letterSentence').text(letter.sentence);

                    // Показываем модальное окно
                    $('#letterModal').modal('show');
                },
                error: function() {
                    alert('Ошибка при получении данных о букве.');
                }
            });
        } else {
            alert('Токен аутентификации не найден. Пожалуйста, войдите в систему.');
        }
    }

    // Обработчик на кнопке выхода для удаления токена
    $('#logout').on('click', function() {
        localStorage.removeItem('authToken');
        window.location.href = 'index.html'; // Замените на нужный URL
    });
});