package SpringSecurity.SpringSecurityJWT.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//we have to make it a filter, we want it to be active every time we get a request
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
        throws ServletException, IOException {
                
        // Filter implementation for user authentication with JWT

        // TODO: Extract JWT from HTTP request, extract information about user from JWT (email, roles) JwtService
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        
        
        // if a user exists but it is not authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //get user by userEmail from database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            //check if token is still valid
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // if the user is valid, upate securityContext and send send the request to our dispatchers
                // this object is needed by SpringSecurity to update our securityContext
                // TODO: Utworzenie obiektu Authentication i ustawienie w nim informacji o użytkowniku oraz jego rolach// 

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                    
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // TODO: Ustawienie obiektu Authentication w SecurityContextHolder
                // Upadate security Context Holder

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
