package jun.schoolmission.controller;

import jun.schoolmission.common.ResponseMessage;
import jun.schoolmission.domain.dto.score.ScoreDto;
import jun.schoolmission.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping(value = "/students/{studentId}/subjects/{subjectId}/scores")
    public ResponseEntity<ResponseMessage<?>> createScore(@PathVariable Long studentId, @PathVariable Long subjectId, @RequestBody @Valid ScoreDto scoreDto) {
        scoreService.updateScore(studentId, subjectId, scoreDto);
        return ResponseEntity.created(URI.create("")).body(new ResponseMessage<>().success(null));
    }
}
