package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;

/**
 * @author cnb22xdk
 */

public interface QuestionConstruction {

	/**
	 * Perform the question construction and return result
	 * 
	 * @param a the noun
	 * @return result of question construction
	 */
	String constructQuestion(String a);

	/**
	 * @return the enum of the question type enumeration.
	 */
	QuestionType getQuestionType();

	/**
	 * @return the String of the question type enumeration.
	 */
	String getQuestionTypeString();

}
