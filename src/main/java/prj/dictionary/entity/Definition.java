package prj.dictionary.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "definitions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Definition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "definition_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_id", referencedColumnName = "word_id", nullable = false)
    @ToString.Exclude
    private Word word;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private Type type;

    @Column(name = "definition", columnDefinition = "nvarchar", length = 1000, nullable = false)
    private String definition;

    @Column(name = "example", columnDefinition = "nvarchar", length = 1000)
    private String example;
}
