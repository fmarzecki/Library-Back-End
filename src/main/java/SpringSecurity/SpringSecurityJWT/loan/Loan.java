package SpringSecurity.SpringSecurityJWT.loan;

import java.sql.Date;

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
    @Column(name="book_id")
    private Integer id;

    @Column(nullable = false, name="date_borrowed")
    private Date dateBorrowed;

    @Column(nullable = false, name="date_due")
    private Date dateDue;
    
    @Column(nullable = false, name="returned")
    private boolean returned;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User user;

}
