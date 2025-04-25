package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.QuestionRepository;

/**
 * @author thh21bgf, jcj23xfb, dwp22pzv
 */

@Entity
@Table(name = "test")
public class Test {

	@Transient
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	@NotNull
	@Column(nullable = false, updatable = false)
	private ZonedDateTime endDateTime;

	@NotNull
	@Column(nullable = false, updatable = false)
	private int numberCorrect;

	@NotNull
	@Column(nullable = false, updatable = false)
	private int numberOfQuestions;

	@NotNull
	@Column(nullable = false, updatable = false)
	private ZonedDateTime startDateTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long testId;

	@ManyToOne
	@NotNull
	@JoinColumn(nullable = false, updatable = false)
	private User user;
	
	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
	private List<Question> questions; //Even though we initially decided we would have the relationship between tests and questions determined on the question side (with Q having a foreign key for T), it seems like thymeleaf prefers having it declared here

	
	public List<Question> getQuestions() {
		return questions;
	}
	
	/**
	 * Gets all the questions in the question repo which have this test ID, and stores them in this test's questions list.
	 * @param questionRepo
	 */
	@Autowired
	public void setQuestions(QuestionRepository questionRepo) {
		
		this.questions = questionRepo.findAllByTest(this);
	}

	public Test() {
	}
	
	/**
	 * Default constructor
	 * @param user - the user taking the test
	 * @param startDateTime - the time the test was started
	 * @param numberOfQuestions - how many questions should the test have?
	 */
	public Test(@NotNull User user, @NotNull ZonedDateTime startDateTime, @NotNull int numberOfQuestions) {
		this.user = user;
		this.startDateTime = startDateTime;
		this.numberOfQuestions = numberOfQuestions;

		this.endDateTime = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
		this.numberCorrect = 0;
	}

	/**
	 * Alternate constructor, with the ability to set the amount of questions correct for testing/debug
	 * @param user
	 * @param startDateTime
	 * @param endDateTime
	 * @param numberCorrect
	 * @param numberOfQuestions
	 */
	public Test(@NotNull User user, @NotNull ZonedDateTime startDateTime, @NotNull ZonedDateTime endDateTime,
			@NotNull int numberCorrect, @NotNull int numberOfQuestions) {
		this.user = user;
		this.startDateTime = startDateTime;
		this.numberOfQuestions = numberOfQuestions;

		this.endDateTime = endDateTime;
		this.numberCorrect = numberCorrect;
	}

	public ZonedDateTime getEndDateTime() {
		return endDateTime;
	}

	public String getFormattedEndTime() {
		return formatter.format(endDateTime);
	}

	public String getFormattedStartTime() {
		return formatter.format(startDateTime);
	}

	public int getNumberCorrect() {
		return numberCorrect;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public ZonedDateTime getStartDateTime() {
		return startDateTime;
	}

	public Long getTestId() {
		return testId;
	}

	public User getUser() {
		return user;
	}

	public void setEndDateTime(ZonedDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public void setNumberCorrect(int numberCorrect) {
		this.numberCorrect = numberCorrect;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public void setStartDateTime(ZonedDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public void setUser(User user) {
		this.user = user;
	}
	


}
