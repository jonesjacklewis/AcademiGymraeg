package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;

/**
 * @author dwp22pzv
 */

@Entity

public class Question {

	public Question() {

	}

	public Question( @NotBlank String questionString, @NotBlank Noun noun,
			@NotBlank QuestionType questionType, @NotBlank String correctAnswer, Test test) {
		this.questionString = questionString;
		this.noun = noun;
		this.questionType = questionType;
		this.correctAnswer = correctAnswer;
		this.test = test;
	}

	/**
	 * Id attribute. The unique identifier for each Question object, and the primary
	 * key for the Question TABLE in the database. The value will be autogenerated,
	 * and cannot be updated once created.
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@NotBlank
	private Long id;

	

	/**
	 * Question attribute. The string containing the actual question being asked.
	 */
	@Column(nullable = false)
	@NotBlank
	private String questionString;

	/**
	 * Noun attribute, the Noun object used by the question.
	 */
	@Column(nullable = false)
	@NotBlank
	private Noun noun;

	/**
	 * QuestionType attribute. Represents the type of this question, either English
	 * to Welsh, Welsh to English, or Gender.
	 */
	@Column(nullable = false)
	@NotBlank
	private QuestionType questionType;

	/**
	 * correctAnswer attribute. The string containing the correct answer to the
	 * question.
	 */
	@Column(nullable = false)
	@NotBlank
	private String correctAnswer;

	/**
	 * givenAnswer attribute. The string containing the answer provided by the user
	 */
	private String givenAnswer;

	private Test test;

	@Override
	public String toString() {
		return questionString;
	}

    public Long getId() {
        return id;
    }

    public String getQuestionString() {
        return questionString;
    }

    public Noun getNoun() {
        return noun;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public Test getTest() {
        return test;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }



}