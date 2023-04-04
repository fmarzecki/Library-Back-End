package SpringSecurity.SpringSecurityJWT.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import SpringSecurity.SpringSecurityJWT.token.TokenRepository;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    // Filter implementation for user authentication with JWT
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
        throws ServletException, IOException {
                
        // Extract JWT from HTTP request, extract information about user from JWT (email, roles) JwtService
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        
        // If a user exists but it is not authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Get user by userEmail from database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Check if token is not expired or revoked
            var isTokenValid = tokenRepository.findByToken(jwt)
            .map(t -> !t.isExpired() && !t.isRevoked())
            .orElse(false);

            // Check if token is still valid
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {

                // Create Authentication object and set information about user and user roles
                // Needed by SpringSecurity to update SecurityContex
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                    
                // WebAuthentication contains information about IP addres and browser
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                /*  
                    Set Authentication object in SecurityContextHolder
                    It stores information about security context of current thread
                    i.e users name, roles
                */  
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
