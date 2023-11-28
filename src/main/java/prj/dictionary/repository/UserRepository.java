package prj.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prj.dictionary.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByUsernameOrEmail(String username, String Email);

    @Query(value = "SELECT u.* FROM users u JOIN user_role ur ON u.user_id = ur.user_id JOIN roles r ON ur.role_id = r.role_id WHERE u.user_id NOT IN (SELECT ur.user_id FROM user_role ur JOIN roles r ON ur.role_id = r.role_id WHERE r.role_name IN ('ADMIN', 'SUPER_ADMIN')) AND (u.username = :search OR u.email = :search);", nativeQuery = true)
    User findOnlyUserByUsernameOrEmail(String search);

    @Query(value = "SELECT u.* FROM users u JOIN user_role ur ON u.user_id = ur.user_id JOIN roles r ON ur.role_id = r.role_id WHERE u.user_id NOT IN (SELECT ur.user_id FROM user_role ur JOIN roles r ON ur.role_id = r.role_id WHERE r.role_name IN ('SUPER_ADMIN')) AND (u.username = :search OR u.email = :search);", nativeQuery = true)
    User findUserAndAdminByUsernameOrEmail(String search);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
