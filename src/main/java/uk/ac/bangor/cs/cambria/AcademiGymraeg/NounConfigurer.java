package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	@PostConstruct
	void loadNounsFromCsv() {
		if (repo.count() > 0)
			return;

		if (csvFile == null)
			return;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {

			CSVParser parser = format.parse(reader);

			for (CSVRecord record : parser) {
				String english = record.get("English");
				String welsh = record.get("Welsh");
				String genderStr = record.get("Gender");

				Gender gender;

				try {
					gender = Gender.valueOf(genderStr.toUpperCase());
				} catch (IllegalArgumentException ex) {
					logger.warn("Unknown gender '{}'. Skipping record: {}", genderStr, record);
					continue;
				}

				repo.save(new Noun(english, welsh, gender));

			}

		} catch (FileNotFoundException e) {
			logger.error("CSV file not found at expected path: {}", csvFile.getFilename(), e);
		} catch (IOException e) {
			logger.error("Error reading from the nouns CSV file: {}", csvFile.getFilename(), e);
		}

	}

}