package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;

public class GenderService {

	private static final Logger logger = LoggerFactory.getLogger(GenderService.class);

	/**
	 * 
	 * Returns a {@link String} of the {@link Gender} values separated with
	 * comma-space
	 * 
	 * @return a {@link String} of the {@link Gender} values separated with
	 *         comma-space
	 */
	public static String getCommaSeperatedGenders() {
		return Arrays.stream(Gender.values()).map(g -> properCase(g.name())).collect(Collectors.joining(", "));
	}

	/**
	 * 
	 * Converts a {@link String} into proper case
	 * 
	 * @param input a {@link String} to convert
	 * @return a {@link String} in proper case
	 */
	private static String properCase(String input) {
		if (input == null || input.isEmpty()) {
			logger.debug("Input is null or empty");
			return input;
		}

		StringBuilder sb = new StringBuilder(input.length());
		boolean nextCharCapital = true;

		for (char c : input.toCharArray()) {
			if (Character.isWhitespace(c) || c == '-') {
				nextCharCapital = true;
				sb.append(c);
			} else if (nextCharCapital) {
				sb.append(Character.toUpperCase(c));
				nextCharCapital = false;
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}

		return sb.toString();

	}
}
