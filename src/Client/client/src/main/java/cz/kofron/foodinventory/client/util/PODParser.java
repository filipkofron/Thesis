package cz.kofron.foodinventory.client.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.PODResult;

/**
 * Created by kofee on 26.3.14.
 */
public class PODParser
{
	public static ArrayList<PODResult> parsePODs(String data)
	{
		ArrayList<PODResult> results = new ArrayList<>();
		try
		{
			JSONObject obj = new JSONObject(data);

			int resNum = obj.getInt("nhits");

			if(!obj.isNull("records"))
			{
				JSONArray records = obj.getJSONArray("records");

				for(int i = 0; i < resNum; i++)
				{
					if(!records.isNull(i))
					{
						JSONObject record = records.optJSONObject(i);
						if (!record.isNull("fields"))
						{
							JSONObject fields = record.getJSONObject("fields");
							results.add(parseResult(fields));
						}
					}
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		return results;
	}

	private static PODResult parseResult(JSONObject obj) throws JSONException
	{
		PODResult pod = new PODResult(obj.getString("gtin_cd"));

		if(!obj.isNull("gtin_nm"))
		{
			pod.setName(obj.getString("gtin_nm"));
		}
		if(!obj.isNull("brand_nm"))
		{
			pod.setVendor(obj.getString("brand_nm"));
		}
		if(!obj.isNull("gtin_img"))
		{
			pod.setImage(Download.downloadImage(obj.getString("gtin_img")));
		}

		return pod;
	}
}
