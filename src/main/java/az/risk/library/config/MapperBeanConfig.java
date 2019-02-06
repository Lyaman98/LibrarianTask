package az.risk.library.config;

import az.risk.library.controller.dto.BookDTO;
import az.risk.library.controller.dto.BookSearchDTO;
import az.risk.library.controller.dto.StudentDTO;
import az.risk.library.controller.dto.StudentSearchDTO;
import az.risk.library.domain.BookEntity;
import az.risk.library.domain.StudentEntity;
import com.remondis.remap.BidirectionalMapper;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperBeanConfig {

    @Bean
    BidirectionalMapper<StudentEntity, StudentDTO> studentMappers() {
        return BidirectionalMapper.of(studentMapper(), studentDTOMapper());
    }

    @Bean
    BidirectionalMapper<BookEntity, BookDTO> bookMappers() {
        return BidirectionalMapper.of(bookMapper(), bookDTOMapper());
    }

    @Bean
    Mapper<StudentEntity, StudentDTO> studentMapper() {
        return Mapping
                .from(StudentEntity.class)
                .to(StudentDTO.class)
                .omitInSource(StudentEntity::getBooks)
                .mapper();
    }

    @Bean
    Mapper<StudentDTO, StudentEntity> studentDTOMapper() {
        return Mapping
                .from(StudentDTO.class)
                .to(StudentEntity.class)
                .omitInDestination(StudentEntity::getBooks)
                .mapper();
    }

    @Bean
    Mapper<BookEntity, BookDTO> bookMapper() {
        return Mapping
                .from(BookEntity.class)
                .to(BookDTO.class)
                .omitInSource(BookEntity::getStudents)
                .mapper();
    }

    @Bean
    Mapper<BookDTO, BookEntity> bookDTOMapper() {
        return Mapping
                .from(BookDTO.class)
                .to(BookEntity.class)
                .omitInDestination(BookEntity::getStudents)
                .mapper();
    }

    @Bean
    Mapper<BookEntity, BookSearchDTO> bookSearch() {
        return Mapping
                .from(BookEntity.class)
                .to(BookSearchDTO.class)
                .useMapper(studentMapper())
                .mapper();
    }

    @Bean
    Mapper<StudentEntity, StudentSearchDTO> studentSearch() {
        return Mapping
                .from(StudentEntity.class)
                .to(StudentSearchDTO.class)
                .useMapper(bookMapper())
                .mapper();
    }
}
