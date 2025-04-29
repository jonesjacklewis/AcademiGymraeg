package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;

/**
 * @author cnb22xdk, jcj23xfb
 */

public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * 
	 * Finds an {@link Optional} of {@link User} for a given email address
	 * 
	 * @param emailAddress a {@link String} email address for the user
	 * @return {@link Optional} of {@link User} representing a potential user
	 */
	Optional<User> findByEmailAddress(String emailAddress);
}
