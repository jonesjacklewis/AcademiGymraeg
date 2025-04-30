package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

/**
 * @author jcj23xfb, ptg22svs
 */

@Component
public class FirstUserConfigurer {
	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder encoder;

	private static final Logger logger = LoggerFactory.getLogger(FirstUserConfigurer.class);

	/**
	 * Sets up a "super" user
	 */
	@PostConstruct
	void setupFirstUser() {
		if (!repo.findByEmailAddress("super@academigymraeg.com").isEmpty()) {
			return;
		}

		User u = new User();

		u.setAdmin(true);
		u.setInstructor(true);
		u.setPassword(encoder.encode("password123!"));
		u.setForename("super");
		u.setUsername("super@academigymraeg.com");

		repo.save(u);
		logger.debug("At super user");
	}

	/**
	 * Sets up an instructor
	 */
	@PostConstruct
	void setupSecondUser() {
		if (!repo.findByEmailAddress("instructor@academigymraeg.com").isEmpty()) {
			return;
		}

		User u = new User();

		u.setAdmin(false);
		u.setInstructor(true);
		u.setPassword(encoder.encode("password456!"));
		u.setForename("instructor");
		u.setUsername("instructor@academigymraeg.com");

		repo.save(u);
		logger.debug("At instructor user");
	}

	/**
	 * Sets up a student
	 */
	@PostConstruct
	void setupThirdUser() {
		if (!repo.findByEmailAddress("student1@academigymraeg.com").isEmpty()) {
			return;
		}

		User u = new User();

		u.setAdmin(false);
		u.setInstructor(false);
		u.setPassword(encoder.encode("password789!"));
		u.setForename("student1");
		u.setUsername("student1@academigymraeg.com");

		repo.save(u);
		logger.debug("At student user");
	}

	/**
	 * Sets up an admin
	 */
	@PostConstruct
	void setupFourthUser() {
		if (!repo.findByEmailAddress("admin@academigymraeg.com").isEmpty()) {
			return;
		}

		User u = new User();

		u.setAdmin(true);
		u.setInstructor(false);
		u.setPassword(encoder.encode("password012!"));
		u.setForename("admin");
		u.setUsername("admin@academigymraeg.com");

		repo.save(u);
		logger.debug("At admin user");
	}
}
