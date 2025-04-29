package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author cnb22xdk, jcj23xfb
 */

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1877944497437890869L;

	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@Column(nullable = false, updatable = true)
	@NotBlank
	@Email
	@Size(max = 320)
	private String emailAddress;

	@Column(nullable = false, updatable = true)
	@NotBlank
	@Size(max = 64)
	private String forename;

	@Column(nullable = false)
	@NotBlank
	private String password;

	@Column(nullable = false)
	private boolean admin = false;

	@Column(nullable = false)
	private boolean instructor = false;

	// set to epoch so show the user has not got an ongoing test
	@Column
	private Instant testStartTimestamp = Instant.EPOCH;

	/**
	 * Returns the authorities granted to the user.
	 * 
	 * Adds "ROLE_ADMIN" if the user has admin privileges and "ROLE_INSTRUCTOR" if
	 * the user has instructor privileges.
	 * 
	 * @return a {@link Collection} of {@link GrantedAuthority} objects representing
	 *         the user's roles
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();

		if (isAdmin())
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		if (isInstructor())
			authorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));

		return authorities;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String getUsername() {
		return emailAddress;
	}

	public void setUsername(String username) {
		this.emailAddress = username;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isInstructor() {
		return instructor;
	}

	public void setInstructor(boolean instructor) {
		this.instructor = instructor;
	}

	public Instant getTestStartTimetamp() {
		return testStartTimestamp;
	}

	public void setTestStartTimetamp(Instant testStartTimetamp) {
		this.testStartTimestamp = testStartTimetamp;
	}

	/**
	 * 
	 * 
	 * Returns whether a user can start a test.
	 * 
	 * Can start a test if the testStartTimestamp is the EPOCH or thirty minutes.
	 * 
	 * @return boolean value of true if they can start a test, else false.
	 */
	public boolean canStartNewTest() {
		Instant now = Instant.now();
		return testStartTimestamp.equals(Instant.EPOCH) || now.minusSeconds(30 * 60).isAfter(testStartTimestamp);
	}

	/**
	 * 
	 * Returns the {@link Instant} that the next test can be started
	 * 
	 * @return the {@link Instant} that the next test can be started
	 */
	public Instant getNextTestStartTime() {
		if (this.canStartNewTest()) {
			return Instant.now();
		}

		return testStartTimestamp.plus(Duration.ofMinutes(30));
	};

}
