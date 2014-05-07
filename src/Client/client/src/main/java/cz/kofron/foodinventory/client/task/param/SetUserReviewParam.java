package cz.kofron.foodinventory.client.task.param;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 24.3.14.
 */
public class SetUserReviewParam
{
	
	/** The rating. */
	public float rating;
	
	/** The delete. */
	public boolean delete;
	
	/** The food id. */
	public int foodId;
	
	/** The text. */
	public String text;

	/**
	 * Instantiates a new sets the user review param.
	 *
	 * @param rating the rating
	 * @param delete the delete
	 * @param foodId the food id
	 * @param text the text
	 */
	public SetUserReviewParam(float rating, boolean delete, int foodId, String text)
	{
		this.rating = rating;
		this.delete = delete;
		this.foodId = foodId;
		this.text = text;
	}
}
