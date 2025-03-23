package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;

/**
 * @author cnb22xdk, jcj23xfb
 */

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmailAddress(String emailAddress);
}
