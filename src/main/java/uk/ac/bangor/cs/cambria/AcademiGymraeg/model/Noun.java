/**
 * This model class represents Noun objects, which form the subject of a given question on a test.
 * This class is marked with the @Entity attribute, and will therefore be mirrored in the database, as the Noun TABLE
 */
package uk.ac.bangor.cs.cambria.AcademiGymraeg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;

/**
 * @author ptg22svs, cnb22xdk
 */

@Entity
public class Noun {

	/**
	 * id attribute. The unique identifier for each Noun object, and the primary key
	 * for the Noun TABLE in the database. The value will be autogenerated, and
	 * cannot be updated once created.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;

	/**
	 * welshNoun attribute. The Welsh language representation of the Noun.
	 */
	@Column(nullable = false)
	private String welshNoun;

	/**
	 * englishNoun attribute. The English language representation of the Noun.
	 */
	@Column(nullable = false)
	private String englishNoun;

	/**
	 * gender attribute. Represents the gender classification for the Welsh language
	 * representation of the noun. Attribute is of type "gender", which allows 3
	 * values: Masculine, Feminine and Plural
	 */
	@Column(nullable = false)
	private Gender gender;

	public Noun(String eng, String welsh, Gender gender) {
		this.welshNoun = welsh;
		this.englishNoun = eng;
		this.gender = gender;
	}

	/**
	 * @return The value of "welshNoun"
	 */
	public String getWelshNoun() {
		return welshNoun;
	}

	/**
	 * @param welshNoun=the value to assign to welshNoun
	 */
	public void setWelshNoun(String welshNoun) {
		this.welshNoun = welshNoun;
	}

	/**
	 * @return The value of "englishNoun"
	 */
	public String getEnglishNoun() {
		return englishNoun;
	}

	/**
	 * @param englishNoun=the value to assign to EnglishNoun
	 */
	public void setEnglishNoun(String englishNoun) {
		this.englishNoun = englishNoun;
	}

	/**
	 * @return The value of "gender"
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender=the value to assign to Gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setId(Long nounId) {
		this.id = nounId;
	}

	/**
	 * @return The value of "id"
	 */
	public Long getId() {
		return id;
	}

}
