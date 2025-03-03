/**
 * 
 */
package uk.ac.bangor.cs.cambria.AcademiGymraeg.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.Enum.Gender;

@Entity
public class Noun  {

	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@NotBlank
	private int Id;
	
	@Column(nullable = false)
	@NotBlank
	private String WelshNoun;
	
	@Column(nullable = false)
	@NotBlank
	private String EnglishNoun;
	
	@Column(nullable = false)
	@NotBlank
	private Gender Gender;

	/**
	 * @return the welshNoun
	 */
	public String getWelshNoun() {
		return WelshNoun;
	}

	/**
	 * @param welshNoun the welshNoun to set
	 */
	public void setWelshNoun(String welshNoun) {
		WelshNoun = welshNoun;
	}

	/**
	 * @return the englishNoun
	 */
	public String getEnglishNoun() {
		return EnglishNoun;
	}

	/**
	 * @param englishNoun the englishNoun to set
	 */
	public void setEnglishNoun(String englishNoun) {
		EnglishNoun = englishNoun;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return Gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		Gender = gender;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}
	
}

