package SpringSecurity.SpringSecurityJWT.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import SpringSecurity.SpringSecurityJWT.token.TokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenRepository tokenRepository;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) {

    final String authHeader = request.getHeader("Authorization");
    final String jwt;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    jwt = authHeader.substring(7);

    var storedToken = tokenRepository.findByToken(jwt)
      .orElse(null);

    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);

      tokenRepository.save(storedToken);
      SecurityContextHolder.clearContext();

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      try {
        response.getWriter().write("{\"message\":\"Logout successful\"}");
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
