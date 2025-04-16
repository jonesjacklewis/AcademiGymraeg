package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

import java.time.Duration;
import java.time.ZonedDateTime;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ResultsService;

public class Result {
	private ZonedDateTime startDateTime;
	private ZonedDateTime endDateTime;
	private String completionDuration;
	private int numberOfQuestions;
	private int numberCorrect;
	private String percentCorrect;

	private final ResultsService rs = new ResultsService();

	public Result() {
	}

	public Result(ZonedDateTime startDateTime, ZonedDateTime endDateTime, int numberOfQuestions, int numberCorrect) {

		if (startDateTime.isAfter(endDateTime)) {
			throw new IllegalArgumentException("End time cannot be before start time.");
		}

		if (numberOfQuestions <= 0) {
			throw new IllegalArgumentException("Cannot calculate a result when there are no questions");
		}

		if (numberCorrect > numberOfQuestions) {
			throw new IllegalArgumentException("Cannot get more questions correct than were available");
		}

		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;

		long millisecondDuration = Duration.between(startDateTime, endDateTime).toMillis();
		this.completionDuration = rs.convertToHourMinuteSecondMilliString(millisecondDuration);

		this.numberOfQuestions = numberOfQuestions;
		this.numberCorrect = numberCorrect;

		this.percentCorrect = rs.getResultsString(numberOfQuestions, numberCorrect);
	}

	public Result(Test test) {
		this(test.getStartDateTime(), test.getEndDateTime(), test.getNumberOfQuestions(), test.getNumberCorrect());
	}

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

	public String getCompletionDuration() {
		return completionDuration;
	}

	public void setCompletionDuration(String completionDuration) {
		this.completionDuration = completionDuration;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public int getNumberCorrect() {
		return numberCorrect;
	}

	public void setNumberCorrect(int numberCorrect) {
		this.numberCorrect = numberCorrect;
	}

	public String getPercentCorrect() {
		return percentCorrect;
	}

	public void setPercentCorrect(String percentCorrect) {
		this.percentCorrect = percentCorrect;
	}

}
