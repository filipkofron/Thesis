package cz.kofron.foodinventory.client.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


/**
 * Created by kofee on 25.3.14.
 */
public class AlarmScheduler
{
	public static void scheduleAlarm(Context context)
	{
		//Long time = new GregorianCalendar().getTimeInMillis() + 12 * 60 * 60 * 1000;

		Intent intentAlarm = new Intent(context, AlarmReceiver.class);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 6 * 60 * 60 * 1000, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
	}
}
