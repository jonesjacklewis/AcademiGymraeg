package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction.EnglishQuestionImpl;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction.GenderQuestionImpl;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction.QuestionConstruction;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.questionConstruction.WelshQuestionImpl;

/**
 * @author dwp22pzv, jcj23xfb, cnb22xdk
 */

@Entity
@Table(name = "question")
public class Question {

	private static final Logger logger = LoggerFactory.getLogger(Question.class);

	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@NotNull
	private Long questionId;

	@Column(nullable = false)
	@NotBlank
	private String questionString;

	@JoinColumn(nullable = false)
	@NotNull
	@ManyToOne
	private Noun noun;

	@Column(nullable = false)
	@NotNull
	private QuestionType questionType;

	@Column(nullable = false)
	@NotBlank
	private String correctAnswer;

	private String givenAnswer;

	@ManyToOne
	@NotNull
	@JoinColumn(nullable = false, updatable = false)
	private Test test;

	public Question() {
	}

	/**
	 * 
	 * Constructor which then populates the Question correctly
	 * 
	 * @param noun         The {@link Noun} object to use for the question
	 * @param questionType The {@link QuestionType} that the question will be
	 * @param test         The {@link Test} the question will belong to
	 */
	public Question(@NotBlank Noun noun, @NotBlank QuestionType questionType, Test test) {
		this.noun = noun;
		this.questionType = questionType;
		this.test = test;

		generateQuestion();
	}

	/**
	 * 
	 * Generates the full Question object
	 * 
	 * @throws {@link IllegalArgumentException} if the {@link QuestionType} is not
	 *                recognised
	 * 
	 */
	private void generateQuestion() {

		QuestionConstruction questionConstructor;
		String queriedNoun;

		switch (this.questionType) {
		case WELSH_TO_ENGLISH -> {
			questionConstructor = new EnglishQuestionImpl();
			queriedNoun = noun.getWelshNoun();
			this.correctAnswer = noun.getEnglishNoun();
		}
		case ENGLISH_TO_WELSH -> {
			questionConstructor = new WelshQuestionImpl();
			queriedNoun = noun.getEnglishNoun();
			this.correctAnswer = noun.getWelshNoun();
		}
		case GENDER -> {
			questionConstructor = new GenderQuestionImpl();
			queriedNoun = noun.getWelshNoun();
			this.correctAnswer = noun.getGender().name();
		}
		default -> {
			logger.error(this.questionString + " is not recognised");
			throw new IllegalArgumentException("Unrecognised question type " + this.questionType);
		}
		}

		this.questionString = questionConstructor.constructQuestion(queriedNoun);
	}

	/**
	 * 
	 * Checks that the {@link Question} has been answered correctly
	 * 
	 * @return a {@link Boolean} showing true if the answer was correct, else false
	 */
	public Boolean checkAnswer() {
		if (this.givenAnswer == null || this.givenAnswer.isEmpty() || this.givenAnswer.isBlank()) {
			return false;
		}

		String sanitisedCorrectAnswer = this.correctAnswer.toLowerCase().trim();
		String sanitisedGivenAnswer = this.givenAnswer.toLowerCase().trim();

		return sanitisedGivenAnswer.equals(sanitisedCorrectAnswer);
	}

	@Override
	public String toString() {
		return questionString;
	}

	public Long getQuestionId() {
		return questionId;
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

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
