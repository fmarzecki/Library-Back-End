package SpringSecurity.SpringSecurityJWT.book;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    final private BookService service;

    // Returns list of book
    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(service.findByAvailable());
    }

    // Saves new book
    @PostMapping
    public ResponseEntity<Book> addBook
        (@RequestBody Book book)
    {
        return ResponseEntity.ok(service.save(book));
    }   
}
