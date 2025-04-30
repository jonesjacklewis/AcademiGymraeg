package uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction;

import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;

/**
 * @author cnb22xdk
 */

@Component
public class EnglishQuestionImpl implements QuestionConstruction {

	@Override
	public String constructQuestion(String a) {
		return "What is the English word for " + a + "?";
	}

	@Override
	public QuestionType getQuestionType() {
		return QuestionType.WELSH_TO_ENGLISH;
	}

	@Override
	public String getQuestionTypeString() {
		return "WELSH_TO_ENGLISH";
	}

}
