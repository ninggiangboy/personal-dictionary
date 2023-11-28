package prj.dictionary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import prj.dictionary.validation.Email;
import prj.dictionary.validation.Password;

@Data
public class SignUpUserDTO {
    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[^@]*$", message = "Username cannot contain @")
    private String username;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @Password
    private String password;
}
