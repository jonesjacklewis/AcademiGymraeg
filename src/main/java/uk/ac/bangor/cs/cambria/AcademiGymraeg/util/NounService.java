package uk.ac.bangor.cs.cambria.AcademiGymraeg.util;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.NounRepository;

/**
 * @author ptg22svs
 */

/**
 * All queries to the Noun table in the datastore are handled by this service
 */
@Service
public class NounService {

	@Autowired
	private NounRepository repo;

	private static final Logger logger = LoggerFactory.getLogger(NounService.class);

	/**
	 * 
	 * Gets a {@link List} of {@link Noun} from the database using the
	 * {@link NounRepository}
	 * 
	 * @return a {@link List} of {@link Noun} from the database
	 */
	public List<Noun> getAllNouns() {
		return repo.findAll();
	}

	/**
	 * 
	 * Saves a {@link Noun} to the database using the {@link NounRepository}
	 * 
	 * @param n a {@link Noun} to save
	 * @throws an {@link IllegalArgumentException} if n is null
	 */
	public void saveRecord(Noun n) {
		if (n == null) {
			logger.error("Noun is null");
			throw new IllegalArgumentException("Null Noun argument");
		}

		repo.save(n);
	}

	/**
	 * 
	 * Gets an {@link Optional} of a {@link Noun} from the database using the
	 * {@link NounRepository} for a given {@link Long} noun id
	 * 
	 * @param id {@link Long} noun id
	 * @return {@link Optional} of a {@link Noun} for the given ID
	 * @throws an {@link IllegalArgumentException} if id is null
	 */
	public Optional<Noun> getById(Long id) {
		if (id == null) {
			logger.error("Noun ID null");
			throw new IllegalArgumentException("Null id argument");
		}

		return repo.findById(id);
	}

	/**
	 * 
	 * Deletes a noun from the database using the {@link NounRepository} for a given
	 * {@link Long} noun id
	 * 
	 * @param id {@link Long} noun id
	 * @throws an {@link IllegalArgumentException} if id is null
	 */
	public void deleteById(Long id) {
		if (id == null) {
			logger.error("Noun ID null");
			throw new IllegalArgumentException("Null id argument");
		}

		repo.deleteById(id);
	}
}
