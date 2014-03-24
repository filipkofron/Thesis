package cz.kofron.foodinventory.client.task.param;

/**
 * Created by kofee on 24.3.14.
 */
public class SetUserReviewParam
{
	public float rating;
	public boolean delete;
	public int foodId;
	public String text;

	public SetUserReviewParam(float rating, boolean delete, int foodId, String text)
	{
		this.rating = rating;
		this.delete = delete;
		this.foodId = foodId;
		this.text = text;
	}
}
