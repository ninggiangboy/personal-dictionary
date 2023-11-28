package prj.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prj.dictionary.dto.LanguageDTO;
import prj.dictionary.entity.Language;
import prj.dictionary.repository.LanguageRepository;
import prj.dictionary.service.LanguageService;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public List<Language> findAllAvailable() {
        return languageRepository.findAllByIsEnable(true);
    }

    public Language findByName(String name) {
        return languageRepository.findByName(name);
    }

    @Override
    public List<Language> findAllByUserId(Integer userId) {
        return languageRepository.findAllByUserId(userId);
    }

    @Override
    public void update(Integer languageId, String languageName) {
        Language language = languageRepository.findById(languageId).get();
        language.setName(languageName);
        languageRepository.save(language);
    }

    @Override
    public void switchStatus(Integer languageId) {
        Language language = languageRepository.findById(languageId).get();
//        language.setIsEnable(!language.getIsEnable());
        languageRepository.save(language);
    }

    @Override
    public Language findById(Integer languageId) {
        return languageRepository.findById(languageId).orElse(null);
    }

    @Override
    public void create(LanguageDTO languageDTO) {
        if (languageRepository.findByName(languageDTO.getName()) != null) {
            return;
        }
        Language language = new Language();
        language.setName(languageDTO.getName());
        languageRepository.save(language);
    }
}
