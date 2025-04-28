package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

/**
 * @author jcj23xfb
 */

@Component
public class FirstUserConfigurer {
	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder encoder;

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
	}

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
	}

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
	}
	
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
	}
}
