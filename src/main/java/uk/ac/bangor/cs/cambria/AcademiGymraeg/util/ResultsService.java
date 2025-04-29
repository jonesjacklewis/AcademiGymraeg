package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Result;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;

/**
 * @author jcj23xfb
 */
@Service
public class ResultsService {

	private static final Logger logger = LoggerFactory.getLogger(ResultsService.class);

	public ResultsService() {
	}

	/**
	 * 
	 * Converts a duration in milliseconds into a {@link String} in the form
	 * HH:MM:SS.mss
	 * 
	 * @param millisecondDuration a long representing a duration in milliseconds
	 * @return a {@link String} in the form HH:MM:SS.mss
	 * @throws an {@link IllegalArgumentException} if the duration is negative
	 */
	public String convertToHourMinuteSecondMilliString(long millisecondDuration) {

		if (millisecondDuration < 0) {
			logger.error("Can not convert a negative duration");
			throw new IllegalArgumentException("A duration cannot be negative");
		}

		Duration duration = Duration.ofMillis(millisecondDuration);

		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;
		long millis = duration.toMillis() % 1000;

		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	}

	/**
	 * 
	 * Gets a formatted percentage {@link String} for a numberOfCorrect and
	 * numberOfQuestions
	 * 
	 * @param numberOfQuestions an integer of the number of questions total
	 * @param numberCorrect     an integer of the number of questions the user
	 *                          answered correctly
	 * @return a {@link String} of a formatted percentage
	 * @throws an {@link IllegalArgumentException} if the number of questions is <=
	 *            0, or the number of correctly answered questions > number of
	 *            questions
	 */
	public String getResultsString(int numberOfQuestions, int numberCorrect) {
		if (numberOfQuestions <= 0) {
			logger.error("0 questions to use");
			throw new IllegalArgumentException("Cannot calculate a result when there are no questions");
		}

		if (numberCorrect > numberOfQuestions) {
			logger.error("Cannot have more correct questions than available");
			throw new IllegalArgumentException("Cannot get more questions correct than were available");
		}

		double percentage = numberCorrect * 100.0 / numberOfQuestions;
		String formattedPercentage;

		if (percentage >= 100) {
			formattedPercentage = "100%";
		} else {
			DecimalFormat df = new DecimalFormat("00.00");
			formattedPercentage = df.format(percentage) + "%";
		}

		return formattedPercentage;
	}

	/**
	 * Converts from a {@link List} of {@link Test} to a {@link List} of
	 * {@link Result}
	 * 
	 * @param tests a {@link List} of {@link Test} to convert
	 * @return a {@link List} of {@link Result} from the test
	 * @throws {@link IllegalArgumentException} if tests is null
	 */
	public List<Result> convertTestsToResults(List<Test> tests) {

		if (tests == null) {
			logger.error("Tests list is null");
			throw new IllegalArgumentException("Tests cannot be null");
		}

		if (tests.size() == 0) {
			return new ArrayList<Result>();
		}

		List<Result> results = new ArrayList<Result>();

		for (Test test : tests) {
			results.add(new Result(test));
		}

		return results;
	}

}
