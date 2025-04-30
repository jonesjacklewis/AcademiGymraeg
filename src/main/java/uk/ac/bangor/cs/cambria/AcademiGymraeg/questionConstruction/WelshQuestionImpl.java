package uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction;

import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;

/**
 * @author cnb22xdk
 */

@Component
public class WelshQuestionImpl implements QuestionConstruction {

	@Override
	public String constructQuestion(String a) {
		return "What is the Welsh word for " + a + "?";
	}

	@Override
	public QuestionType getQuestionType() {
		return QuestionType.ENGLISH_TO_WELSH;
	}

	@Override
	public String getQuestionTypeString() {
		return "ENGLISH_TO_WELSH";
	}

}
