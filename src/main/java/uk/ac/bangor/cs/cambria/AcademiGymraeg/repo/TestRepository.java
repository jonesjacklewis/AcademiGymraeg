package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;

/**
 * @author thh21bgf, jcj23xfb
 */

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
	/**
	 * 
	 * Returns a {@link List} of {@link Test} for a given {@link User}
	 * 
	 * @param user a {@link User} to find a {@link List} of {@link Test} for
	 * @return a {@link List} of {@link Test} for the {@link User}
	 */
	List<Test> findAllByUser(User user);

	/**
	 * 
	 * Deletes all tests for a given {@link User}
	 * 
	 * @param user a {@link User} whose tests should be deleted
	 */
	void deleteAllByUser(User user);
}
