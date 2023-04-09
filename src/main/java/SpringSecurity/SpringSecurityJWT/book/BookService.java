package SpringSecurity.SpringSecurityJWT.book;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    
    final private BookRepository repository; 

    public Book save(Book book) {
        repository.save(book);
        return book;
    }

    public void saveAll(List<Book> books) {
        repository.saveAll(books);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public List<Book> findByAvailable() {
        return repository.findByAvailable();
    }

}
