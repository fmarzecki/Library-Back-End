package SpringSecurity.SpringSecurityJWT.book;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService service;

    @PostConstruct
    public void addBooks() {
        Book book =  new Book( 1, "The Catcher in the Rye", "J.D. Salinger", true);
        Book book1 =  new Book( 2, "To Kill a Mockingbird", "Harper Lee", false);
        Book book2 =  new Book( 3, "1984", "George Orwell", true);
        Book book3 =  new Book( 4, "Pride and Prejudice", "Jane Austen", false);
 
        service.saveAll(List.of(book, book1, book2, book3));
    }
    
    @GetMapping
    public List<Book> findAll() {
        return service.findByOrderByAvailableDesc();
    }
    

    
}
