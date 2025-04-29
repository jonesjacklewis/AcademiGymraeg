package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

class ValidatorServiceTests {

	private final UserRepository userRepository = mock(UserRepository.class);
	private final ValidatorService validatorService = new ValidatorService(userRepository);

	@Test
	void countOccurences_basicCase() {
		assertEquals(3, validatorService.countCharOccurencesInString("banana", 'a'));
	}

	@Test
	void countOccurences_noOccurences() {
		assertEquals(0, validatorService.countCharOccurencesInString("hello", 'q'));
	}

	@Test
	void countOccurences_emptyString() {
		assertEquals(0, validatorService.countCharOccurencesInString("", 'a'));
	}

	@Test
	void countOccurences_nonAlpha() {
		assertEquals(1, validatorService.countCharOccurencesInString("hello@mail.me", '@'));
	}

	@Test
	void countOccurences_nullString() {
		assertEquals(0, validatorService.countCharOccurencesInString(null, '@'));
	}

	@Test
	void countOccurences_caseSensitive() {
		String testString = "Team";
		assertEquals(1, validatorService.countCharOccurencesInString(testString, 'T'));
		assertEquals(0, validatorService.countCharOccurencesInString(testString, 't'));
	}

	@Test
	void isValidEmail_nullEmail() {
		assertFalse(validatorService.isValidEmail(null));
	}

	@Test
	void isValidEmail_duplicateEmail() {
		Optional<User> mockUser = Optional.of(new User());

		when(userRepository.findByEmailAddress("admin@academigymraeg.com")).thenReturn(mockUser);

		assertFalse(validatorService.isValidEmail("admin@academigymraeg.com"));
	}

	@Test
	void isValidEmail_noAtSign() {
		Optional<User> mockUser = Optional.empty();

		when(userRepository.findByEmailAddress("admin")).thenReturn(mockUser);

		assertFalse(validatorService.isValidEmail("admin"));
	}

	@Test
	void isValidEmail_multipleAtSign() {
		Optional<User> mockUser = Optional.empty();

		when(userRepository.findByEmailAddress("admin@mail@com")).thenReturn(mockUser);

		assertFalse(validatorService.isValidEmail("admin@mail@com"));
	}

	@Test
	void isValidEmail_emptyLocalPart() {
		Optional<User> mockUser = Optional.empty();

		when(userRepository.findByEmailAddress("@mail.com")).thenReturn(mockUser);

		assertFalse(validatorService.isValidEmail("@mail.com"));
	}

	@Test
	void isValidEmail_noDomainDot() {
		Optional<User> mockUser = Optional.empty();

		when(userRepository.findByEmailAddress("hello@mailcom")).thenReturn(mockUser);

		assertFalse(validatorService.isValidEmail("hello@mailcom"));
	}

	@Test
	void isValidEmail_domainOnlyContainsDot() {
		Optional<User> mockUser = Optional.empty();

		when(userRepository.findByEmailAddress("hello@.")).thenReturn(mockUser);

		assertFalse(validatorService.isValidEmail("hello@."));
	}

	@Test
	void isValidEmail_domainOnlyContainsMultipleDots() {
		Optional<User> mockUser = Optional.empty();

		when(userRepository.findByEmailAddress("hello@..")).thenReturn(mockUser);

		assertFalse(validatorService.isValidEmail("hello@.."));
	}

	@Test
	void isValidEmail_validEmail() {
		Optional<User> mockUser = Optional.empty();

		when(userRepository.findByEmailAddress("test@domain.com")).thenReturn(mockUser);

		assertTrue(validatorService.isValidEmail("hello@domain.com"));
	}

	@Test
	void isValidPassword_tooShort() {
		assertFalse(validatorService.isValidPassword("test"));
	}

	@Test
	void isValidPassword_containsSpace() {
		assertFalse(validatorService.isValidPassword("pass word"));
	}

	@Test
	void isValidPassword_containsTab() {
		assertFalse(validatorService.isValidPassword("password\t"));
	}

	@Test
	void isValidPassword_noLetters() {
		assertFalse(validatorService.isValidPassword("1234567!"));
	}

	@Test
	void isValidPassword_noUppercase() {
		assertFalse(validatorService.isValidPassword("пароль123!"));
	}

	@Test
	void isValidPassword_noLowercase() {
		assertFalse(validatorService.isValidPassword("ΚΩΔΙΚΟΣ123!"));
	}

	@Test
	void isValidPassword_noDigits() {
		assertFalse(validatorService.isValidPassword("PassпаΔΙΚΟ!"));
	}

	@Test
	void isValidPassword_noSymbols() {
		assertFalse(validatorService.isValidPassword("PassпаΔΙΚΟ123"));
	}

	@Test
	void isValidPassword_isEmpty() {
		assertFalse(validatorService.isValidPassword(""));
	}

	@Test
	void isValidPassword_isNull() {
		assertFalse(validatorService.isValidPassword(null));
	}

	@Test
	void isValidPassword_onlyNonLatinLetters() {
		assertFalse(validatorService.isValidPassword("парольΚωδικός密碼סיסמה"));
	}

	@Test
	void isValidPassword_validPasswordComplex() {
		assertTrue(validatorService.isValidPassword("PassпаΔΙΚΟ123!"));
	}

	@Test
	void isValidPassword_validPasswordSimple() {
		assertTrue(validatorService.isValidPassword("Password123!"));
	}

	@Test
	void isValidPassword_validPasswordExactlyMin() {
		assertTrue(validatorService.isValidPassword("Pass123!"));
	}

}
