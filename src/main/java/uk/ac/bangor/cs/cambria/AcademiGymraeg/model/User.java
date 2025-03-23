package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

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

	/* User's unique account ID */
	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	/* User's email address */
	@Column(nullable = false, updatable = true)
	@NotBlank
	@Email
	@Size(max = 320)
	private String emailAddress;

	/* User's first name */
	@Column(nullable = false, updatable = true)
	@NotBlank
	@Size(max = 64)
	private String forename;

	/* User's password */
	@Column(nullable = false)
	@NotBlank
	private String password;

	/* Is the user an administrator? */
	@Column(nullable = false)
	private boolean admin = false;

	/* Is the user an instructor? */
	@Column(nullable = false)
	private boolean instructor = false;

	/*
	 * Assigns specified access to user accounts based on if they are an
	 * administrator and/or instructor. Overrides existing default method
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

	/* Overrides existing default method */
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

	/* Overrides existing default method */
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

}
