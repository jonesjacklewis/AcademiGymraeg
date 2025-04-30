package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

/**
 * @author grs22lkc
 */

@Service
public class UserService {

	private final UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * 
	 * Gets the ID of the currently logged in user
	 * 
	 * @return the {@link Long} ID of the user, or null if does not exist;
	 */
	public Long getLoggedInUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			User user = userRepository.findByEmailAddress(username).orElse(null);

			if (user != null) {
				return user.getUserId();
			}
		}
		logger.debug("User does not exist/is not authenticated");
		return null;
	}

	/**
	 * 
	 * Gets the forename of the currently logged in user
	 * 
	 * @return the {@link String} forename of the user, or "Unknown User" if does
	 *         not exist;
	 */
	public String getLoggedInUserForename() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			User user = userRepository.findByEmailAddress(username).orElse(null);

			if (user != null) {
				return user.getForename();
			}
		}
		logger.debug("User does not exist/is not authenticated");
		return "Unknown User";
	}

	/**
	 * 
	 * Gets the rmail of the currently logged in user
	 * 
	 * @return the {@link String} rmail of the user, or null if does not exist;
	 */
	public String getLoggedInUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			User user = userRepository.findByEmailAddress(username).orElse(null);

			if (user != null) {
				return user.getUsername();
			}
		}
		logger.debug("User does not exist/is not authenticated");
		return null;
	}

	/**
	 * 
	 * Returns true if the current user is admin, else false
	 * 
	 * @return boolean value of true if the current user is admin, else false
	 */
	public boolean isLoggedInUserAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			User user = userRepository.findByEmailAddress(username).orElse(null);

			if (user != null) {
				return user.isAdmin();
			}
		}
		logger.debug("User does not exist/is not authenticated");
		return false;
	}

	/**
	 * 
	 * Returns true if the current user is instructor, else false
	 * 
	 * @return boolean value of true if the current user is instructor, else false
	 */
	public boolean isLoggedInUserInstructor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			User user = userRepository.findByEmailAddress(username).orElse(null);

			if (user != null) {
				return user.isInstructor();
			}
		}
		logger.debug("User does not exist/is not authenticated");
		return false;
	}

}
