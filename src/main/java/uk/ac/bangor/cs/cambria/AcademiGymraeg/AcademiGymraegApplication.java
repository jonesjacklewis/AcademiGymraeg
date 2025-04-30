package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author jcj23xfb, grs22lkc
 */
@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AcademiGymraegApplication {

	@Autowired
	private UserDetailsService userDetailsService;

	public static void main(String[] args) {
		SpringApplication.run(AcademiGymraegApplication.class, args);
	}

	/**
	 * grs22lkc added this bean to allow retrieval of static files when user not
	 * logged in
	 **/
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
	}

	/**
	 * 
	 * Configures security filter chain for application, custom log in, and log out
	 * handling.
	 * 
	 * @param http the {@link HttpSecurity} to modify
	 * @return the configured {@link SecurityFilterChain} instance
	 * @throws Exception if an error is caused when configuring security settings
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService)
				.formLogin(
						form -> form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/home", true))
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")))
				.authorizeHttpRequests(req -> req.requestMatchers("/login").permitAll().anyRequest().authenticated());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
