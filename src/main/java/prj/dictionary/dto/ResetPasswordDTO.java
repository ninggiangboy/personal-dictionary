package prj.dictionary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import prj.dictionary.validation.Email;

@Data
public class ResetPasswordDTO {
    @NotBlank(message = "Email is required")
    @Email
    private String email;
}
