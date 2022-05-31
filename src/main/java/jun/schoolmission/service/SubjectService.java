package jun.schoolmission.service;

import jun.schoolmission.domain.dto.subject.SubjectDto;

import java.util.List;

public interface SubjectService {

    Long createSubject(SubjectDto subjectDto);
    List<SubjectDto> searchSubjectDtos();
    void deleteSubject(Long id);
}
