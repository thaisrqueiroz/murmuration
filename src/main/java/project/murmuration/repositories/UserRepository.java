package project.murmuration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.murmuration.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}