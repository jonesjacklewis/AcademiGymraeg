package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

/**
 * @author jcj23xfb
 */

@Component
public class RepositoryUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> obj = repo.findByEmailAddress(username);
		
		if(obj.isPresent()) {
			return obj.get();
		}
		
		throw new UsernameNotFoundException(username = " not found.");
	}

}
