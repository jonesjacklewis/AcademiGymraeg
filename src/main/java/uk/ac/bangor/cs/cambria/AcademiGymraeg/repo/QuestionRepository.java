package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;

/**
 * @author dwp22pzv
 */

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	/**
	 * 
	 * Returns a {@link List} of {@link Question} for a given {@link Test}
	 * 
	 * @param test a {@link Test} to find a {@link List} of {@link Question} for
	 * @return a {@link List} of {@link Question} for the {@link Test}
	 */
	List<Question> findAllByTest(Test test);
}
