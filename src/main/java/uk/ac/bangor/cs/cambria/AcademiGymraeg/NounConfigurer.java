package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.NounRepository;

/**
 * @author thh21bgf jcj23xfb
 */

@Component
public class NounConfigurer {

	@Autowired
	private NounRepository repo;

	private static final Logger logger = LoggerFactory.getLogger(NounConfigurer.class);
	private static final CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true)
			.setIgnoreEmptyLines(true).setTrim(true).build();

	@Value("classpath:/nouns.csv")
	Resource csvFile;

	/**
	 * Loads the default set of nouns from the CSV file
	 */
	@PostConstruct
	void loadNounsFromCsv() {
		if (repo.count() > 0)
			return;

		if (csvFile == null) {
			logger.warn("Unable to load from CSV, as csvFile null");
			return;
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {

			CSVParser parser = format.parse(reader);
			List<Noun> nouns = new ArrayList<Noun>();

			for (CSVRecord record : parser) {
				String english = record.get("English").toLowerCase();
				String welsh = record.get("Welsh").toLowerCase();
				String genderStr = record.get("Gender").toUpperCase();

				Gender gender;

				try {
					gender = Gender.valueOf(genderStr);
				} catch (IllegalArgumentException ex) {
					logger.warn("Unknown gender '{}'. Skipping record: {}", genderStr, record);
					continue;
				}

				nouns.add(new Noun(english, welsh, gender));

			}

			repo.saveAll(nouns);
			logger.debug("Nouns added to database");

		} catch (FileNotFoundException e) {
			logger.error("CSV file not found at expected path: {}", csvFile.getFilename(), e);
		} catch (IOException e) {
			logger.error("Error reading from the nouns CSV file: {}", csvFile.getFilename(), e);
		}

	}

}