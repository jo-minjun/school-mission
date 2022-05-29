package jun.schoolmission.controller;

import jun.schoolmission.common.ResponseMessage;
import jun.schoolmission.domain.dto.StudentDto;
import jun.schoolmission.domain.dto.StudentInputDto;
import jun.schoolmission.domain.dto.StudentOutputDto;
import jun.schoolmission.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<ResponseMessage<?>> createStudent(@RequestBody @Valid StudentInputDto studentInputDto) {
        Long id = studentService.createStudent(studentInputDto.getStudent());
        return ResponseEntity.created(URI.create("/students/" + id)).body(new ResponseMessage<>().success(null));
    }

    @GetMapping
    public ResponseEntity<ResponseMessage<?>> searchStudents() {
        List<StudentDto> studentDtos = studentService.searchStudentDtos();
        StudentOutputDto studentOutputDto = StudentOutputDto.builder()
                .students(studentDtos)
                .build();
        return ResponseEntity.ok().body(new ResponseMessage<>().success(studentOutputDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseMessage<?>> deleteStudents(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
