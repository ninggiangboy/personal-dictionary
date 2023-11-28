package prj.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prj.dictionary.entity.Language;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByName(String name);

    List<Language> findAllByIsEnable(boolean isEnable);

    @Query(value = "SELECT DISTINCT l.* FROM words w JOIN languages l ON w.language_id = l.language_id WHERE w.created_by = :userId", nativeQuery = true)
    List<Language> findAllByUserId(Integer userId);
}
