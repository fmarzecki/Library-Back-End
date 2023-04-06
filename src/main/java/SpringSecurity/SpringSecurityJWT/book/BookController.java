package SpringSecurity.SpringSecurityJWT.book;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService service;

    @GetMapping
    public List<Book> findAll() {
        return service.findByAvailable();
    }
     
}
