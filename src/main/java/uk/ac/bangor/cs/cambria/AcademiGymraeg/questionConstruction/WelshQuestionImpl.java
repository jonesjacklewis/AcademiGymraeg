package uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction;

import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.QuestionConstruction;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;

/**
 * @author cnb22xdk
 */

@Component
public class WelshQuestionImpl implements QuestionConstruction {
	
	/**
	 * Perform the question construction for an Welsh word
	 */
	@Override
	public String constructQuestion(String a) {
		return "What is the Welsh word for " + a + "?";
	}
	
	/**
	 * Sets the appropriate enumeration value
	 */
	@Override
	public QuestionType getQuestionType() {
		return QuestionType.ENGLISH_TO_WELSH;
	}
	
	/**
	 * Sets the text of the enumeration value
	 */
	@Override
	public String getQuestionTypeString() {
		return "ENGLISH_TO_WELSH";
	}

}
