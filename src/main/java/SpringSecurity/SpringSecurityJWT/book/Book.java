package SpringSecurity.SpringSecurityJWT.book;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false, name="title")
    private String title;
    @Column(nullable = false, name="author")
    private String author;
    @Column(nullable = false, name="available")
    private Integer available;
    @Column(nullable = false, name="available_at")
    @Builder.Default
    private String availableAt = "Warszawska 24";

    public Book(String title, String author, Integer available, String availableAt) {
        this.title = title;
        this.author = author;
        this.available = available;
        this.availableAt = availableAt;
    }

    public Book(String title, String author, Integer available) {
        this.title = title;
        this.author = author;
        this.available = available;
    }

}
