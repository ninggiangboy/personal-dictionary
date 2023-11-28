package prj.dictionary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import prj.dictionary.entity.Type;

@Data
public class DefinitionDTO {
    @NotNull(message = "Type is required")
    private Type type;
    @NotBlank(message = "Definition is required")
    private String definition;
    private String example;
}
