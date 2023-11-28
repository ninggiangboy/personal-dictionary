package prj.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prj.dictionary.entity.Definition;
import prj.dictionary.entity.User;
import prj.dictionary.repository.DefinitionRepository;
import prj.dictionary.service.DefinitionService;

@Service
public class DefinitionServiceImpl implements DefinitionService {

    @Autowired
    DefinitionRepository definitionRepository;

    @Override
    public Definition findById(User user, Integer id) {
        return definitionRepository.findByWordCreatedByAndId(user, id);
    }
}
