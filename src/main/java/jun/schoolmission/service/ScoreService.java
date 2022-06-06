package jun.schoolmission.service;

import jun.schoolmission.domain.dto.score.ScoreDto;
import jun.schoolmission.domain.dto.score.StudentScoreDto;

public interface ScoreService {

    void updateScore(Long studentId, Long subjectId, ScoreDto scoreDto);
    void deleteScore(Long studentId, Long subjectId);
    StudentScoreDto findStudentAvgScore(Long studentId);
}
