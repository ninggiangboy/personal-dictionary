package prj.dictionary.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import prj.dictionary.entity.Language;

@Data
public class NewWordDTO {
    @NotBlank(message = "Term is required")
    private String term;
    private String pronunciation;
    @NotNull(message = "Language is required")
    private Language language;
    @Valid
    private DefinitionDTO firstDefinition;
}
