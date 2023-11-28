package prj.dictionary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import prj.dictionary.entity.Language;

@Data
public class UpdateWordDTO {
    @NotBlank(message = "Term is required")
    private String term;
    @NotBlank(message = "Pronunciation is required")
    private String pronunciation;
    @NotNull(message = "Language is required")
    private Language language;
}
