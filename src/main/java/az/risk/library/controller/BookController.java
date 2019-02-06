package az.risk.library.controller;

import az.risk.library.controller.dto.BookDTO;
import az.risk.library.controller.dto.BookSearchDTO;
import az.risk.library.domain.BookEntity;
import az.risk.library.service.BookService;
import com.remondis.remap.BidirectionalMapper;
import com.remondis.remap.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BidirectionalMapper<BookEntity, BookDTO> booksMapper;
    private final Mapper<BookEntity, BookSearchDTO> bookSearchMapper;

    public BookController(BookService bookService, BidirectionalMapper<BookEntity, BookDTO> booksMapper,
                          Mapper<BookEntity, BookSearchDTO> bookSearchMapper) {
        this.bookService = bookService;
        this.booksMapper = booksMapper;
        this.bookSearchMapper = bookSearchMapper;
    }

    @GetMapping
    List<BookDTO> findAll() {
        return bookService.findAll().stream()
                .map(booksMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    BookDTO findById(@PathVariable Long id) {
        return booksMapper.map(bookService.findById(id));
    }

    @PostMapping
    BookDTO create(@RequestBody BookDTO bookDTO) {
        BookEntity createdBook = bookService.create(booksMapper.mapFrom(bookDTO));

        return booksMapper.map(createdBook);
    }

    @PutMapping
    BookDTO update(@RequestBody BookDTO bookDTO) {
        BookEntity updatedBook = bookService.update(booksMapper.mapFrom(bookDTO));

        return booksMapper.map(updatedBook);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PostMapping("/search")
    List<BookSearchDTO> search(@RequestBody BookDTO bookDTO) {
        return bookService
                .findByCriteria(bookDTO)
                .stream()
                .map(bookSearchMapper::map)
                .collect(Collectors.toList());
    }

}
