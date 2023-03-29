package SpringSecurity.SpringSecurityJWT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import SpringSecurity.SpringSecurityJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    
    private final UserRepository repository;


    // czyli gdyby nie funkcja anonimowa, musielibysmy stworzyc klase ktore implementuje
    // userDetailsService, nastepnie w niej nadpisac metode loadUserByUsername ?

    // Tak, dokładnie. Jeśli nie chcemy korzystać z klasy anonimowej, musielibyśmy 
    // stworzyć osobną klasę, która implementuje interfejs UserDetailsService, a następnie nadpisać w niej metodę loadUserByUsername().

    // Klasa anonimowa to klasa, która jest definiowana bez nazwy.
    // Zamiast nazwy, używa się słowa kluczowego new do utworzenia instancji klasy anonimowej.


    // @Bean
    // public UserDetailsService userDetailsService() {
    //     return new UserDetailsService() {
    //         @Override
    //         public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //             return null;
    //         }
    //     };
    // }


    // z tlambdy mozemy korzystac jedynie gdy interfejs ktory implementujemy posiaada jedna metode
    // jest to tak zwany interfejs funkcyjny
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    //this is a data accces object which is responsbile for fetching user details and also encode password
    @Bean
    public AuthenticationProvider authenticationProvider() {
        
        // it needs to know which loadUserByUsername to use
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());

        // provide password encoder
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
            return config.getAuthenticationManager();
    }
}
