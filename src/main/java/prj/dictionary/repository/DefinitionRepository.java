package prj.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prj.dictionary.entity.Definition;
import prj.dictionary.entity.User;

@Repository
public interface DefinitionRepository extends JpaRepository<Definition, Integer> {
    Definition findByWordCreatedByAndId(User user, Integer id);

    @Modifying
    @Query(value = "DELETE FROM definitions WHERE word_id IN (SELECT word_id FROM words WHERE created_by = :userId) AND definition_id = :id", nativeQuery = true)
    void deleteByUserIdAndId(Integer userId, Integer id);
}
