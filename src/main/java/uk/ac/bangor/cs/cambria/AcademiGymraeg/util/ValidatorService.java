package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

/**
 * @author jcj23xfb
 */
public class ValidatorService {

	private final UserRepository userRepository;

	public ValidatorService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public int countCharOccurencesInString(String str, char target) {

		if (str == null) {
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

	public boolean isValidEmail(String email) {

		if (email == null) {
			return false;
		}

		if (!userRepository.findByEmailAddress(email).isEmpty()) {
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
