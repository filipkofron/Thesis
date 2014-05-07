package cz.kofron.foodinventory.client.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import cz.kofron.foodinventory.client.preference.Preferences;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 25.3.14.
 */
public class BootReceiver extends BroadcastReceiver
{
	
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent)
	{
		AlarmScheduler.scheduleAlarm(context);
		SharedPreferences.Editor editor = Preferences.getPreferences(context).edit();
		editor.putBoolean("firstLaunch", false);
		editor.commit();
	}
}
