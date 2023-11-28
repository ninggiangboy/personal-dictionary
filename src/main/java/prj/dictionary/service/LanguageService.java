package prj.dictionary.service;

import prj.dictionary.dto.LanguageDTO;
import prj.dictionary.entity.Language;

import java.util.List;

public interface LanguageService {
    List<Language> findAll();

    List<Language> findAllAvailable();

    Language findByName(String name);

    List<Language> findAllByUserId(Integer userId);

    void update(Integer languageId, String languageName);

    void switchStatus(Integer languageId);

    Language findById(Integer languageId);

    void create(LanguageDTO languageDTO);
}
