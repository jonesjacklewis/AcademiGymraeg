package uk.ac.bangor.cs.cambria.AcademiGymraeg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.projections.NounIdOnly;

/**
 * @author ptg22svs
 */

public interface NounRepository extends JpaRepository<Noun, Long> {
	List<NounIdOnly> findAllBy();

	List<Noun> findAllByIdIn(List<Long> ids);
}
