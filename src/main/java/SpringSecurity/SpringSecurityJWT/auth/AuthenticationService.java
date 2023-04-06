package SpringSecurity.SpringSecurityJWT.auth;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import SpringSecurity.SpringSecurityJWT.config.JwtService;
import SpringSecurity.SpringSecurityJWT.exception.EmailAlreadyTakenException;
import SpringSecurity.SpringSecurityJWT.token.Token;
import SpringSecurity.SpringSecurityJWT.token.TokenRepository;
import SpringSecurity.SpringSecurityJWT.token.TokenType;
import SpringSecurity.SpringSecurityJWT.user.Role;
import SpringSecurity.SpringSecurityJWT.user.User;
import SpringSecurity.SpringSecurityJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Allows us to create user and save it to the database 
    public BasicResponse register(RegisterRequest request) {

      Optional<User> optionalUser = repository.findByEmail(request.getEmail());
      if ( optionalUser.isPresent() ) {
        throw new EmailAlreadyTakenException("Email already Taken");
      }
        // Create user out of RegisterRequest object
        var user = User.builder()
            .email(request.getEmail())
            .lastname(request.getLastname())
            .firstname(request.getFirstname())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .build()
        ;

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
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

        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationRespnose.builder()
            .token(jwtToken)
            .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
        tokenRepository.save(token);
      }
    
      private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
          return;
        validUserTokens.forEach(token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
      }
}
