package jun.schoolmission.controller;

import jun.schoolmission.common.ResponseMessage;
import jun.schoolmission.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping(value = "/students/{studentId}/subjects/{subjectId}/scores")
    public ResponseEntity<ResponseMessage<?>> createScore(@PathVariable Long studentId, @PathVariable Long subjectId) {
        return null;
    }
}
