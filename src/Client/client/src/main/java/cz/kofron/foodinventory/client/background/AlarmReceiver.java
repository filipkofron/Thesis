package cz.kofron.foodinventory.client.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by kofee on 25.3.14.
 */
public class AlarmReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		IntentoryCheck.checkInventory(context);
	}
}
