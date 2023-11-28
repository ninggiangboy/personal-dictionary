package prj.dictionary.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import prj.dictionary.constant.IConstant;
import prj.dictionary.dto.DefinitionDTO;
import prj.dictionary.dto.NewWordDTO;
import prj.dictionary.dto.UpdateWordDTO;
import prj.dictionary.entity.Definition;
import prj.dictionary.entity.Language;
import prj.dictionary.entity.User;
import prj.dictionary.entity.Word;
import prj.dictionary.repository.DefinitionRepository;
import prj.dictionary.repository.WordRepository;
import prj.dictionary.service.WordService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    DefinitionRepository definitionRepository;

    @Override
    public Page<Word> findAll(User user, int page, int sortMode) {
        return wordRepository.findByCreatedBy(user, PageRequest.of(page - 1, IConstant.WORDS_PER_PAGE, getSort(sortMode)));
    }

    @Override
    public List<Word> findAll(User user) {
        return wordRepository.findByCreatedBy(user);
    }

    @Override
    public Word findByTerm(User user, String term) {
        return wordRepository.findTopByCreatedByAndTerm(user, term);
    }

    @Override
    public Page<Word> search(User user, List<Word> searched, int page, int sortMode) {
        int pageSize = IConstant.WORDS_PER_PAGE;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, searched.size());
        List<Word> pageContent = searched.subList(startIndex, endIndex);
        switch (sortMode) {
            case 1:
                pageContent.sort(Comparator.comparing(Word::getTerm)); // Ascending order
                break;
            case 2:
                pageContent.sort(Comparator.comparing(Word::getCreatedAt).reversed()); // Descending order
                break;
            case 3:
                pageContent.sort(Comparator.comparing(Word::getModifiedAt).reversed()); // Descending order
                break;
            // Add more cases for different sorting options if needed
        }
        return new PageImpl<>(pageContent, PageRequest.of(page - 1, pageSize), searched.size());
    }

    private Sort getSort(int sortMode) {
        Sort sort = Sort.by("term").ascending();
        switch (sortMode) {
            case 2:
                sort = Sort.by("createdAt").descending();
                break;
            case 3:
                sort = Sort.by("modifiedAt").descending();
                break;
        }
        return sort;
    }

    @Override
    public Page<Word> findByTermStartWith(User user, String character, int page, int sortMode) {
        Page<Word> list = null;
        if (Character.isLetter(character.charAt(0))) {
            list = wordRepository.findByCreatedByAndTermStartingWith(user, character, PageRequest.of(page - 1, IConstant.WORDS_PER_PAGE, getSort(sortMode)));
        } else if (character.charAt(0) == '*') {
            String regex = "[^A-Za-z]%";
            list = wordRepository.findByCreatedByAndTermLike(user, regex, PageRequest.of(page - 1, IConstant.WORDS_PER_PAGE, getSort(sortMode)));
        }
        return list;
    }

    @Override
    public Page<Word> findByLanguage(User user, Language language, int page, int sortMode) {
        return wordRepository.findByCreatedByAndLanguage(user, language, PageRequest.of(page - 1, IConstant.WORDS_PER_PAGE, getSort(sortMode)));
    }

    @Override
    public Word findById(User user, Integer id) {
        return wordRepository.findByCreatedByAndWordId(user, id);
    }

    @Override
    @Transactional
    public void deleteById(User user, Integer id) {
        wordRepository.deleteByCreatedByAndWordId(user, id);
    }

    @Override
    public Word create(NewWordDTO newWordDTO, User user) {
        Word word = mapper.map(newWordDTO, Word.class);
        Definition definition = mapper.map(newWordDTO.getFirstDefinition(), Definition.class);
        definition.setWord(word);
        word.getDefinitions().add(definition);
        word.setCreatedBy(user);
        return wordRepository.save(word);
    }

    @Override
    public void addDefinition(Word word, DefinitionDTO definitionDTO) {
        Definition definition = mapper.map(definitionDTO, Definition.class);
        definition.setWord(word);
        word.getDefinitions().add(definition);
        word.setModifiedAt(LocalDateTime.now());
        wordRepository.save(word);
    }

    @Override
    public void editDefinition(Definition definition, DefinitionDTO definitionDTO) {
        Definition updated = mapper.map(definitionDTO, Definition.class);
        mapper.map(updated, definition);
        Word word = definition.getWord();
        word.setModifiedAt(LocalDateTime.now());
        wordRepository.save(word);
        definitionRepository.save(definition);
    }

    @Override
    @Transactional
    public void deleteDefinition(User user, Integer id) {
        Definition definition = definitionRepository.findById(id).orElse(null);
        Word word = definition.getWord();
        definitionRepository.deleteByUserIdAndId(user.getId(), id);
        word.setModifiedAt(LocalDateTime.now());
        wordRepository.save(word);

    }

    @Override
    public void delete(Integer id) {
        wordRepository.deleteById(id);
    }

    @Override
    public Word findRandom(User user) {
        return wordRepository.findRandomWordAndCreatedBy(user.getId());
    }

    @Override
    public void update(Word word, UpdateWordDTO updateWordDTO) {
        Word updated = mapper.map(updateWordDTO, Word.class);
        mapper.map(updated, word);
        word.setModifiedAt(LocalDateTime.now());
        wordRepository.save(word);
    }
}
