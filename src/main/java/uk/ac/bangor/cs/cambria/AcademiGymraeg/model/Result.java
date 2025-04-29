package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

import java.time.Duration;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ResultsService;

public class Result {

	private ZonedDateTime startDateTime;
	private ZonedDateTime endDateTime;
	private String completionDuration;
	private int numberOfQuestions;
	private int numberCorrect;
	private String percentCorrect;

	private final ResultsService rs = new ResultsService();
	private static final Logger logger = LoggerFactory.getLogger(Result.class);

	public Result() {
	}

	/**
	 * 
	 * Constructor
	 * 
	 * @param startDateTime     the start time of the {@link Test} as a
	 *                          {@link ZonedDateTime}
	 * @param endDateTime       the end time of the {@link Test} as a
	 *                          {@link ZonedDateTime}
	 * @param numberOfQuestions an integer representing the number of questions on
	 *                          the test
	 * @param numberCorrect     an integer representing the number of correct
	 *                          answers given on the test
	 * 
	 * @throws a {@link IllegalArgumentException} if end time is before start, there
	 *           are no questions, there are more correct than given
	 */
	public Result(ZonedDateTime startDateTime, ZonedDateTime endDateTime, int numberOfQuestions, int numberCorrect) {

		if (startDateTime.isAfter(endDateTime)) {
			logger.error("End time cannot be before start time.");
			throw new IllegalArgumentException("End time cannot be before start time.");
		}

		if (numberOfQuestions <= 0) {
			logger.error("Cannot calculate a result when there are no questions");
			throw new IllegalArgumentException("Cannot calculate a result when there are no questions");
		}

		if (numberCorrect > numberOfQuestions) {
			logger.error("Cannot get more questions correct than were available");
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
