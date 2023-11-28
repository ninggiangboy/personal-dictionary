package prj.dictionary.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prj.dictionary.dto.LanguageDTO;
import prj.dictionary.entity.Language;
import prj.dictionary.service.LanguageService;

import java.util.List;

@Controller
public class LanguageController {

    @Autowired
    LanguageService languageService;

    @GetMapping(value = "/languages/modifier")
    public String modifier(@RequestParam Integer languageId, HttpServletRequest request, Model model) {
        if (languageId == null || languageService.findById(languageId) == null) {
            List<Language> languages = languageService.findAllAvailable();
            model.addAttribute("languages", languages);
            model.addAttribute("error", "Language is required");
            return "dashboard/language-manager";
        }
        String action = request.getParameter("action");
        if (action.equals("Edit")) {
            return "redirect:/languages/update?id=" + languageId;
        } else if (action.equals("Remove")) {
            return "redirect:/languages/delete?id=" + languageId;
        }
        return "redirect:/dashboard/languages";
    }

    @GetMapping(value = "/languages/delete")
    public String delete(@RequestParam Integer id, Model model) {
        if (id == null || languageService.findById(id) == null) {
            List<Language> languages = languageService.findAllAvailable();
            model.addAttribute("languages", languages);
            model.addAttribute("error", "Language is required");
            return "dashboard/language-manager";
        }
        languageService.switchStatus(id);
        return "redirect:/dashboard/languages";
    }

    @GetMapping(value = "/languages/update")
    public String update(@RequestParam Integer id, Model model) {
        model.addAttribute("language", languageService.findById(id));
        model.addAttribute("languageDTO", new LanguageDTO());
        return "language/edit-language";
    }

//    @PostMapping(value = "/languages/update")
//    public String update(@Valid LanguageDTO languageDTO, Model model, HttpServletRequest request, BindingResult bindingResult) {
//        Integer id = Integer.valueOf(request.getParameter("id"));
//        Language searched = languageService.findByName(languageDTO.getName());
//        if (searched != null && !searched.getId().equals(id)) {
//            if (searched.getIsEnable()) {
//                bindingResult.rejectValue("name", "error.name", "Language name is already existed");
//            } else {
//                bindingResult.rejectValue("name", "error.name", "Language name is already existed but disabled");
//            }
//        }
//        if (languageDTO.getName().equals("")) {
//            bindingResult.rejectValue("name", "error.name", "Language name is required");
//        }
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("language", languageService.findById(id));
//            return "language/edit-language";
//        }
//        languageService.update(id, languageDTO.getName());
//        return "redirect:/dashboard/languages";
//    }

    @GetMapping(value = "/languages/add")
    public String create(Model model) {
        model.addAttribute("languageDTO", new LanguageDTO());
        return "language/add-language";
    }

//    @PostMapping(value = "/languages/add")
//    public String create(@Valid LanguageDTO languageDTO, Model model, BindingResult bindingResult) {
//        Language searched = languageService.findByName(languageDTO.getName());
//        if (searched != null) {
//            if (searched.getIsEnable()) {
//                bindingResult.rejectValue("name", "error.name", "Language name is already existed");
//            } else {
//                bindingResult.rejectValue("name", "error.name", "Language name is already existed but disabled");
//            }
//        }
//        if (languageDTO.getName().equals("")) {
//            bindingResult.rejectValue("name", "error.name", "Language name is required");
//        }
//        if (bindingResult.hasErrors()) {
//            return "language/add-language";
//        }
//        languageService.create(languageDTO);
//        return "redirect:/dashboard/languages";
//    }
}
