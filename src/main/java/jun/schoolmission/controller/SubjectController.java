package jun.schoolmission.controller;

import jun.schoolmission.common.ResponseMessage;
import jun.schoolmission.domain.dto.subject.SubjectDto;
import jun.schoolmission.domain.dto.subject.SubjectInputDto;
import jun.schoolmission.domain.dto.subject.SubjectOutputDto;
import jun.schoolmission.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<ResponseMessage<?>> searchSubjects() {
        List<SubjectDto> subjectDtos = subjectService.searchSubjectDtos();
        SubjectOutputDto subjectOutputDto = SubjectOutputDto.builder()
                .subjects(subjectDtos)
                .build();
        return ResponseEntity.ok().body(new ResponseMessage<>().success(subjectOutputDto));
    }
}

