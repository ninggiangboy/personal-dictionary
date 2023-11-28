package prj.dictionary.service;

import org.springframework.data.domain.Page;
import prj.dictionary.dto.DefinitionDTO;
import prj.dictionary.dto.NewWordDTO;
import prj.dictionary.dto.UpdateWordDTO;
import prj.dictionary.entity.Definition;
import prj.dictionary.entity.Language;
import prj.dictionary.entity.User;
import prj.dictionary.entity.Word;

import java.util.List;

public interface WordService {
    Page<Word> findAll(User user, int page, int sortMode);

    List<Word> findAll(User user);

    Word findByTerm(User user, String term);

    Page<Word> search(User user, List<Word> searched, int page, int sortMode);

    Page<Word> findByTermStartWith(User user, String character, int page, int sortMode);

    Page<Word> findByLanguage(User user, Language language, int page, int sortMode);

    Word findById(User user, Integer id);

    void deleteById(User user, Integer id);

    Word create(NewWordDTO newWordDTO, User user);

    void addDefinition(Word word, DefinitionDTO definitionDTO);

    void editDefinition(Definition definition, DefinitionDTO definitionDTO);

    void deleteDefinition(User user, Integer id);

    void delete(Integer id);

    Word findRandom(User user);

    void update(Word word, UpdateWordDTO updateWordDTO);
}
