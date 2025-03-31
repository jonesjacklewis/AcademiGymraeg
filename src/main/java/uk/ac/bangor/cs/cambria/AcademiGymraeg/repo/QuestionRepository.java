package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;

/**
 * @author dwp22pzv
 */

public interface QuestionRepository extends JpaRepository<Question, Long> {
	// Already inherits "FindAll" and "FindByID"

	//May not work, to redo if not
	List<Question> findNRandomQuestions(int n);
	
	
}
