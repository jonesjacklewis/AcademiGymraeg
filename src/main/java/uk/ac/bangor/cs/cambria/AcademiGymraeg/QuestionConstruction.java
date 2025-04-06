package uk.ac.bangor.cs.cambria.AcademiGymraeg;

/**
 * @author cnb22xdk
 */


public interface QuestionConstruction {
	
	/**
	 * Perform the question construction and return result
	 * @param a the noun
	 * @return result of question construction
	 */
	String constructQuestion(String a);
	
	/**
	 * @return the string of the question type enumeration.
	 */
	String getQuestionType();

}
