package uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction;

import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.GenderService;

/**
 * @author cnb22xdk
 */

@Component
public class GenderQuestionImpl implements QuestionConstruction {

	@Override
	public String constructQuestion(String a) {

		String commaOptions = GenderService.getCommaSeperatedGenders();

		return "Is this word " + commaOptions + " " + a + "?";
	}

	@Override
	public QuestionType getQuestionType() {
		return QuestionType.GENDER;
	}

	@Override
	public String getQuestionTypeString() {
		return "GENDER";
	}

}
