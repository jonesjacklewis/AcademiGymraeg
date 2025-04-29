package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Result;

class ResultsServiceTests {

	private final ResultsService rs = new ResultsService();

	@Test
	void convertToHHMMSS_negativeDuration() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rs.convertToHourMinuteSecondMilliString(-100L));

		assertEquals("A duration cannot be negative", exception.getMessage());
	}

	@Test
	void convertToHHMMSS_justOneHour() {
		String res = rs.convertToHourMinuteSecondMilliString(3600000L);

		assertEquals(res, "01:00:00.000");
	}

	@Test
	void convertToHHMMSS_justOneMinute() {
		String res = rs.convertToHourMinuteSecondMilliString(60000L);

		assertEquals(res, "00:01:00.000");
	}

	@Test
	void convertToHHMMSS_justOneSecond() {
		String res = rs.convertToHourMinuteSecondMilliString(1000L);

		assertEquals(res, "00:00:01.000");
	}

	@Test
	void convertToHHMMSS_justOneMillisecond() {
		String res = rs.convertToHourMinuteSecondMilliString(1L);

		assertEquals(res, "00:00:00.001");
	}

	@Test
	void convertToHHMMSS_999Millisecond() {
		String res = rs.convertToHourMinuteSecondMilliString(999L);

		assertEquals(res, "00:00:00.999");
	}

	@Test
	void convertToHHMMSS_mixedTime() {
		String res = rs.convertToHourMinuteSecondMilliString(3661001L);

		assertEquals(res, "01:01:01.001");
	}

	@Test
	void convertToHHMMSS_zeroDuration() {
		String res = rs.convertToHourMinuteSecondMilliString(0L);

		assertEquals(res, "00:00:00.000");
	}

	@Test
	void getResultString_noQuestions() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rs.getResultsString(0, 10));

		assertEquals("Cannot calculate a result when there are no questions", exception.getMessage());
	}

	@Test
	void getResultString_negativeQuestions() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rs.getResultsString(-10, 10));

		assertEquals("Cannot calculate a result when there are no questions", exception.getMessage());
	}

	@Test
	void getResultString_moreCorrectThanQuestions() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rs.getResultsString(10, 11));

		assertEquals("Cannot get more questions correct than were available", exception.getMessage());
	}

	@Test
	void getResultString_oneHundredPercent() {
		String res = rs.getResultsString(10, 10);

		assertEquals(res, "100%");
	}

	@Test
	void getResultString_zeroPercent() {
		String res = rs.getResultsString(10, 0);

		assertEquals(res, "00.00%");
	}

	@Test
	void getResultString_fiftyPercent() {
		String res = rs.getResultsString(10, 5);

		assertEquals(res, "50.00%");
	}

	@Test
	void getResultString_decimalPercent() {
		String res = rs.getResultsString(13, 7);

		assertEquals(res, "53.85%");
	}

	@Test
	void getResultString_negativePercent() {
		String res = rs.getResultsString(13, -5);

		assertEquals(res, "-38.46%");
	}

	@Test
	void convertTestList_emptyList() {
		List<uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test> tests = new ArrayList<>();
		List<Result> results = rs.convertTestsToResults(tests);

		assertTrue(results.isEmpty());
	}

	@Test
	void convertTestList_nullList() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rs.convertTestsToResults(null));

		assertEquals("Tests cannot be null", exception.getMessage());
	}

	@Test
	void convertTestList_validTest() {
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusMinutes(2L);
		uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test mockTest = mock(
				uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test.class);
		int numberOfQuestions = 10;
		int numberCorrect = 5;

		when(mockTest.getStartDateTime()).thenReturn(start);
		when(mockTest.getEndDateTime()).thenReturn(end);
		when(mockTest.getNumberOfQuestions()).thenReturn(numberOfQuestions);
		when(mockTest.getNumberCorrect()).thenReturn(numberCorrect);

		List<uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test> tests = List.of(mockTest);

		List<Result> results = rs.convertTestsToResults(tests);

		assertEquals(1, results.size());

		Result result = results.get(0);

		assertEquals(start, result.getStartDateTime());
		assertEquals(end, result.getEndDateTime());
		assertEquals("00:02:00.000", result.getCompletionDuration());
		assertEquals(numberOfQuestions, result.getNumberOfQuestions());
		assertEquals(numberCorrect, result.getNumberCorrect());
		assertEquals("50.00%", result.getPercentCorrect());

	}

}
