package az.risk.library.service;

import az.risk.library.domain.BookEntity;
import az.risk.library.repository.BookRepository;
import az.risk.library.controller.dto.BookDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    public BookEntity findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
    }

    public BookEntity create(BookEntity book) {

        //checking if the book already exists
        if (bookRepository.findByIsbn(book.getIsbn()) != null){
            throw new IllegalArgumentException("Book already exists " + book.getIsbn());
        }
        return bookRepository.save(book);
    }

    public BookEntity update(BookEntity book) {
        if (!bookRepository.existsById(book.getId())) {
            throw new IllegalArgumentException("Book not found " + book.getId());
        }

        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookEntity> findByCriteria(BookDTO bookDTO) {
        Specification<BookEntity> specification = createSpecification(bookDTO);

        return bookRepository.findAll(specification);
    }

    private Specification<BookEntity> createSpecification(BookDTO bookDTO) {
        Specification<BookEntity> specification = Specification.where(null);

        if (bookDTO.getId() != null) {
            specification = specification.and(((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), bookDTO.getId())));
        }

        if (bookDTO.getQuantity() != 0) {
            specification = specification.and(((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("quantity"), bookDTO.getQuantity())));
        }

        if (bookDTO.getBookName() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("bookName")),
                            bookDTO.getBookName().toLowerCase().trim() + "%"));
        }

        if (bookDTO.getAuthor() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("author")),
                            bookDTO.getAuthor().toLowerCase().trim() + "%"));
        }

        if (bookDTO.getPublisher() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("publisher")),
                            bookDTO.getPublisher().toLowerCase().trim() + "%"));
        }


        if (bookDTO.getPublishedDate() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("publishedDate"), bookDTO.getPublishedDate()));
        }

        return specification;

    }


}
