package net.az3l1t.alphabet_server.service.impl;

import jakarta.annotation.PostConstruct;
import net.az3l1t.alphabet_server.api.dto.LetterButtonRequest;
import net.az3l1t.alphabet_server.api.dto.LetterButtonResponse;
import net.az3l1t.alphabet_server.api.dto.LetterButtonWithInfoResponse;
import net.az3l1t.alphabet_server.api.dto.LetterCharacterRequest;
import net.az3l1t.alphabet_server.core.entity.LetterButtonEntity;
import net.az3l1t.alphabet_server.infrastructure.repository.LetterButtonRepository;
import net.az3l1t.alphabet_server.service.mapper.LetterButtonMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class LetterButtonService {
    private final LetterButtonRepository letterButtonRepository;
    private final LetterButtonMapper mapper;

    public LetterButtonService(LetterButtonRepository letterButtonRepository, LetterButtonMapper mapper) {
        this.letterButtonRepository = letterButtonRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        if (letterButtonRepository.count() == 0) {
            initArmAlphabetData();
        }
    }

    private void initArmAlphabetData() {
        List<LetterButtonEntity> armAlphabet = Arrays.asList(
                new LetterButtonEntity("Ա", "Աբլա", "Աբլա - Это слово используется для обозначения малого или простого предмета."),
                new LetterButtonEntity("Բ", "Բազմություն", "Բազմություն - Означает множество или большое количество чего-либо."),
                new LetterButtonEntity("Գ", "Գիր", "Գիր - Слово для обозначения книги или письменности."),
                new LetterButtonEntity("Դ", "Դար", "Դար - Означает столетие или век."),
                new LetterButtonEntity("Ե", "Երեք", "Երեք - Число три."),
                new LetterButtonEntity("Զ", "Զավեշտ", "Զավեշտ - Это слово означает шутку или забаву."),
                new LetterButtonEntity("Է", "Էջ", "Էջ - Означает страницу в книге."),
                new LetterButtonEntity("Ը", "Ընկնել", "Ընկնել - Это глагол, означающий 'падать'."),
                new LetterButtonEntity("Թ", "Թեյ", "Թեյ - Означает чай, напиток."),
                new LetterButtonEntity("Ժ", "Ժպիտ", "Ժպիտ - Слово для обозначения улыбки."),
                new LetterButtonEntity("Ի", "Իրավունք", "Իրավունք - Означает право или права."),
                new LetterButtonEntity("Լ", "Լույս", "Լույս - Означает свет."),
                new LetterButtonEntity("Խ", "Խաղ", "Խաղ - Это слово означает игру."),
                new LetterButtonEntity("Ծ", "Ծով", "Ծով - Означает море."),
                new LetterButtonEntity("Կ", "Կառավարություն", "Կառավարություն - Означает правительство или управление."),
                new LetterButtonEntity("Հ", "Հույս", "Հույս - Это слово означает надежду."),
                new LetterButtonEntity("Ձ", "Ձի", "Ձի - Означает лошадь."),
                new LetterButtonEntity("Ղ", "Ղայթ", "Ղայթ - Означает свет или освещение."),
                new LetterButtonEntity("Ճ", "Ճանապարհ", "Ճանապարհ - Означает дорогу или путь."),
                new LetterButtonEntity("Մ", "Մայր", "Մայր - Слово для обозначения матери."),
                new LetterButtonEntity("Յ", "Յար", "Յար - Это слово может означать поля или угодья."),
                new LetterButtonEntity("Ն", "Նախագահ", "Նախագահ - Означает президента или главу."),
                new LetterButtonEntity("Շ", "Շատ", "Շատ - Означает много или большое количество."),
                new LetterButtonEntity("Ո", "Ոսկի", "Ոսկի - Означает золото."),
                new LetterButtonEntity("Չ", "Չար", "Չար - Означает зло или злой."),
                new LetterButtonEntity("Պ", "Պատմություն", "Պատմություն - Это слово означает историю или рассказ."),
                new LetterButtonEntity("Ջ", "Ջուր", "Ջուր - Означает воду."),
                new LetterButtonEntity("Ռ", "Ռազմ", "Ռազմ - Означает войну или боевые действия."),
                new LetterButtonEntity("Ս", "Սեր", "Սեր - Означает любовь."),
                new LetterButtonEntity("Վ", "Վերջ", "Վերջ - Означает конец."),
                new LetterButtonEntity("Տ", "Տեղ", "Տեղ - Означает место или локацию."),
                new LetterButtonEntity("Ր", "Րեկ", "Րեկ - Означает реку."),
                new LetterButtonEntity("Ց", "Ցանկ", "Ցանկ - Означает желание или список."),
                new LetterButtonEntity("Ու", "Ուրիշ", "Ուրիշ - Означает другой или иной."),
                new LetterButtonEntity("Փ", "Փաստ", "Փաստ - Означает факт."),
                new LetterButtonEntity("Ք", "Քարտ", "Քարտ - Означает карту или карту."),
                new LetterButtonEntity("Օ", "Օր", "Օր - Это слово обозначает день."),
                new LetterButtonEntity("Ֆ", "Ֆուտբոլ", "Ֆուտբոլ - Означает футбол, спортивная игра."),
                new LetterButtonEntity("և", "և", "և - Это союз 'и'."),
                new LetterButtonEntity("դ", "դ", "դ - Это маленькая буква 'դ'."),
                new LetterButtonEntity("թ", "թ", "թ - Это маленькая буква 'թ'."),
                new LetterButtonEntity("ռ", "ռ", "ռ - Это маленькая буква 'ռ'."),
                new LetterButtonEntity("ձ", "ձ", "ձ - Это маленькая буква 'ձ'."),
                new LetterButtonEntity("ղ", "ղ", "ղ - Это маленькая буква 'ղ'."),
                new LetterButtonEntity("չ", "չ", "չ - Это маленькая буква 'չ'.")
        );
        letterButtonRepository.saveAll(armAlphabet);
    }

    @Async
    public CompletableFuture<LetterButtonResponse> createTheButtonInfo(LetterButtonRequest request){
        LetterButtonEntity buttonInfo = mapper.toLetterButton(request);
        if(letterButtonRepository.findById(buttonInfo.getCharacter()).isPresent()){
            throw new RuntimeException("LetterButton was not found : %s".formatted(buttonInfo.getCharacter()));
        }
        letterButtonRepository.save(buttonInfo);
        return CompletableFuture.completedFuture(new LetterButtonResponse(buttonInfo.getCharacter()));
    }
    @Async
    public CompletableFuture<LetterButtonWithInfoResponse> getButtonInfo(LetterCharacterRequest request){
        LetterButtonEntity letterButtonEntity = letterButtonRepository.findById(request.getCharacter())
                .orElseThrow(
                        ()->new RuntimeException("LetterButton was found : %s".formatted(request.getCharacter()))
                );
        return CompletableFuture.completedFuture(new LetterButtonWithInfoResponse(
                letterButtonEntity.getCharacter(),
                letterButtonEntity.getWord(),
                letterButtonEntity.getSentence()
        ));
    }
    @Async
    public CompletableFuture<List<LetterButtonWithInfoResponse>> getAllButtonsInfo(){
        return CompletableFuture.completedFuture(letterButtonRepository.findAll()
                .stream()
                .map(mapper::toLetterWithInfo)
                .toList());
    }
}
