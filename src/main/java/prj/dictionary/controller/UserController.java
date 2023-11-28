package prj.dictionary.controller;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import prj.dictionary.dto.ChangePasswordDTO;
import prj.dictionary.dto.ResetPasswordDTO;
import prj.dictionary.dto.SignUpUserDTO;
import prj.dictionary.dto.UpdateUserDTO;
import prj.dictionary.entity.User;
import prj.dictionary.model.CustomUserDetails;
import prj.dictionary.service.EmailService;
import prj.dictionary.service.StorageService;
import prj.dictionary.service.UserService;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class UserController {

    @Autowired<form th:action="@{/add-doctor}" method="post" th:object="${newDoctorDTO}">
    <div class="row">
        <div class="col-sm-6">
            <div class="form-group">
    <label>Tên đầy đủ <span class="text-danger">*</span></label>
                <input class="form-control" name="fullName" type="text" th:field="*{fullName}">
                <span th:if="${#fields.hasErrors('fullName')}" th:errors="*{fullName}" class="text-danger"></span>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
    <label>ID nhân viên <span class="text-danger">*</span></label>
                <input class="form-control" name="userId" type="text" th:field="*{userId}">
                <span th:if="${#fields.hasErrors('userId')}" th:errors="*{userId}" class="text-danger"></span>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
    <label>Ngày sinh <span class="text-danger">*</span></label>
                <div class="cal-icon">
                    <input type="text" class="form-control datetimepicker" name="dateOfBirth" th:field="*{dateOfBirth}">
                </div>
                <span th:if="${#fields.hasErrors('dateOfBirth')}" th:errors="*{dateOfBirth}" class="text-danger"></span>
            </div>
        </div>
        <div class="col-sm-6 col-md-6 col-lg-3">
            <div class="form-group">
    <label>Phòng ban <span class="text-danger">*</span></label>
                <select class="form-control select" name="departmentId" th:field="*{departmentId}">
                    <option th:each="department : ${departments}" th:value="${department.id}" th:text="${department.name}"></option>
                </select>
                <span th:if="${#fields.hasErrors('departmentId')}" th:errors="*{departmentId}" class="text-danger"></span>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
    <label>Ảnh đại diện <span class="text-danger">*</span></label>
                <div class="profile-upload">
                    <div class="upload-img">
                        <img alt="" th:src="@{/dashboard/img/user.jpg}">
                    </div>
                    <div class="upload-input">
                        <input type="file" class="form-control" name="profileImage" th:field="*{profileImage}">
                    </div>
                    <span th:if="${#fields.hasErrors('profileImage')}" th:errors="*{profileImage}" class="text-danger"></span>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group gender-select">
                <label class="gen-label">Giới tính <span class="text-danger">*</span></label>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" name="gender" checked class="form-check-input" th:field="*{gender}" value="Nam">Nam
            </label>
                </div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" name="gender" class="form-check-input" th:field="*{gender}" value="Nữ">Nữ
            </label>
                </div>
                <span th:if="${#fields.hasErrors('gender')}" th:errors="*{gender}" class="text-danger"></span>
            </div>
        </div>
    </div>
    <div class="form-group">
    <label>Short Biography</label>
        <textarea class="form-control" rows="3" cols="30" name="shortBiography" th:field="*{shortBiography}"></textarea>
        <span th:if="${#fields.hasErrors('shortBiography')}" th:errors="*{shortBiography}" class="text-danger"></span>
    </div>
    <div class="form-group">
        <label class="display-block">Trạng thái <span class="text-danger">*</span></label>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" id="product_active" name="activeStatus" value="true" checked th:field="*{activeStatus}">
            <label class="form-check-label" for="product_active">
    Hoạt động
            </label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" id="product_inactive" name="activeStatus" value="false" th:field="*{activeStatus}">
            <label class="form-check-label" for="product_inactive">
    Không hoạt động
            </label>
        </div>
        <span th:if="${#fields.hasErrors('activeStatus')}" th:errors="*{activeStatus}" class="text-danger"></span>
    </div>
    <div class="m-t-20 text-center">
        <button class="btn btn-primary submit-btn">Create Doctor</button>
    </div>
</form>

    UserService userService;

    @Autowired
    StorageService storageService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailService emailService;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @GetMapping("/login")
    public String getLoginFrom(Model model, HttpServletRequest request) {
        String username = request.getParameter("username");
        model.addAttribute("usernameLogin", username);
        return "user/login";
    }

    @GetMapping("/signup")
    public String getSignupForm(Model model) {
        model.addAttribute("signUpUserDTO", new SignUpUserDTO());
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupUser(@Valid SignUpUserDTO signUpUserDTO, BindingResult bindingResult) {
        if (userService.existsByUsername(signUpUserDTO.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username is already taken");
        }

        if (userService.existsByEmail(signUpUserDTO.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Email is already taken");
        }

        if (bindingResult.hasErrors()) {
            return "user/signup";
        }

        userService.createUser(signUpUserDTO);
        return "redirect:/home";
    }

    @GetMapping("/user")
    public String getUserProfile(HttpServletRequest request, Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        if (request.getParameter("edit") != null) {
            model.addAttribute("updateUserDTO", new UpdateUserDTO());
            return "user/edit-profile";
        }
        if (request.getParameter("password") != null) {
            model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
            return "user/change-password";
        }
        return "user/user";
    }

    @GetMapping("/reset-password")
    public String resetPassword(Model model) {
        model.addAttribute("resetPasswordDTO", new ResetPasswordDTO());
        return "user/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String email = resetPasswordDTO.getEmail();
        User user = userService.findByEmail(email);
        if (user == null) {
            bindingResult.rejectValue("email", "error.email", "Email does not match any account");
        }
        if (bindingResult.hasErrors()) {
            return "user/reset-password";
        }

        String fullName = user.getFullName();
        String time = new Date().toString();
        String newPassword = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 15);

        Context context = new Context();
        context.setVariable("fullName", fullName);
        context.setVariable("time", time);
        context.setVariable("password", newPassword);
        String html = templateEngine.process("email/password-changed-email.html", context);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(() -> {
            try {
                emailService.send("Reset Password", html, user.getEmail(), true);
                userService.resetPassword(newPassword, user);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        redirectAttributes.addFlashAttribute("usernameLogin", email);
        return "redirect:/login";
    }

    @PostMapping("/update-user")
    public String updateUser(@Valid UpdateUserDTO updateUserDTO,
                             BindingResult bindingResult,
                             Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        model.addAttribute("user", currentUser);

        String newUsername = updateUserDTO.getUsername();
        String oldUsername = currentUser.getUsername();

        if (!updateUserDTO.getConfirmPassword().isBlank()) {
            if (!encoder.matches(updateUserDTO.getConfirmPassword(),
                    currentUser.getPassword())) {
                bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Incorrect password");
            } else {
                if (!oldUsername.equals(newUsername) && userService.existsByUsername(newUsername)) {
                    bindingResult.rejectValue("username", "error.username", "Username is already taken");
                }

                String newEmail = updateUserDTO.getEmail();
                if (!currentUser.getEmail().equals(newEmail) && userService.existsByEmail(newEmail)) {
                    bindingResult.rejectValue("email", "error.email", "Email is already taken");
                }
            }
        }

        if (bindingResult.hasErrors()) {
            return "user/edit-profile";
        }

        userService.update(updateUserDTO, currentUser);

        if (!oldUsername.equals(newUsername)) {
            CustomUserDetails updatedUserDetails = new CustomUserDetails(currentUser);

            UsernamePasswordAuthenticationToken updatedAuthentication =
                    new UsernamePasswordAuthenticationToken(updatedUserDetails, authentication.getCredentials(),
                            updatedUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
        }

        return "redirect:/user";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid ChangePasswordDTO changePasswordDTO, BindingResult bindingResult,
                                 Authentication authentication, HttpServletRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        String oldPassword = changePasswordDTO.getOldPassword();
        String newPassword = changePasswordDTO.getNewPassword();
        String confirmPassword = changePasswordDTO.getConfirmPassword();

        if (!oldPassword.isBlank() && !encoder.matches(oldPassword, currentUser.getPassword())) {
            bindingResult.rejectValue("oldPassword", "error.oldPassword", "Incorrect password");
        }

        if (!bindingResult.hasErrors()) {
            if (newPassword.equals(oldPassword)) {
                bindingResult.rejectValue("newPassword", "error.newPassword", "New password must be different");
            } else if (!confirmPassword.equals(newPassword)) {
                bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Confirm Password does not match");
            }
        }

        if (bindingResult.hasErrors()) {
            return "user/change-password";
        }

        userService.changePassword(changePasswordDTO, currentUser);

        request.getSession().invalidate();

        return "redirect:/user";
    }

    @PostMapping("/change-image")
    public String uploadImage(@RequestParam("image") MultipartFile image, Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        try {
            String fileName = storageService.save(image);
            userService.changeImage(fileName, user);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorUpload", e.getMessage());
        }
        return "redirect:/user";
    }

    @GetMapping("/user/switch-status")
    public String disableUser(@RequestParam Integer id) {
        User user = userService.findById(id);
        userService.switchStatus(user);
        return "redirect:/dashboard/users?username=" + user.getUsername();
    }

    @GetMapping("/user/switch-role")
    public String switchRole(@RequestParam Integer id) {
        User user = userService.findById(id);
        userService.switchRole(user);
        return "redirect:/dashboard/users?username=" + user.getUsername();
    }
}
