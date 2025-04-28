package uk.ac.bangor.cs.cambria.AcademiGymraeg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.NounRepository;

/**
 * @author dwp22pzv, jcj23xfb
 */

@Component
public class TestConfigurer {

	@Autowired
	private NounRepository nounRepo;

	private Random rand = new Random();

	private QuestionConfigurer questionConfig;

	@Autowired
	public TestConfigurer(QuestionConfigurer questionConfig) {
		this.questionConfig = questionConfig;
	}

	/**
	 * Gets the required number of Nouns from the repo and passes them to
	 * QuestionConfigurer. This can be a temporary solution, just doing it here to
	 * help with linking QuestionConfigurer to tests in general. Checks for
	 * duplicates, so should only get unique nouns. Once this is done, the Test can
	 * find it's associated questions by looking in the Question repo for any
	 * Questions with the matching Test object.
	 * 
	 * @param Test test: the Test object to generate questions for.
	 */
	public void generateQuestionsForTest(Test test) {

		if (test.getNumberOfQuestions() <= 0) {
			throw new IllegalArgumentException(
					"Test method .getNumberOfQuestions returned a value less than 1. This value needs to be at least 1 before questions can be generated for the test.");
		}

		List<Long> allNounIds = nounRepo.findAllBy().stream().map(n -> n.getId())
				.collect(Collectors.toCollection(ArrayList::new));

		int nounCount = allNounIds.size();

		if (nounCount < test.getNumberOfQuestions()) {
			throw new IllegalArgumentException("Not enough nouns in order to generate unique questions");
		}

		Collections.shuffle(allNounIds);
		List<Long> selectedNounIds = allNounIds.subList(0, test.getNumberOfQuestions());

		List<Noun> selectedNouns = nounRepo.findAllByIdIn(selectedNounIds);

		questionConfig.configureQuestion(selectedNouns, test);

	}

	/**
	 * Gets the required number of Nouns from the repo and passes them to
	 * QuestionConfigurer. This can be a temporary solution, just doing it here to
	 * help with linking QuestionConfigurer to tests in general. Checks for
	 * duplicates, so should only get unique nouns. Once this is done, the Test can
	 * find it's associated questions by looking in the Question repo for any
	 * Questions with the matching Test object.
	 * 
	 * @param Test test: the Test object to generate questions for.
	 */
	public void generateQuestionsForTestOld(Test test) {

		if (test.getNumberOfQuestions() <= 0) {
			throw new IllegalArgumentException(
					"Test method .getNumberOfQuestions returned a value less than 1. This value needs to be at least 1 before questions can be generated for the test.");
		}

		List<Noun> allNouns = nounRepo.findAll();
		int nounCount = allNouns.size(); /*
											 * Is it more performant to count the repo directly, or should this count
											 * the list that was pulled from the repo instead, since we need to get the
											 * list anyway?
											 */

		if (nounCount < test.getNumberOfQuestions()) {
			throw new IllegalArgumentException("Not enough nouns in order to generate unique questions");
		}

		Collections.shuffle(allNouns);
		List<Noun> selectedNouns = allNouns.subList(0, test.getNumberOfQuestions());

		questionConfig.configureQuestion(selectedNouns, test);

	}

}
