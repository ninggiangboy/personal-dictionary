package prj.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj.dictionary.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
}
