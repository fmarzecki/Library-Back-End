package SpringSecurity.SpringSecurityJWT.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // @Query("SELECT s FROM Student s WHERE s.email = ?1")
    // Optional<Student> findStudentByEmail(String email);

    Optional<User> findByEmail(String email);
}
