package SpringSecurity.SpringSecurityJWT.user;

import SpringSecurity.SpringSecurityJWT.loan.Loan;
import SpringSecurity.SpringSecurityJWT.loan.LoanRepository;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;
    final private LoanRepository loanRepository;

    public UserRequest getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow();
        List<Loan> loans = loanRepository.findAllByStudentId(user.getId());

        UserRequest userRequest = UserRequest
            .builder()
            .lastname(user.getLastname())
            .firstname(user.getFirstname())
            .booksBorrowed(loans.size())
            .enabled(user.isEnabled())
            .role(user.getRole())
            .build();

        return userRequest; 
    }
}
