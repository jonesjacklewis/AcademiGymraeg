package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

/**
 * @author jcj23xfb
 */
@Service
public class ValidatorService {

	private final UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(ValidatorService.class);

	public ValidatorService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * 
	 * Counts the number of times a specific character appears in a {@link String}
	 * 
	 * @param str    a {@link String} to count the occurrences in
	 * @param target a char to count the occurrences of
	 * @return an integer count of the number of times target appears in str
	 */
	public int countCharOccurencesInString(String str, char target) {

		if (str == null) {
			logger.debug("str is null");
			return 0;
		}

		int count = 0;

		for (int i = 0, len = str.length(); i < len; i++) {
			if (str.charAt(i) == target) {
				count++;
			}
		}

		return count;
	}
	
	/**
	 * 
	 * Validates an email is both syntactically valid, and is unique
	 * 
	 * @param email a {@link String} email to validate
	 * @return boolean value true if email is valid, else false
	 */
	public boolean isValidEmail(String email) {
		return isValidEmail(email, false);
	}


	/**
	 * 
	 * Validates an email is both syntactically valid, and is unique
	 * 
	 * @param email a {@link String} email to validate
	 * @param isEdit a boolean for whether to allow duplicate email e.g., for edit
	 * @return boolean value true if email is valid, else false
	 */
	public boolean isValidEmail(String email, boolean isEdit) {

		if (email == null) {
			return false;
		}

		if (!userRepository.findByEmailAddress(email).isEmpty() && !isEdit) {
			return false;
		}

		if (countCharOccurencesInString(email, '@') != 1) {
			return false;
		}

		String[] parts = email.split("@");

		if (parts[0].length() == 0) {
			return false;
		}

		int domainDotCount = countCharOccurencesInString(parts[1], '.');

		if (domainDotCount < 1) {
			return false;
		}

		if (parts[1].length() - domainDotCount == 0) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * Checks whether a {@link String} password conforms to password criteria
	 * 
	 * @param password a {@link String} password to validate
	 * @return a boolean value of true if the password is valid else false
	 */
	public boolean isValidPassword(String password) {

		if (password == null) {
			return false;
		}

		if (password.length() < 8) {
			return false;
		}

		if (password.contains(" ") || password.contains("\t") || password.trim().length() == 0) {
			return false;
		}

		boolean hasLetter = false;
		boolean hasUpper = false;
		boolean hasLower = false;
		boolean hasDigit = false;
		boolean hasSymbol = false;

		for (int i = 0, len = password.length(); i < len; i++) {
			char c = password.charAt(i);

			if (Character.isLetter(c)) {
				hasLetter = true;

				if (Character.isUpperCase(c)) {
					hasUpper = true;
				} else {
					hasLower = true;
				}

			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else {
				hasSymbol = true;
			}

			if (hasLetter && hasUpper && hasLower && hasDigit && hasSymbol) {
				return true;
			}

		}

		return false;
	}

}
