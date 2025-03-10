
public class Question {

	
	/**
	 * Id attribute. The unique identifier for each Question object, and the primary key for the Question TABLE in the database.
	 * The value will be autogenerated, and cannot be updated once created.
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@NotBlank
	private int id;
	
}
