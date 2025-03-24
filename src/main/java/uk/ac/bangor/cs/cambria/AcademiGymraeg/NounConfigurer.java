package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.NounRepository;

@Component
public class NounConfigurer {

	@Autowired
	private NounRepository repo;

	@PostConstruct
	void setupSomeNouns() {

		if (repo.count() > 0) {
			return;
		}

		Noun n = new Noun();
		n.setEnglishNoun("Aeroplane");
		n.setWelshNoun("Awyren");
		n.setGender(Gender.FEMININE);
		repo.save(n);

		n = new Noun();
		n.setEnglishNoun("Apple");
		n.setWelshNoun("Afal");
		n.setGender(Gender.MASCULINE);
		repo.save(n);

		n = new Noun();
		n.setEnglishNoun("Book");
		n.setWelshNoun("Llyfr");
		n.setGender(Gender.MASCULINE);
		repo.save(n);

		n = new Noun();
		n.setEnglishNoun("Business");
		n.setWelshNoun("Busnes");
		n.setGender(Gender.MASCULINE);
		repo.save(n);

	}
}
