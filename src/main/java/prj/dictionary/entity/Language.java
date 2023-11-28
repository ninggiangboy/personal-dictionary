package prj.dictionary.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "languages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Integer id;

    @Column(name = "language_name", nullable = false, unique = true)
    private String name;
}
