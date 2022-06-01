package jun.schoolmission.service;

import jun.schoolmission.domain.dto.score.ScoreDto;

public interface ScoreService {

    Long updateScore(Long studentId, Long subjectId, ScoreDto scoreDto);
    void deleteScore(Long studentId, Long subjectId);
}
