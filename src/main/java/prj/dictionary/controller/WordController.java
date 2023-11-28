package prj.dictionary.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prj.dictionary.dto.DefinitionDTO;
import prj.dictionary.dto.NewWordDTO;
import prj.dictionary.dto.UpdateWordDTO;
import prj.dictionary.entity.*;
import prj.dictionary.model.CustomUserDetails;
import prj.dictionary.repository.DefinitionRepository;
import prj.dictionary.service.DefinitionService;
import prj.dictionary.service.LanguageService;
import prj.dictionary.service.TypeService;
import prj.dictionary.service.WordService;

import java.util.List;

@Controller
public class WordController {
    @Autowired
    WordService wordService;

    @Autowired
    TypeService typeService;

    @Autowired
    LanguageService languageService;

    @Autowired
    DefinitionService definitionService;

    @Autowired
    DefinitionRepository definitionRepository;

    @ModelAttribute("languageList")
    public List<Language> populateLanguageList() {
        return languageService.findAll();
    }

    @ModelAttribute("languageListAvailable")
    public List<Language> populateLanguageListNewWord() {
        return languageService.findAllAvailable();
    }

    @ModelAttribute("typeList")
    public List<Type> populateTypeList() {
        return typeService.findAll();
    }

    @ModelAttribute("user")
    public User populateUser(Authentication authentication) {
        User user = null;
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            user = userDetails.getUser();
        }
        return user;
    }

    @GetMapping("/word/delete")
    public String deleteWord(@RequestParam Integer id, Model model, HttpServletRequest request) {
        User user = (User) model.getAttribute("user");
        wordService.deleteById(user, id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/word/create")
    public String createWord(Model model) {
        model.addAttribute("newWordDTO", new NewWordDTO());
        return "word/create-word";
    }

    @PostMapping("/word/create")
    public String createWord(@Valid NewWordDTO newWordDTO, BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        User user = (User) model.getAttribute("user");
        String skipTermCheck = request.getParameter("skipTermCheck");
        if (skipTermCheck != null) {
            request.getSession().setAttribute("skipTermChecked", Boolean.valueOf(skipTermCheck));
        } else {
            if (wordService.findByTerm(user, newWordDTO.getTerm()) != null) {
                bindingResult.rejectValue("term", "error.term", "This term already exists. If you still want to add this term, please click this checkbox.");
            }
            request.getSession().removeAttribute("skipTermChecked");
        }
        if (bindingResult.hasErrors()) {
            return "word/create-word";
        }
        Word word = wordService.create(newWordDTO, user);
        request.getSession().removeAttribute("skipTermChecked");
        if (request.getParameter("addDef") != null) {
            return "redirect:/word/add-definition?id=" + word.getWordId();
        } else {
            return "redirect:/word/" + word.getWordId();
        }
    }

    @GetMapping("/word/add-definition")
    public String addDefinition(@RequestParam Integer id, Model model) {
        User user = (User) model.getAttribute("user");
        Word word = wordService.findById(user, id);
        model.addAttribute("word", word);
        model.addAttribute("definitionDTO", new DefinitionDTO());
        return "word/add-definition";
    }

    @PostMapping("/word/add-definition")
    public String addDefinition(@RequestParam Integer id, @Valid DefinitionDTO definitionDTO, BindingResult bindingResult, Model model, HttpServletRequest request) {
        User user = (User) model.getAttribute("user");
        Word word = wordService.findById(user, id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("word", word);
            return "word/add-definition";
        }
        wordService.addDefinition(word, definitionDTO);
        if (request.getParameter("addDef") != null) {
            return "redirect:/word/add-definition?id=" + word.getWordId();
        } else {
            return "redirect:/word/update?id=" + word.getWordId();
        }
    }

    @GetMapping("/word/update")
    public String updateWord(@RequestParam Integer id, Model model) {
        User user = (User) model.getAttribute("user");
        Word word = wordService.findById(user, id);
        model.addAttribute("word", word);
        model.addAttribute("updateWordDTO", new UpdateWordDTO());
        return "word/update-word";
    }

    @PostMapping("/word/update")
    public String updateWord(@RequestParam Integer id, @Valid UpdateWordDTO updateWordDTO, BindingResult bindingResult, Model model, HttpServletRequest request) {
        User user = (User) model.getAttribute("user");
        Word word = wordService.findById(user, id);
        String skipTermCheck = request.getParameter("skipTermCheck");
        if (skipTermCheck != null) {
            request.getSession().setAttribute("skipTermChecked", Boolean.valueOf(skipTermCheck));
        } else {
            if (!word.getTerm().equals(updateWordDTO.getTerm()) && wordService.findByTerm(user, updateWordDTO.getTerm()) != null) {
                bindingResult.rejectValue("term", "error.term", "This term already exists. If you still want to add this term, please click this checkbox.");
            }
            request.getSession().removeAttribute("skipTermChecked");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("word", word);
            return "word/update-word";
        }
        wordService.update(word, updateWordDTO);
        request.getSession().removeAttribute("skipTermChecked");
        return "redirect:/word/" + word.getWordId();
    }

    @GetMapping("/word/edit-definition")
    public String editDefinition(@RequestParam Integer id, Model model) {
        User user = (User) model.getAttribute("user");
        Definition definition = definitionService.findById(user, id);
        model.addAttribute("definition", definition);
        model.addAttribute("definitionDTO", new DefinitionDTO());
        return "word/edit-definition";
    }

    @PostMapping("/word/edit-definition")
    public String editDefinition(@RequestParam Integer id, @Valid DefinitionDTO definitionDTO, BindingResult bindingResult, Model model, HttpServletRequest request) {
        User user = (User) model.getAttribute("user");
        Definition definition = definitionService.findById(user, id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("definition", definition);
            return "word/edit-definition";
        }
        wordService.editDefinition(definition, definitionDTO);
        return "redirect:/word/update?id=" + definition.getWord().getWordId();
    }

    @GetMapping("/word/delete-definition")
    public String deleteDefinition(@RequestParam Integer id, Model model, HttpServletRequest request) {
        User user = (User) model.getAttribute("user");
        Definition definition = definitionService.findById(user, id);
        Word word = definition.getWord();
        wordService.deleteDefinition(user, id);
        return "redirect:/word/update?id=" + word.getWordId();
    }
}
