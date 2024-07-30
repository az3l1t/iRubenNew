package net.az3l1t.alphabet_server.service.impl;

import jakarta.annotation.PostConstruct;
import net.az3l1t.alphabet_server.api.dto.LetterButtonRequest;
import net.az3l1t.alphabet_server.api.dto.LetterButtonResponse;
import net.az3l1t.alphabet_server.api.dto.LetterButtonWithInfoResponse;
import net.az3l1t.alphabet_server.api.dto.LetterCharacterRequest;
import net.az3l1t.alphabet_server.core.entity.LetterButtonEntity;
import net.az3l1t.alphabet_server.infrastructure.repository.LetterButtonRepository;
import net.az3l1t.alphabet_server.service.mapper.LetterButtonMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
                new LetterButtonEntity("Ա", "/static/buttonsImg/1.jpg", "Աբլա", "Աբլա - Это слово используется для обозначения малого или простого предмета."),
                new LetterButtonEntity("Բ", "/static/buttonsImg/2.jpg", "Բազմություն", "Բազմություն - Означает множество или большое количество чего-либо."),
                new LetterButtonEntity("Գ", "/static/buttonsImg/3.jpg", "Գիր", "Գիր - Слово для обозначения книги или письменности."),
                new LetterButtonEntity("Դ", "/static/buttonsImg/4.jpg", "Դար", "Դար - Означает столетие или век."),
                new LetterButtonEntity("Ե", "/static/buttonsImg/5.jpg", "Երեք", "Երեք - Число три."),
                new LetterButtonEntity("Զ", "/static/buttonsImg/6.jpg", "Զավեշտ", "Զավեշտ - Это слово означает шутку или забаву."),
                new LetterButtonEntity("Է", "/static/buttonsImg/7.jpg", "Էջ", "Էջ - Означает страницу в книге."),
                new LetterButtonEntity("Ը", "/static/buttonsImg/8.jpg", "Ընկնել", "Ընկնել - Это глагол, означающий 'падать'."),
                new LetterButtonEntity("Թ", "/static/buttonsImg/9.jpg", "Թեյ", "Թեյ - Означает чай, напиток."),
                new LetterButtonEntity("Ժ", "/static/buttonsImg/10.jpg", "Ժպիտ", "Ժպիտ - Слово для обозначения улыбки."),
                new LetterButtonEntity("Լ", "/static/buttonsImg/11.jpg", "Լույս", "Լույս - Означает свет."),
                new LetterButtonEntity("Խ", "/static/buttonsImg/12.jpg", "Խաղ", "Խաղ - Это слово означает игру."),
                new LetterButtonEntity("Ծ", "/static/buttonsImg/13.jpg", "Ծով", "Ծով - Означает море."),
                new LetterButtonEntity("Կ", "/static/buttonsImg/14.jpg", "Կառավարություն", "Կառավարություն - Означает правительство или управление."),
                new LetterButtonEntity("Հ", "/static/buttonsImg/15.jpg", "Հույս", "Հույս - Это слово означает надежду."),
                new LetterButtonEntity("Ձ", "/static/buttonsImg/16.jpg", "Ձի", "Ձի - Означает лошадь."),
                new LetterButtonEntity("Ղ", "/static/buttonsImg/17.jpg", "Ղայթ", "Ղայթ - Означает свет или освещение."),
                new LetterButtonEntity("Ճ", "/static/buttonsImg/18.jpg", "Ճանապարհ", "Ճանապարհ - Означает дорогу или путь."),
                new LetterButtonEntity("Մ", "/static/buttonsImg/19.jpg", "Մայր", "Մայր - Слово для обозначения матери."),
                new LetterButtonEntity("Յ", "/static/buttonsImg/20.jpg", "Յար", "Յար - Это слово может означать поля или угодья."),
                new LetterButtonEntity("Ն", "/static/buttonsImg/21.jpg", "Նախագահ", "Նախագահ - Означает президента или главу."),
                new LetterButtonEntity("Շ", "/static/buttonsImg/22.jpg", "Շատ", "Շատ - Означает много или большое количество."),
                new LetterButtonEntity("Օ", "/static/buttonsImg/23.jpg", "Օր", "Օր - Это слово обозначает день."),
                new LetterButtonEntity("Ֆ", "/static/buttonsImg/24.jpg", "Ֆուտբոլ", "Ֆուտբոլ - Означает футбол, спортивная игра."),
                new LetterButtonEntity("Չ", "/static/buttonsImg/25.jpg", "Չար", "Չար - Означает зло или злой."),
                new LetterButtonEntity("Պ", "/static/buttonsImg/26.jpg", "Պատմություն", "Պատմություն - Это слово означает историю или рассказ."),
                new LetterButtonEntity("Ջ", "/static/buttonsImg/27.jpg", "Ջուր", "Ջուր - Означает воду."),
                new LetterButtonEntity("Ռ", "/static/buttonsImg/28.jpg", "Ռազմ", "Ռազմ - Означает войну или боевые действия."),
                new LetterButtonEntity("Ս", "/static/buttonsImg/29.jpg", "Սեր", "Սեր - Означает любовь."),
                new LetterButtonEntity("Վ", "/static/buttonsImg/30.jpg", "Վերջ", "Վերջ - Означает конец.")
        );
        letterButtonRepository.saveAll(armAlphabet);
    }

    public LetterButtonResponse createTheButtonInfo(LetterButtonRequest request){
        LetterButtonEntity buttonInfo = mapper.toLetterButton(request);
        if(letterButtonRepository.findById(buttonInfo.getCharacter()).isPresent()){
            throw new RuntimeException("LetterButton was not found : %s".formatted(buttonInfo.getCharacter()));
        }
        letterButtonRepository.save(buttonInfo);
        return new LetterButtonResponse(buttonInfo.getCharacter());
    }

    public LetterButtonWithInfoResponse getButtonInfo(LetterCharacterRequest request){
        LetterButtonEntity letterButtonEntity = letterButtonRepository.findById(request.getCharacter())
                .orElseThrow(
                        ()->new RuntimeException("LetterButton was found : %s".formatted(request.getCharacter()))
                );
        return  new LetterButtonWithInfoResponse(
                letterButtonEntity.getCharacter(),
                letterButtonEntity.getUrlToPhoto(),
                letterButtonEntity.getWord(),
                letterButtonEntity.getSentence()
        );
    }

    public List<LetterButtonWithInfoResponse> getAllButtonsInfo(){
        return letterButtonRepository.findAll()
                .stream()
                .map(mapper::toLetterWithInfo)
                .toList();
    }
}
