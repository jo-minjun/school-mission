package jun.schoolmission.domain.repository;

import jun.schoolmission.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
