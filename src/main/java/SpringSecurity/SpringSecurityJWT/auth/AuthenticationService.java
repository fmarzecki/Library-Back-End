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

    // allows us to create user, save it to the database and generate token out of it  
    public BasicResponse register(RegisterRequest request) {
        //create user out of RegisterRequest object
        var user = User.builder()
            .email(request.getEmail())
            .lastname(request.getLastname())
            .firstname(request.getFirstname())
            // encode password before saving it to database
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .build()
        ;

        repository.save(user);

        return BasicResponse.builder().response("Succes!").build();
    }

    // We'll use AuthenticationManager @bean that we created in AppConfig
    public AuthenticationRespnose authenticate(AuthenticationRequest request) {

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
