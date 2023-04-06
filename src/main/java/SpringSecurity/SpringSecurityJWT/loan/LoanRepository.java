package SpringSecurity.SpringSecurityJWT.loan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SpringSecurity.SpringSecurityJWT.book.Book;
 

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    // SQL - "SELECT * FROM loan l
    //        JOIN book b ON l.book_id = b.id WHERE l.student_id = ?1"

    @Query("SELECT l FROM Loan l JOIN FETCH l.book b WHERE l.user.id = ?1")
    List<Loan> findAllByStudentId(Integer id);

    Loan findByBookAndUser(Book book,);

}

