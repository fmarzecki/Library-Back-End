package SpringSecurity.SpringSecurityJWT.demo;


import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

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
        userRepository.save(user);

        Book book =  new Book("The Catcher in the Rye", "J.D. Salinger", 4, "Warszawska 24");
        Book book1 =  new Book("To Kill a Mockingbird", "Harper Lee", 1, "Pawia 44");
        Book book2 =  new Book("1984", "George Orwell", 8, "Warszawska 24");
        Book book3 =  new Book("Pride and Prejudice", "Jane Austen", 0, "Warszawska 24");
        Book b1 = new Book("Animal Farm", "George Orwell", 1, "Pawia 44");
        Book b2 = new Book("Brave New World", "Aldous Huxley", 4, "Warszawska 24");
        Book b3 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 6, "Pawia 44");
        Book b4 = new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 0, "Warszawska 24");
        Book b6 = new Book("The Name of the Wind", "Patrick Rothfuss", 5, "Pawia 44");
        Book b5 = new Book("The Road", "Cormac McCarthy", 2, "Pawia 44");
        Book b7 = new Book("The Girl with the Dragon Tattoo", "Stieg Larsson", 1, "Warszawska 24");
        Book b8 = new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 3, "Pawia 44");
        Book b9 = new Book("The Handmaid's Tale", "Margaret Atwood", 0, "Warszawska 24");
        bookRepository.saveAll(List.of(book, book1, book2, book3, b1,b2,b3,b4,b5,b6,b7,b8,b9));

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
            book3);
            
    
        loanRepository.saveAll(List.of(loan1, loan2, loan3));
        
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


}
