package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.NounRepository;

/**
 * @author jcj23xfb, thh21bgf
 */

@Component
public class NounConfigurer {

	@Autowired
	private NounRepository repo;

	@Value("classpath:/nouns.csv")
	Resource csvFile;

	@PostConstruct
	void setupSomeNouns() {
		if (repo.count() > 0)
			return;

		if (csvFile == null)
			return;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
			String line;

			br.readLine();

			while ((line = br.readLine()) != null) {

				if (!"English,Welsh,Gender".equals(line.trim())) {

					String[] lineParts = line.split(",");

					Gender gender = null;

					switch (lineParts[2].trim()) {
					case "Masculine":
						gender = Gender.MASCULINE;
						break;
					case "Feminine":
						gender = Gender.FEMININE;
						break;
					}

					if (gender != null)
						repo.save(new Noun(lineParts[0], lineParts[1], gender));
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
