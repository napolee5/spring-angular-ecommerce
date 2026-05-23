package project.repository;

import project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);

    List<User> findBySurname(String surname);
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    List<User> findAll();

}
