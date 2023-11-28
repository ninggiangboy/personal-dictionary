package prj.dictionary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "words")
@Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Integer wordId;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    @ToString.Exclude
    private User createdBy;

    @Column(name = "term", columnDefinition = "nvarchar", length = 255, nullable = false)
    private String term;

    @Column(name = "pronunciation", columnDefinition = "nvarchar", length = 255)
    private String pronunciation;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "language_id")
    private Language language;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "word", cascade = CascadeType.ALL)
    private List<Definition> definitions = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "editable")
    private Boolean editable;

    @PrePersist
    public void prePersist() {
        editable = true;
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    public String getStringDefinitions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Definition definition : definitions) {
            stringBuilder.append(definition.getDefinition()).append("\n");
        }
        return stringBuilder.toString();
    }

    public String getStringExamples() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Definition definition : definitions) {
            if (definition.getExample() != null) {
                stringBuilder.append(definition.getExample()).append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
