package SpringSecurity.SpringSecurityJWT.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    //at the startup SpringSecurity will try to look for a Bean of type security
    // filter chain that is responsible for configuring all the HTTP security of 
    // our application 

    final private JwtAuthenticationFilter jwtAuthFilter;
    final private AuthenticationProvider AuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{

        // in here we can choose what are the URLs that we want to secure
        // for example when loggin or regestering
        // create whitelist
        http
            .cors()
            .disable()
            .csrf()
            .disable()
            .authorizeHttpRequests()
            // .requestMatchers("/test/admin").hasRole("USER")
            // permit all ednpoints in string list
            .requestMatchers("/api/v1/auth/**")
            .permitAll()
            // all the other request require authentication
            .anyRequest()
            .authenticated()
            // configure session management
            // the session should be stateless because we wanted 
            // every request to be authenticated
            // so the session state should not be stored (it should be stateless)
            // ".and()" - adds a new config
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // i need to tell spring which authentication provider I want to use
            .authenticationProvider(AuthenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     configuration.setAllowedOrigins(Arrays.asList("*"));
    //     configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    //     configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
    //     configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;
    // }
    
}
