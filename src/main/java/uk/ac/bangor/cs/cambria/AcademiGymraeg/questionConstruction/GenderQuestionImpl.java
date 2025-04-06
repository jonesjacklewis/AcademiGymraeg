package uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction;

import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.QuestionConstruction;

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
		return "What is the gender of the word " + a + "?";
	}
	
	/**
	 * Sets the appropriate enumeration value
	 */
	@Override
	public String getQuestionType() {
		return "GENDER";
	}

}
