package az.risk.library.service;

import az.risk.library.controller.dto.StudentDTO;
import az.risk.library.domain.StudentEntity;
import az.risk.library.repository.StudentRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final BookService bookService;

    public StudentService(StudentRepository studentRepository, BookService bookService) {
        this.studentRepository = studentRepository;
        this.bookService = bookService;
    }

    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

    public StudentEntity findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }

    public StudentEntity create(StudentEntity student) {
        return studentRepository.save(student);
    }

    public StudentEntity update(StudentEntity student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new IllegalArgumentException("Student not found: " + student.getId());
        }

        return studentRepository.save(student);
    }

    public void delete(Long id) {
        studentRepository.delete(findById(id));
    }

    public void addBookForStudent(Long studentId, Long bookId) {
        StudentEntity student = findById(studentId);
        student.addBook(bookService.findById(bookId));

        studentRepository.save(student);
    }

    public void deleteBookForStudent(Long studentId, Long bookId) {
        StudentEntity student = findById(studentId);
        student.removeBook(bookService.findById(bookId));

        studentRepository.save(student);
    }
    public List<StudentEntity> findByCriteria(StudentDTO studentDTO) {
        Specification<StudentEntity> specification = getSpecification(studentDTO);

        return studentRepository.findAll(specification);
    }

    private Specification<StudentEntity> getSpecification(StudentDTO studentDTO) {
        Specification<StudentEntity> specification = Specification.where(null);

        if (studentDTO.getId() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), studentDTO.getId()));
        }

        if (studentDTO.getPhoneNumber() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("phoneNumber"), studentDTO.getPhoneNumber() + "%"));
        }

        if (studentDTO.getFirstName() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                            studentDTO.getFirstName().toLowerCase() + "%"));
        }

        if (studentDTO.getLastName() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                            studentDTO.getLastName().toLowerCase() + "%"));
        }

        if (studentDTO.getDate() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("date"), studentDTO.getDate()));
        }

        if (studentDTO.getGender() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("gender"), studentDTO.getGender()));
        }


        return specification;
    }

}
