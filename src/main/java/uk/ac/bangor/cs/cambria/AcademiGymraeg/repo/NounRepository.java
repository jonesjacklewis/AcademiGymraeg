package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.Model.Noun;

/**
 * @author ptg22svs
 */

public interface NounRepository extends JpaRepository<Noun, Long> {

	//Already inherits "FindAll" and "FindByID"
	//In order to select a Random Noun, Controller class could
	//iterate over "FindAll" for a random amount of iterations
	
	
	//Updating a Noun will be as simple as "FindByID" then call set<property> on the
	//returned Noun object
	
	//Deleting will involve calling the inherited "DeleteByID" method
}
