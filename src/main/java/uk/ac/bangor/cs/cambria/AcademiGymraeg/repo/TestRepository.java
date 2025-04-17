package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;

/**
 * @author thh21bgf, jcj23xfb
 */

public interface TestRepository extends JpaRepository<Test, Long>{
	List<Test> findAllByUser(User u);
}
