package jun.schoolmission.controller;

import jun.schoolmission.common.ResponseMessage;
import jun.schoolmission.domain.dto.subject.SubjectInputDto;
import jun.schoolmission.service.SubjectService;
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
@RequiredArgsConstructor
@RequestMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ResponseMessage<?>> createSubject(@RequestBody @Valid SubjectInputDto subjectInputDto) {
        Long id = subjectService.createSubject(subjectInputDto.getSubject());
        return ResponseEntity.created(URI.create("/subjects/" + id)).body(new ResponseMessage<>().success(null));
    }
}

