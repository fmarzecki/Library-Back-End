package SpringSecurity.SpringSecurityJWT.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Implementując interfejs UserDetails w klasie User, możemy dostarczyć informacje o użytkowniku,
// takie jak nazwa użytkownika, hasło, role i uprawnienia, do mechanizmu uwierzytelniania i autoryzacji w Spring Security.

// Klasa User reprezentuje użytkownika w systemie i zawiera jego podstawowe informacje,
// takie jak imię, nazwisko, adres e-mail, hasło i role. Implementując interfejs UserDetails,
// klasa User musi dostarczyć metod, które zwracają informacje o użytkowniku wymagane przez
// Spring Security, takie jak jego nazwę użytkownika, hasło, role i uprawnienia.

// Dzięki temu, Spring Security może wykonywać operacje uwierzytelnienia i autoryzacji
// na podstawie informacji o użytkowniku dostarczonych przez klasę User implementującą interfejs UserDetails.

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false, name="firstname")
    private String firstname;
    @Column(nullable = false, name="lastname")
    private String lastname;
    @JsonIgnore
    @Column(nullable = false, name="email")
    private String email;
    @JsonIgnore
    @Column(nullable = false, name="password")
    private String password;

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    // private List<Loan> loans;

    // @OneToMany(mappedBy = "user")
    // private List<Token> tokens;


    // when spring security start setup application it will use object called UserDetails
    // thats and interface that contains a bunch of methods

    @JsonIgnore
    // User can have only one role
    @Enumerated(EnumType.STRING)
    public Role role;

    // Ta metoda zwraca kolekcję obiektów typu GrantedAuthority, która reprezentuje role i uprawnienia przypisane do użytkownika
    // Wartość zwracana z tej metody określa, jakie operacje i zasoby są dostępne dla danego użytkownika w systemie.
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    
    public User(String firstname, String lastname, String email, String password, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }


}
