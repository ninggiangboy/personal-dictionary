package prj.dictionary.service;

import prj.dictionary.entity.Definition;
import prj.dictionary.entity.User;

public interface DefinitionService {
    Definition findById(User user, Integer id);
}
