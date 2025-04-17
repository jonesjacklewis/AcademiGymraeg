package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Result;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

/**
 * @author jcj23xfb
 */
@Service
public class ResultsService {

	public ResultsService() {
	}

	public String convertToHourMinuteSecondMilliString(long millisecondDuration) {

		if (millisecondDuration < 0) {
			throw new IllegalArgumentException("A duration cannot be negative");
		}

		Duration duration = Duration.ofMillis(millisecondDuration);

		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;
		long millis = duration.toMillis() % 1000;

		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	}

	public String getResultsString(int numberOfQuestions, int numberCorrect) {
		if (numberOfQuestions <= 0) {
			throw new IllegalArgumentException("Cannot calculate a result when there are no questions");
		}

		if (numberCorrect > numberOfQuestions) {
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

	public List<Result> convertTestsToResults(List<Test> tests) {
		
		if(tests == null) {
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
