package uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;

/**
 * @author cnb22xdk
 */

public interface QuestionConstruction {

	/**
	 * Generates the question text for a given {@link String} noun
	 * 
	 * @param noun {@link String} noun to generate a question text for
	 * @return a {@link String} question text for the noun
	 */
	String constructQuestion(String noun);

	/**
	 * @return the {@link QuestionType} that the question represents
	 */
	QuestionType getQuestionType();

	/**
	 * @return the {@link String} value of the {@link QuestionType} that the
	 *         question represents
	 */
	String getQuestionTypeString();

}
