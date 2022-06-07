package jun.schoolmission.domain.repository;

import jun.schoolmission.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByPhoneNumber(String phoneNumber);

    @Query(value = "select student from Student student join fetch student.studentSubjects")
    Optional<Student> findByIdFetch(Long studentId);
}
