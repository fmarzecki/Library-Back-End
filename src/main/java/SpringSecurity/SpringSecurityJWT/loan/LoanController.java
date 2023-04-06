package SpringSecurity.SpringSecurityJWT.loan;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import SpringSecurity.SpringSecurityJWT.user.User;
import SpringSecurity.SpringSecurityJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/loans")
public class LoanController {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @GetMapping()
    public List<Loan>  getUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username).orElseThrow();
        List<Loan> loans = loanRepository.findAllByStudentId(user.getId());

        return loans;
    }

}
