package jun.schoolmission.service;

public interface SubjectStudentService {

    Long createScore(Long studentId, Long subjectId, ScoreDto scoreDto);
}
