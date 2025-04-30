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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;

/**
 * @author ptg22svs, cnb22xdk, jcj23xfb
 */

@Entity
public class Noun {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;

	/**
	 * Welsh-language representation of the noun.
	 * 
	 * Only allows Unicode letters, spaces, apostrophes, and hyphens.
	 */
	@Pattern(regexp = "^[\\p{L} '\\-]+$")
	@NotBlank
	@Column(nullable = false)
	private String welshNoun;

	/**
	 * Enlish-language representation of the noun.
	 * 
	 * Only allows Unicode letters, spaces, apostrophes, and hyphens.
	 */
	@Pattern(regexp = "^[\\p{L} '\\-]+$")
	@NotBlank
	@Column(nullable = false)
	private String englishNoun;

	@NotNull
	@Column(nullable = false)
	private Gender gender;

	public Noun(String englishNoun, String welshNoun, Gender gender) {
		this.englishNoun = englishNoun;
		this.welshNoun = welshNoun;
		this.gender = gender;
	}

	public Noun() {
	}

	public String getWelshNoun() {
		return welshNoun;
	}

	public void setWelshNoun(String welshNoun) {
		this.welshNoun = welshNoun;
	}

	public String getEnglishNoun() {
		return englishNoun;
	}

	public void setEnglishNoun(String englishNoun) {
		this.englishNoun = englishNoun;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Long getNounId() {
		return id;
	}

	public void setNounId(Long nounId) {
		this.id = nounId;
	}

	/**
	 * 
	 * Converts the {@link Noun} object to a {@link String} using a pipe
	 * 
	 * @return the {@link Noun} object as a {@link String} in the form
	 *         welshNoun|englishNoun
	 * 
	 */
	@Override
	public String toString() {
		return this.welshNoun + " | " + this.englishNoun;
	}

}
