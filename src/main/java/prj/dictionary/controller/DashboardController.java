package prj.dictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prj.dictionary.entity.Language;
import prj.dictionary.entity.User;
import prj.dictionary.model.CustomUserDetails;
import prj.dictionary.service.LanguageService;
import prj.dictionary.service.UserService;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private LanguageService languageService;

    @GetMapping(value = "/dashboard")
    public String dashboard() {
        return "dashboard/dashboard";
    }

    @GetMapping(value = "/dashboard/users")
    public String search(@RequestParam(required = false) String username, Model model, Authentication authentication) {
        if (username == null || username.isEmpty()) {
            return "dashboard/user-manager";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("admin", userDetails);
        User userSearched;
        if (username.equals(userDetails.getUsername())) {
            model.addAttribute("yourself", true);
            return "dashboard/user-manager";
        }
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"))) {
            userSearched = userService.findUserAndAdmin(username);
        } else {
            userSearched = userService.findUserOnly(username);
        }
        model.addAttribute("userSearched", userSearched);
        return "dashboard/user-manager";
    }

    @GetMapping(value = "/dashboard/languages")
    public String languages(Model model) {
        List<Language> languages = languageService.findAllAvailable();
        model.addAttribute("languages", languages);
        return "dashboard/language-manager";
    }
}
