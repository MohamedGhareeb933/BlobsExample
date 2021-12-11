package example.blob.dao;

import example.blob.entity.Prerequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PrerequisiteRepository extends JpaRepository<Prerequisite, Long> {
}
