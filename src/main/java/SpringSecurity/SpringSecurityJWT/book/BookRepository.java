package SpringSecurity.SpringSecurityJWT.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b ORDER BY CASE WHEN b.available > 0 THEN 0 ELSE 1 END")
    List<Book> findByAvailable();
}
