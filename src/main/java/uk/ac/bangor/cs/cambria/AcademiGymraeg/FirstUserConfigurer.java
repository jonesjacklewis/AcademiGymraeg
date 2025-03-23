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
		if (!repo.findByEmailAddress("admin@academigymraeg.com").isEmpty()) {
			return;
		}

		User u = new User();

		u.setAdmin(true);
		u.setInstructor(true);
		u.setPassword(encoder.encode("password123!"));
		u.setForename("admin");
		u.setUsername("admin@academigymraeg.com");

		repo.save(u);
	}
}
