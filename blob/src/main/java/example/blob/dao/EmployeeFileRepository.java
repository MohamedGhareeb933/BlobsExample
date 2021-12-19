package example.blob.dao;

import example.blob.entity.EmployeeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeFileRepository extends JpaRepository<EmployeeFile, Long> {
}
