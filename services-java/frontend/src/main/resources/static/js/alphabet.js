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
            const letterElement = $(`
                <div class="letter-block">
                    ${letter.character}
                </div>
            `);
            container.append(letterElement);
        });
    }
});
