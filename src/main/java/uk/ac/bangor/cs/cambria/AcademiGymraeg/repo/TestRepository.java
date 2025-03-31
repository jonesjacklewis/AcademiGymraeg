package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;

/**
 * @author thh21bgf
 */

public interface TestRepository extends JpaRepository<Test, Long>{
	
	Optional <Test> findByTestId(Long testId);
	Optional <Test> findTestEndDateTimeBetween(ZonedDateTime endDateTime);
	Optional <Test> findByNumberCorrect(int numberCorrect);
	
}
