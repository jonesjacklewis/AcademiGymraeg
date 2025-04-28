package uk.ac.bangor.cs.cambria.AcademiGymraeg;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.QuestionRepository;

/**
 * @author dwp22pzv, jcj23xfb, cnb22xdk
 */

@Component
public class QuestionConfigurer {

    private final QuestionRepository repo;
    @Autowired
    public QuestionConfigurer(QuestionRepository repo) {
    	this.repo = repo;
    }

    /**
     * For a given list of nouns, generate questions with an evenly split selection of question types.
     *  @param List<Noun> nouns: A list of noun objects from which to derive questions
     *  @param Test test: the test object these questions should be associated with.
     */
    public void configureQuestionOld(List<Noun> nouns, Test test){

        int i = 0;

        for (Noun noun : nouns){

            if( i >= QuestionType.values().length) { i = 0;} /*Loop the counter back around so it can only ever be within the range of question types. Not hardcoding the current 3 types in order to allow for future expansion. */
            
            QuestionType questionType = QuestionType.values()[i];

            Question newQuestion = new Question( noun, questionType,  test);
            
            repo.save(newQuestion);
            
            i++;
        }
        test.setQuestionsWithRepo(repo);
    }
    
    /**
     * For a given list of nouns, generate questions with an evenly split selection of question types.
     *  @param List<Noun> nouns: A list of noun objects from which to derive questions
     *  @param Test test: the test object these questions should be associated with.
     */
    public void configureQuestion(List<Noun> nouns, Test test){

        int questionTypeIndex = 0;
        
        List<Question> questions = new ArrayList<Question>();

        for (Noun noun : nouns){

            if( questionTypeIndex >= QuestionType.values().length) { questionTypeIndex = 0;} /*Loop the counter back around so it can only ever be within the range of question types. Not hardcoding the current 3 types in order to allow for future expansion. */
            
            QuestionType questionType = QuestionType.values()[questionTypeIndex];

            Question newQuestion = new Question( noun, questionType,  test);
            
            questions.add(newQuestion);
            
            questionTypeIndex++;
        }
        
        repo.saveAll(questions);
        test.setQuestionsWithRepo(repo);
    }

    /**
     * For a given list of nouns, generate questions with a single given question type.
     *  @param List<Noun> nouns: A list of noun objects from which to derive questions
     *  @param Test test: the test object these questions should be associated with.
     *  @param QuestionType questionType: a questionType enum which all generated questions will use.
     */
    public void configureQuestion(List<Noun> nouns, Test test, QuestionType questionType){

        for (Noun noun : nouns){

            Question newQuestion = new Question( noun, questionType,  test);
            
            repo.save(newQuestion);
        }
        test.setQuestionsWithRepo(repo);
    }
}
