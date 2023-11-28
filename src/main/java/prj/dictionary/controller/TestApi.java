package prj.dictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import prj.dictionary.model.CustomUserDetails;
import prj.dictionary.repository.DefinitionRepository;
import prj.dictionary.repository.UserRepository;
import prj.dictionary.repository.WordRepository;
import prj.dictionary.service.RoleService;
import prj.dictionary.service.SearchService;

@RestController
public class TestApi {
    @Autowired
    WordRepository wordRepository;

    @Autowired
    DefinitionRepository definitionRepository;

    @Autowired
    SearchService searchService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @GetMapping("/test")
    public String test(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getAuthorities().toString();
    }

    public static void main(String[] args) {
    }
}
