package SpringSecurity.SpringSecurityJWT.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import SpringSecurity.SpringSecurityJWT.config.JwtService;
import SpringSecurity.SpringSecurityJWT.user.Role;
import SpringSecurity.SpringSecurityJWT.user.User;
import SpringSecurity.SpringSecurityJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    // Allows us to create user and save it to the database 
    public BasicResponse register(RegisterRequest request) {

        // Create user out of RegisterRequest object
        var user = User.builder()
            .email(request.getEmail())
            .lastname(request.getLastname())
            .firstname(request.getFirstname())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .build()
        ;

        repository.save(user);
        return BasicResponse.builder().response("Succes!").build();
    }

    // We'll use AuthenticationManager @bean that we created in AppConfig
    public AuthenticationRespnose authenticate(AuthenticationRequest request) {

        // Tries to authenticate, if failed, throws AuthenticationException
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );
        
        var user = repository.findByEmail(request.getEmail())
            .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationRespnose.builder()
            .token(jwtToken)
            .build();
    }
}
