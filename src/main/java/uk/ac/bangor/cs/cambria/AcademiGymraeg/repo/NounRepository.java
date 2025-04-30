package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.projections.NounIdOnly;

/**
 * @author ptg22svs
 */

public interface NounRepository extends JpaRepository<Noun, Long> {
	/**
	 * 
	 * Returns a {@link List} of the Noun IDs using the {@link NounIdOnly}
	 * projection
	 * 
	 * @return a {@link List} of the Noun IDs
	 */
	List<NounIdOnly> findAllBy();

	/**
	 * 
	 * Returns a {@link List} of {@link Noun} where the id is in a provided
	 * {@link List} of {@link Long}
	 * 
	 * @param ids a {@link List} of {@link Long} containing noun IDs
	 * @return {@link List} of {@link Noun} from the provided IDs
	 */
	List<Noun> findAllByIdIn(List<Long> ids);
}
