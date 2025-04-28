package uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction;

import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.QuestionConstruction;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.GenderService;

/**
 * @author cnb22xdk
 */

@Component
public class GenderQuestionImpl implements QuestionConstruction {

	/**
	 * Perform the question construction for an Welsh word's Gender
	 */
	@Override
	public String constructQuestion(String a) {

		String commaOptions = GenderService.getCommaSeperatedGenders();

		return "Is this word " + commaOptions + " " + a + "?";
	}

	/**
	 * Sets the appropriate enumeration value
	 */
	@Override
	public QuestionType getQuestionType() {
		return QuestionType.GENDER;
	}

	/**
	 * Sets the text of the enumeration value
	 */
	@Override
	public String getQuestionTypeString() {
		return "GENDER";
	}

}
