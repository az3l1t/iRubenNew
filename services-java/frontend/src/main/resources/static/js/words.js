let currentWordIndex = 0;
let words = [];

function loadGroup(groupId) {
    fetch(`http://localhost:8030/words/${groupId}`)
        .then(response => response.json())
        .then(data => {
            words = data.ListOfArmenianWords.map((word, index) => ({
                word: word,
                translation: data.ListOfRussianWords[index]
            }));
            currentWordIndex = 0;
            showWord();
            document.getElementById('wordContainer').style.display = 'block';
        })
        .catch(error => console.error('Error fetching data:', error));
}

function showWord() {
    if (currentWordIndex >= words.length) {
        document.getElementById('wordContainer').style.display = 'none';
        alert('No more words');
        return;
    }
    document.getElementById('word1').textContent = words[currentWordIndex].word;
    document.getElementById('translation1').textContent = words[currentWordIndex].translation;
}

function showNextWord() {
    currentWordIndex++;
    showWord();
}
