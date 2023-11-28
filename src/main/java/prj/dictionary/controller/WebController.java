package prj.dictionary.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import prj.dictionary.entity.Language;
import prj.dictionary.entity.User;
import prj.dictionary.entity.Word;
import prj.dictionary.model.CustomUserDetails;
import prj.dictionary.service.LanguageService;
import prj.dictionary.service.SearchService;
import prj.dictionary.service.WordService;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    SearchService searchService;

    @Autowired
    WordService wordService;

    @Autowired
    LanguageService languageService;

    @ModelAttribute("user")
    public User populateUser(Authentication authentication) {
        System.out.println();
        User user = null;
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            user = userDetails.getUser();
        }
        return user;
    }

    @ModelAttribute("languageList")
    public List<Language> populateLanguageList(Authentication authentication) {
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            return languageService.findAllByUserId(user.getId());
        }
        return languageService.findAll();
    }

    private int getSortMode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer sortMode = (Integer) session.getAttribute("sort");
        if (sortMode == null || sortMode == 0) {
            sortMode = 1;
            session.setAttribute("sort", sortMode);
        }
        return sortMode;
    }

    private int getSearchMode(HttpServletRequest request) {
        String searchModeString = request.getParameter("searchMode");
        HttpSession session = request.getSession();
        Integer searchMode = 1;
        if (searchModeString != null) {
            try {
                searchMode = Integer.parseInt(searchModeString);
                session.setAttribute("searchMode", searchMode);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            searchMode = (Integer) session.getAttribute("searchMode");
            if (searchMode == null) {
                searchMode = 1;
                session.setAttribute("searchMode", searchMode);
            }
        }
        return searchMode;
    }

    @GetMapping(value = {"/", "/home"})
    public String homepage(@RequestParam(required = false, defaultValue = "1") int page, Model model, Authentication authentication, HttpServletRequest request) {
        if (page < 1) page = 1;
        if (authentication != null) {
            int sortMode = getSortMode(request);
            Page<Word> pages = wordService.findAll((User) model.getAttribute("user"), page, sortMode);
            model.addAttribute("wordList", pages.stream().toList());
            model.addAttribute("pages", pages);
            model.addAttribute("pageCurrent", page);
        }
        return "home";
    }

    @GetMapping(value = {"/sort"})
    public String sort(@RequestParam Integer mode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("sort", mode);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping(value = {"/search"})
    public String searchPage(@RequestParam("term") String term, @RequestParam(required = false, defaultValue = "1") int page, Model model, HttpServletRequest request) {
        User user = (User) model.getAttribute("user");
        if (term.isBlank()) {
            return "redirect:/home";
        }
        List<Word> list = wordService.findAll(user);
        List<Word> searched = searchService.search(term, list, getSearchMode(request));
        int sortMode = getSortMode(request);
        Page<Word> pages = wordService.search(user, searched, page, sortMode);
        model.addAttribute("searchTerm", term);
        model.addAttribute("wordList", pages.stream().toList());
        model.addAttribute("pages", pages);
        model.addAttribute("pageCurrent", page);
        return "search";
    }

    @GetMapping(value = "/word/{id}")
    public String wordSelect(@PathVariable Integer id, Model model) {
        User user = (User) model.getAttribute("user");
        Word word = wordService.findById(user, id);
        model.addAttribute("word", word);
        return "word";
    }

    @GetMapping(value = "/random")
    public String getRandom(Model model) {
        User user = (User) model.getAttribute("user");
        Word word = wordService.findRandom(user);
        model.addAttribute("word", word);
        return "word";
    }

    @GetMapping(value = "/browse")
    public String getBrowse(@RequestParam String character, @RequestParam(required = false, defaultValue = "1") int page, Model model, HttpServletRequest request) {
        User user = (User) model.getAttribute("user");
        int sortMode = getSortMode(request);
        Page<Word> pages = wordService.findByTermStartWith(user, character, page, sortMode);
        model.addAttribute("wordList", pages.stream().toList());
        model.addAttribute("searchChar", character);
        model.addAttribute("pages", pages);
        model.addAttribute("pageCurrent", page);
        return "browse";
    }

    @GetMapping(value = "/language/{languageName}")
    public String getLanguage(@PathVariable String languageName, @RequestParam(required = false, defaultValue = "1") int page, Model model, HttpServletRequest request) {
        User user = (User) model.getAttribute("user");
        Language language = languageService.findByName(languageName);
        Page<Word> pages = wordService.findByLanguage(user, language, page, getSortMode(request));
        model.addAttribute("searchLanguage", languageName);
        model.addAttribute("wordList", pages.stream().toList());
        model.addAttribute("pages", pages);
        model.addAttribute("pageCurrent", page);
        return "language";
    }
}