package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.QuestionRepository;

/**
 * @author dwp22pzv, jcj23xfb, cnb22xdk
 */

@Component
public class QuestionConfigurer {

	private final QuestionRepository repo;

	public QuestionConfigurer(QuestionRepository repo) {
		this.repo = repo;
	}

	/**
	 * 
	 * Generates a set of questions for a specific {@link Test} from a {@link List}
	 * of {@link Noun}
	 * 
	 * @param nouns a {@link List} of {@link Noun} to use to generate the questions
	 * @param test  a {@link Test} to assign the questions to
	 */
	public void configureQuestion(List<Noun> nouns, Test test) {

		int questionTypeIndex = 0;

		List<Question> questions = new ArrayList<Question>();
		QuestionType[] questionTypeValues = QuestionType.values();

		for (Noun noun : nouns) {

			if (questionTypeIndex >= questionTypeValues.length) {
				questionTypeIndex = 0;
			}

			QuestionType questionType = questionTypeValues[questionTypeIndex];

			Question newQuestion = new Question(noun, questionType, test);

			questions.add(newQuestion);

			questionTypeIndex++;
		}

		Collections.shuffle(questions);

		repo.saveAll(questions);
		test.setQuestionsWithRepo(repo);
	}

}
