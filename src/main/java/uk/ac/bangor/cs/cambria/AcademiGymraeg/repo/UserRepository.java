package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.Model.User;

/**
 * @author cnb22xdk
 */

public interface UserRepository extends JpaRepository<User, Long> {

}
