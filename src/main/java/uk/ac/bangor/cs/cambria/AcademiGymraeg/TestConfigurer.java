package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.NounRepository;

/**
 * @author dwp22pzv, jcj23xfb
 */

@Component
public class TestConfigurer {

	@Autowired
	private NounRepository nounRepo;

	private QuestionConfigurer questionConfig;
	private static final Logger logger = LoggerFactory.getLogger(TestConfigurer.class);

	public TestConfigurer(QuestionConfigurer questionConfig) {
		this.questionConfig = questionConfig;
	}

	/**
	 * 
	 * Generates a number of questions for a given {@link Test}
	 * 
	 * @param test the {@link Test} to generate a number of {@link Question} for
	 */
	public void generateQuestionsForTest(Test test) {

		if (test.getNumberOfQuestions() <= 0) {
			logger.debug("No questions requested");
			throw new IllegalArgumentException("Unable to generate a test with no questions");
		}

		List<Long> allNounIds = nounRepo.findAllBy().stream().map(n -> n.getId())
				.collect(Collectors.toCollection(ArrayList::new));

		int nounCount = allNounIds.size();

		if (nounCount < test.getNumberOfQuestions()) {
			logger.debug("Not enough nouns in order to generate unique questions");
			throw new IllegalArgumentException("Not enough nouns in order to generate unique questions");
		}

		Collections.shuffle(allNounIds);
		List<Long> selectedNounIds = allNounIds.subList(0, test.getNumberOfQuestions());

		List<Noun> selectedNouns = nounRepo.findAllByIdIn(selectedNounIds);

		questionConfig.configureQuestion(selectedNouns, test);

	}

}
