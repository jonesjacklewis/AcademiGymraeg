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
		
		Noun n = new Noun();
		n.setEnglishNoun("aeroplane");
		n.setWelshNoun("awyren");
		n.setGender(Gender.FEMININE);
		repo.save(n);
		
		n = new Noun();
		n.setEnglishNoun("apple");
		n.setWelshNoun("afal");
		n.setGender(Gender.MASCULINE);
		repo.save(n);
	
		
		n = new Noun();
		n.setEnglishNoun("book");
		n.setWelshNoun("llyfr");
		n.setGender(Gender.MASCULINE);
		repo.save(n);
	
		
		n = new Noun();
		n.setEnglishNoun("business");
		n.setWelshNoun("busnes");
		n.setGender(Gender.MASCULINE);
		repo.save(n);
	
		
	}
}
