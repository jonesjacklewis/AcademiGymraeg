package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;

/**
 * @author dwp22pzv
 */

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
