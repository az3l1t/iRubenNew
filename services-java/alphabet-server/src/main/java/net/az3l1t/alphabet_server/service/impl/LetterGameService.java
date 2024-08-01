package net.az3l1t.alphabet_server.service.impl;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import net.az3l1t.alphabet_server.api.dto.LetterGameRequest;
import net.az3l1t.alphabet_server.api.dto.LetterGameResponse;
import net.az3l1t.alphabet_server.core.entity.LetterGame;
import net.az3l1t.alphabet_server.infrastructure.repository.LetterGameRepository;
import net.az3l1t.alphabet_server.service.mapper.LetterGameMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class LetterGameService {
    private final LetterGameRepository letterGameRepository;
    private final LetterGameMapper letterGameMapper;
    private static Map<String, String> russianToArmenianMap = new HashMap<>();

    public LetterGameService(LetterGameRepository letterGameRepository, LetterGameMapper letterGameMapper) {
        this.letterGameRepository = letterGameRepository;
        this.letterGameMapper = letterGameMapper;
    }

    @PostConstruct
    public void init() {
        if (letterGameRepository.count() == 0) {
            russianToArmenianMap.put("а", "ա");
            russianToArmenianMap.put("б", "բ");
            russianToArmenianMap.put("г", "գ");
            russianToArmenianMap.put("д", "դ");
            russianToArmenianMap.put("е", "ե");
            russianToArmenianMap.put("з", "զ");
            russianToArmenianMap.put("э", "է");
            russianToArmenianMap.put("ы", "ը");
            russianToArmenianMap.put("у", "ու");
            russianToArmenianMap.put("в", "ւ");
            russianToArmenianMap.put("о", "օ");
            russianToArmenianMap.put("ш", "շ");
            russianToArmenianMap.put("к", "կ");
            russianToArmenianMap.put("х", "խ");
            russianToArmenianMap.put("ц", "ծ");
            russianToArmenianMap.put("л", "լ");
            russianToArmenianMap.put("м", "մ");
            russianToArmenianMap.put("н", "ն");
            russianToArmenianMap.put("т", "տ");
            russianToArmenianMap.put("р", "ր");
            russianToArmenianMap.put("п", "փ");
            russianToArmenianMap.put("с", "ս");
            russianToArmenianMap.put("ф", "ֆ");
            russianToArmenianMap.put("ж", "ջ");
            initArmGameData();
        }
    }

    private void initArmGameData() {
        List<LetterGame> games = new ArrayList<>();

        games.add(new LetterGame("ա", changeTheCharacterInList(Arrays.asList("арбуз", "папа", "алые", "акула"), "а"), Arrays.asList("арбуз", "папа", "алые", "акула")));
        games.add(new LetterGame("բ", changeTheCharacterInList(Arrays.asList("бусина", "победа", "собака", "балл"), "б"), Arrays.asList("бусина", "победа", "собака", "балл")));
        games.add(new LetterGame("գ", changeTheCharacterInList(Arrays.asList("горшок", "гитара", "город", "говорит"), "г"), Arrays.asList("горшок", "гитара", "город", "говорит")));
        games.add(new LetterGame("դ", changeTheCharacterInList(Arrays.asList("дверь", "дождь", "дом", "документ"), "д"), Arrays.asList("дверь", "дождь", "дом", "документ")));
        games.add(new LetterGame("ե", changeTheCharacterInList(Arrays.asList("петух", "пень", "пекарь", "петь"), "е"), Arrays.asList("петух", "пень", "пекарь", "петь")));
        games.add(new LetterGame("զ", changeTheCharacterInList(Arrays.asList("зебра", "зонт", "завтрак", "зеркало"), "з"), Arrays.asList("зебра", "зонт", "завтрак", "зеркало")));
        games.add(new LetterGame("է", changeTheCharacterInList(Arrays.asList("эксперт", "премьер", "где", "время"), "е"), Arrays.asList("эксперт", "премьер", "где", "время")));
        games.add(new LetterGame("ը", changeTheCharacterInList(Arrays.asList("плывут", "мир", "смысл", "пыль"), "ы"), Arrays.asList("плывут", "мир", "смысл", "пыль")));
        games.add(new LetterGame("ու", changeTheCharacterInList(Arrays.asList("пункт", "мур", "пул", "бур"), "у"), Arrays.asList("пункт", "мур", "пул", "бур")));
        games.add(new LetterGame("ւ", changeTheCharacterInList(Arrays.asList("вина", "вокруг", "пункт", "всех"), "в"), Arrays.asList("вина", "вокруг", "пункт", "всех")));
        games.add(new LetterGame("օ", changeTheCharacterInList(Arrays.asList("профиль", "доставка", "плод", "ход"), "о"), Arrays.asList("профиль", "доставка", "плод", "ход")));
        games.add(new LetterGame("շ", changeTheCharacterInList(Arrays.asList("шум", "школа", "шар", "шоколад"), "ш"), Arrays.asList("шум", "школа", "шар", "шоколад")));
        games.add(new LetterGame("կ", changeTheCharacterInList(Arrays.asList("кот", "круг", "книга", "клещи"), "к"), Arrays.asList("кот", "круг", "книга", "клещи")));
        games.add(new LetterGame("հ", changeTheCharacterInList(Arrays.asList("хомяк", "хлеб", "хватит", "хорошо"), "х"), Arrays.asList("хомяк", "хлеб", "хватит", "хорошо")));
        games.add(new LetterGame("խ", changeTheCharacterInList(Arrays.asList("химия", "хоть", "хор", "хром"), "х"), Arrays.asList("химия", "хоть", "хор", "хром")));
        games.add(new LetterGame("ձ", changeTheCharacterInList(Arrays.asList("дзюдо", "дзынь", "дзет", "дзеркало"), "д"), Arrays.asList("дзюдо", "дзынь", "дзет", "дзеркало")));
        games.add(new LetterGame("ծ", changeTheCharacterInList(Arrays.asList("цвет", "цифра", "цена", "цукор"), "ц"), Arrays.asList("цвет", "цифра", "цена", "цукор")));
        games.add(new LetterGame("լ", changeTheCharacterInList(Arrays.asList("лира", "лаборатория", "лямпа", "лагерь"), "л"), Arrays.asList("лира", "лаборатория", "лямпа", "лагерь")));
        games.add(new LetterGame("մ", changeTheCharacterInList(Arrays.asList("молоко", "мир", "мама", "мед"), "м"), Arrays.asList("молоко", "мир", "мама", "мед")));
        games.add(new LetterGame("ն", changeTheCharacterInList(Arrays.asList("ночь", "наука", "небо", "нога"), "н"), Arrays.asList("ночь", "наука", "небо", "нога")));
        games.add(new LetterGame("տ", changeTheCharacterInList(Arrays.asList("тельняшка", "тату", "тесто", "творог"), "т"), Arrays.asList("тельняшка", "тату", "тесто", "творог")));
        games.add(new LetterGame("վ", changeTheCharacterInList(Arrays.asList("вода", "вино", "время", "всегда"), "в"), Arrays.asList("вода", "вино", "время", "всегда")));
        games.add(new LetterGame("ր", changeTheCharacterInList(Arrays.asList("рыба", "роза", "ром", "ресторан"), "р"), Arrays.asList("рыба", "роза", "ром", "ресторан")));
        games.add(new LetterGame("ց", changeTheCharacterInList(Arrays.asList("цена", "церковь", "цвет", "цитрус"), "ц"), Arrays.asList("цена", "церковь", "цвет", "цитрус")));
        games.add(new LetterGame("փ", changeTheCharacterInList(Arrays.asList("папа", "пельмени", "пункт", "пицца"), "п"), Arrays.asList("папа", "пельмени", "пункт", "пицца")));
        games.add(new LetterGame("ք", changeTheCharacterInList(Arrays.asList("кафе", "класс", "крепость", "ключ"), "к"), Arrays.asList("кафе", "класс", "крепость", "ключ")));
        games.add(new LetterGame("ռ", changeTheCharacterInList(Arrays.asList("рок", "речь", "роза", "роса"), "р"), Arrays.asList("рок", "речь", "роза", "роса")));
        games.add(new LetterGame("ս", changeTheCharacterInList(Arrays.asList("солнце", "сапог", "сок", "сапфир"), "с"), Arrays.asList("солнце", "сапог", "сок", "сапфир")));
        games.add(new LetterGame("և", changeTheCharacterInList(Arrays.asList("евро", "эврика", "елка", "еж"), "е"), Arrays.asList("евро", "эврика", "елка", "еж")));
        games.add(new LetterGame("ֆ", changeTheCharacterInList(Arrays.asList("фото", "футбол", "финик", "фляга"), "ф"), Arrays.asList("фото", "футбол", "финик", "фляга")));
        games.add(new LetterGame("ջ", changeTheCharacterInList(Arrays.asList("жук", "жар", "жидкость", "журнал"), "ж"), Arrays.asList("жук", "жар", "жидкость", "журнал")));

        letterGameRepository.saveAll(games);
    }
    @Async
    public static List<String> changeTheCharacterInList(List<String> words, String character) {

        String armenianChar = russianToArmenianMap.get(character);
        if (armenianChar == null) {
            return words;
        }

        List<String> modifiedWords = new ArrayList<>();

        for (String word : words) {
            modifiedWords.add(word.replace(character, armenianChar));
        }

        return modifiedWords;
    }
    @Async
    public CompletableFuture<LetterGameResponse> createGameByCharacter(LetterGameRequest request){
        LetterGame letterGame = letterGameMapper.toLetterGame(request);
        letterGameRepository.save(letterGame);
        return CompletableFuture.completedFuture(letterGameMapper.toLetterGameResponse(letterGame));
    }
    @Async
    public CompletableFuture<LetterGameResponse> findGameByCharacter(String character){
        LetterGame letterGame = letterGameRepository.findById(character)
                .orElseThrow(
                        ()->new RuntimeException(
                                "The game was not found : %s".formatted(character)
                        )
                );
        return CompletableFuture.completedFuture(new LetterGameResponse(letterGame.getCharacter(), letterGame.getListOfArmenianWords(), letterGame.getListOfRussianWords()));
    }
}
