package SpringSecurity.SpringSecurityJWT.loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import SpringSecurity.SpringSecurityJWT.auth.BasicResponse;
import SpringSecurity.SpringSecurityJWT.book.Book;
import SpringSecurity.SpringSecurityJWT.book.BookRepository;
import SpringSecurity.SpringSecurityJWT.book.BookIdRequest;
import SpringSecurity.SpringSecurityJWT.exception.EmailAlreadyTakenException;
import SpringSecurity.SpringSecurityJWT.user.User;
import SpringSecurity.SpringSecurityJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/loans")
public class LoanController {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @GetMapping()
    public List<Loan> getLoans() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username).orElseThrow();
        List<Loan> loans = loanRepository.findAllByStudentId(user.getId());

        return loans;
    }

    @PostMapping()
    public BasicResponse addLoan(@RequestBody BookIdRequest bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username).orElseThrow();
        Book book = bookRepository.findById(bookId.getId()).orElseThrow();
        List<Loan> loan = loanRepository.findByUserIdAndBookId(user.getId(), book.getId());

        if (book.getAvailable() <= 0) {
            throw new IllegalStateException("Books not available");
        }
        
        int size = loan.size();

        if(size > 0 && LocalDate.now().isBefore(loan.get(size -1).getDateDue())) {
            throw new EmailAlreadyTakenException("Loan already borrowed");
        }

        Loan newLoan = new Loan(
            LocalDate.now().plusDays(1),
            LocalDate.now().plusDays(30),
            false,
            user,
            book
        );

        book.setAvailable(book.getAvailable()-1);
        bookRepository.save(book);
        loanRepository.save(newLoan);
        return new BasicResponse("Loan added");
    }

}
