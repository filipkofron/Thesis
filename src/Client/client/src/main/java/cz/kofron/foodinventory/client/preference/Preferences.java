package cz.kofron.foodinventory.client.preference;

import android.content.Context;
import android.content.SharedPreferences;

import cz.kofron.foodinventory.client.activity.MainActivity;

/**
 * Created by kofee on 25.3.14.
 */
public class Preferences
{
	public static SharedPreferences getPreferences(Context context)
	{
		return context.getSharedPreferences("cz.kofron.foodinventory.client", MainActivity.MODE_PRIVATE);
	}
}
