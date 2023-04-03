package SpringSecurity.SpringSecurityJWT.loan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
 

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    @Query("SELECT l FROM Loan l WHERE l.user.id = ?1")
    List<Loan> findAllByStudentId(Integer id);


    @Query("SELECT l FROM Loan l WHERE l.book.id = ?1")
    List<Loan> findAllByBookId(Integer id);
}

