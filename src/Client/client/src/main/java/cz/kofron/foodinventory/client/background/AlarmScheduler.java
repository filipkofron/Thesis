package cz.kofron.foodinventory.client.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import cz.kofron.foodinventory.client.preference.Preferences;


// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 25.3.14.
 */
public class AlarmScheduler
{
	
	/**
	 * Make pending intent.
	 *
	 * @param context the context
	 * @return the pending intent
	 */
	private static PendingIntent makePendingIntent(Context context)
	{
		Intent intent = new Intent(context, AlarmReceiver.class);
		return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/**
	 * Schedule alarm.
	 *
	 * @param context the context
	 */
	public static void scheduleAlarm(Context context)
	{
		//Long time = new GregorianCalendar().getTimeInMillis() + 12 * 60 * 60 * 1000;

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
		int hours = Preferences.getPreferences(context).getInt("notifications_check_frequency", 6);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), hours * 60 * 60 * 1000, makePendingIntent(context));
	}

	/**
	 * Cancel alarm.
	 *
	 * @param context the context
	 */
	public static void cancelAlarm(Context context)
	{
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(makePendingIntent(context));
	}
}
