package jun.schoolmission.domain.repository;

import jun.schoolmission.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);

    @Query(value = "select subject from Subject subject join fetch subject.studentSubjects")
    Optional<Subject> findByIdFetch(Long subjectId);
}
