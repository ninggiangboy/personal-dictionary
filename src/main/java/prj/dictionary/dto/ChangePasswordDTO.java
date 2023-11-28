package prj.dictionary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import prj.dictionary.validation.Password;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "Old password is required")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Password
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}
