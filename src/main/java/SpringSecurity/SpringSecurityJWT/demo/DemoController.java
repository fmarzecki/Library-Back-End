package SpringSecurity.SpringSecurityJWT.demo;


import java.time.LocalDate;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringSecurity.SpringSecurityJWT.auth.BasicResponse;
import SpringSecurity.SpringSecurityJWT.book.Book;
import SpringSecurity.SpringSecurityJWT.book.BookRepository;
import SpringSecurity.SpringSecurityJWT.loan.Loan;
import SpringSecurity.SpringSecurityJWT.loan.LoanRepository;
import SpringSecurity.SpringSecurityJWT.user.Role;
import SpringSecurity.SpringSecurityJWT.user.User;
import SpringSecurity.SpringSecurityJWT.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class DemoController {
    
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoanRepository loanRepository;

    @PostConstruct
    public void addBooks() {
        User user =  new User("Filip", "Marzecki", "@icloud.com", passwordEncoder.encode("test123"), Role.ROLE_ADMIN);

        Book book =  new Book("The Catcher in the Rye", "J.D. Salinger", 4);
        Book book1 =  new Book("To Kill a Mockingbird", "Harper Lee", 1);
        Book book2 =  new Book("1984", "George Orwell", 8);
        Book book3 =  new Book("Pride and Prejudice", "Jane Austen", 0);

        bookRepository.saveAll(List.of(book, book1, book2, book3));

        Loan loan1 = new Loan(
            LocalDate.parse("2023-03-14"),
            LocalDate.parse("2023-03-30"),
            false,
            user,
            book1);
            
        Loan loan2 = new Loan(
            LocalDate.parse("2013-03-14"),
            LocalDate.parse("2014-03-30"),
            true,
            user,
            book2);
            
        Loan loan3 = new Loan(
            LocalDate.parse("2020-03-14"),
            LocalDate.parse("2017-03-30"),
            false,
            user,
            book2);
            
    
        user.setLoans(List.of(loan1, loan2, loan3));
        userRepository.save(user);
    }


    @GetMapping()
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("This endpoint is secured, how did you get gere!?");
    }

    @GetMapping("/authCheck")
    public BasicResponse sayHello2() {
        BasicResponse response = new BasicResponse("Authenticated");
        return response;
    }

    @GetMapping("/loans")
    public List<Loan>  getUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username).orElseThrow();
        List<Loan> loans = loanRepository.findAllByStudentId(user.getId());

        return loans;
    }

    @GetMapping("/allLoans")
    public List<Loan> getLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans;
    }

    @GetMapping("/bookLoanHistory")
    public List<Loan> getLoanHistory() {
        Book book = bookRepository.findById(3).orElseThrow();
        List<Loan> loans = loanRepository.findAllByBookId(book.getId());
        return loans;
    }
}
