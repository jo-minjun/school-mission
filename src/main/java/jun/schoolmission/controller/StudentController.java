package jun.schoolmission.controller;

import jun.schoolmission.common.ResponseMessage;
import jun.schoolmission.domain.dto.StudentInputDto;
import jun.schoolmission.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

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
}
