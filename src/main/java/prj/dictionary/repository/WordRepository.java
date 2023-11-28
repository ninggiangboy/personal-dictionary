package prj.dictionary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prj.dictionary.entity.Language;
import prj.dictionary.entity.User;
import prj.dictionary.entity.Word;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {

    Word findTopByCreatedByAndTerm(User user, String term);

    Word findByCreatedByAndWordId(User user, Integer wordId);

    Page<Word> findByCreatedBy(User user, Pageable pageable);

    Page<Word> findByCreatedByAndLanguage(User user, Language language, Pageable pageable);

    List<Word> findByCreatedBy(User user);

    List<Word> findByTermContains(String keyword);

    Page<Word> findByCreatedByAndTermLike(User user, String regex, Pageable pageable);

    Page<Word> findByCreatedByAndTermStartingWith(User user, String character, Pageable pageable);

    void deleteByCreatedByAndWordId(User user, Integer id);

    @Query(value = "SELECT TOP 1 * FROM words WHERE created_by = :user_id ORDER BY NEWID()", nativeQuery = true)
    Word findRandomWordAndCreatedBy(@Param("user_id") Integer userId);
}
