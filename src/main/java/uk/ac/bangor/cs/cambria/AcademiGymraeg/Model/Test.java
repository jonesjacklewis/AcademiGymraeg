package uk.ac.bangor.cs.cambria.AcademiGymraeg.Model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/**
 * @author thh21bgf, jcj23xfb
 */

@Entity
public class Test {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long testId;
	
	@NotNull
	@Column(nullable = false, updatable = false)
	private ZonedDateTime startDateTime;
	
	@NotNull
	@Column(nullable = false, updatable = false)
	private ZonedDateTime endDateTime;

	@NotNull
	@Column(nullable = false, updatable = false)
	private int numberCorrect;
	
	@NotNull
	@Column(nullable = false, updatable = false)
	private int numberOfQuestions;
	
	public ZonedDateTime getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(ZonedDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public ZonedDateTime getEndDateTime() {
		return endDateTime;
	}
	
	public void setEndDateTime(ZonedDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public String getFormattedStartTime() {
		return formatter.format(startDateTime);
	}
	
	public String getFormattedEndTime() {
		return formatter.format(endDateTime);
	}
	
	public Long getTestId() {
		return testId;
	}
	
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	
	public int getNumberCorrect() {
		return numberCorrect;
	}
	
	public void setNumberCorrect(int numberCorrect) {
		this.numberCorrect = numberCorrect;
	}
	
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}
	
	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
	
}
