package prj.dictionary.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import prj.dictionary.validation.Email;

@Data
public class UpdateUserDTO {
    @NotEmpty(message = "Username is required")
    @Pattern(regexp = "^[^@]*$", message = "Username cannot contain @")
    private String username;

    @NotEmpty(message = "Full Name is required")
    private String fullName;

    @NotEmpty(message = "Email is required")
    @Email
    private String email;

    @NotEmpty(message = "Please confirm your password")
    private String confirmPassword;
}
