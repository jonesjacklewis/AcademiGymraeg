package uk.ac.bangor.cs.cambria.AcademiGymraeg;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.QuestionType;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.QuestionRepository;

/**
 * @author dwp22pzv
 */

@Component
public class QuestionConfigurer {

    @Autowired
    private QuestionRepository repo;

    /**
     * For a given list of nouns, generate questions with an evenly split selection of question types.
     *  @param List<Noun> nouns: A list of noun objects from which to derive questions
     *  @param Test test: the test object these questions should be associated with.
     */
    public void configureQuestion(List<Noun> nouns, Test test){

        int i = 0;

        for (Noun noun : nouns){

            if( i >= QuestionType.values().length) { i = 0;} /*Loop the counter back around so it can only ever be within the range of question types. Not hardcoding the current 3 types in order to allow for future expansion. */
            
            QuestionType questionType = QuestionType.values()[i];

            Question newQuestion = new Question( noun, questionType,  test);
            
            repo.save(newQuestion);
            
            i++;
        }
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
    }

    

}
