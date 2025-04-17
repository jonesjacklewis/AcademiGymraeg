package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;

/**
 * @author dwp22pzv
 */

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByTest(Test test);
}
