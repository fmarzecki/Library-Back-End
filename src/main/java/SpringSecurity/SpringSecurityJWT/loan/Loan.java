package SpringSecurity.SpringSecurityJWT.loan;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import SpringSecurity.SpringSecurityJWT.book.Book;
import SpringSecurity.SpringSecurityJWT.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue
    @Column(name="loan_id")
    private Integer id;

    @Column(nullable = false, name="date_borrowed")
    private LocalDate dateBorrowed;

    @Column(nullable = false, name="date_due")
    private LocalDate dateDue;
    
    @Column(nullable = false, name="returned")
    private boolean returned;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public Loan(LocalDate dateBorrowed, LocalDate dateDue, boolean returned, User user, Book book) {
        this.dateBorrowed = dateBorrowed;
        this.dateDue = dateDue;
        this.returned = returned;
        this.user = user;
        this.book = book;
    }

}
