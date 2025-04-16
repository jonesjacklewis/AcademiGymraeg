package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

/**
 * @author thh21bgf, jcj23xfb
 */

@Entity
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

	public Test() {
	}

	public Test(@NotNull User user, @NotNull ZonedDateTime startDateTime, @NotNull int numberOfQuestions) {
		this.user = user;
		this.startDateTime = startDateTime;
		this.numberOfQuestions = numberOfQuestions;

		this.endDateTime = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
		this.numberCorrect = 0;
	}

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
