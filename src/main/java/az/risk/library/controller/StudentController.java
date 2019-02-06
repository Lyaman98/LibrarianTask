package az.risk.library.controller;

import az.risk.library.controller.dto.StudentDTO;
import az.risk.library.controller.dto.StudentSearchDTO;
import az.risk.library.domain.StudentEntity;
import az.risk.library.service.StudentService;
import com.remondis.remap.BidirectionalMapper;
import com.remondis.remap.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final BidirectionalMapper<StudentEntity, StudentDTO> studentsMapper;
    private final Mapper<StudentEntity, StudentSearchDTO> studentSearchMapper;

    public StudentController(StudentService studentService, BidirectionalMapper<StudentEntity, StudentDTO> studentsMapper,
                             Mapper<StudentEntity, StudentSearchDTO> studentSearchMapper) {
        this.studentService = studentService;
        this.studentsMapper = studentsMapper;
        this.studentSearchMapper = studentSearchMapper;
    }

    @GetMapping
    List<StudentDTO> findAll() {
        return studentService.findAll().stream()
                .map(studentsMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    StudentDTO findById(@PathVariable Long id) {
        return studentsMapper.map(studentService.findById(id));
    }

    @PostMapping
    StudentDTO create(@RequestBody StudentDTO studentDTO) {
        StudentEntity createdStudent = studentService.create(studentsMapper.mapFrom(studentDTO));

        return studentsMapper.map(createdStudent);
    }

    @PutMapping
    StudentDTO update(@RequestBody StudentDTO studentDTO) {
        StudentEntity updatedStudent = studentService.update(studentsMapper.mapFrom(studentDTO));

        return studentsMapper.map(updatedStudent);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        studentService.delete(id);
    }

    @PostMapping("/search")
    List<StudentSearchDTO> search(@RequestBody StudentDTO studentDTO) {
        return studentService.findByCriteria(studentDTO).stream()
                .map(studentSearchMapper::map)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/book/{bookId}")
    void addBook(@PathVariable Long id, @PathVariable Long bookId) {
        studentService.addBookForStudent(id, bookId);
    }


    @DeleteMapping("/{id}/book/{bookId}")
    void deleteBook(@PathVariable Long id, @PathVariable Long bookId) {
        studentService.deleteBookForStudent(id, bookId);
    }

}
