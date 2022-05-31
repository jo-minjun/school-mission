package jun.schoolmission.controller;

import jun.schoolmission.common.ResponseMessage;
import jun.schoolmission.domain.dto.subject.SubjectInputDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubjectController {

    @PostMapping
    public ResponseEntity<ResponseMessage<?>> createSubject(@RequestBody @Valid SubjectInputDto subjectInputDto) {
        return null;
    }
}
