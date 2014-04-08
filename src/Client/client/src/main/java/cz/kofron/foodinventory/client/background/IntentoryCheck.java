package cz.kofron.foodinventory.client.background;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.MainActivity;
import cz.kofron.foodinventory.client.model.InventoryItem;
import cz.kofron.foodinventory.client.network.Communicator;
import cz.kofron.foodinventory.client.network.Connection;
import cz.kofron.foodinventory.client.network.Connector;
import cz.kofron.foodinventory.client.preference.Preferences;
import cz.kofron.foodinventory.client.protocol.message.GetInventoryResponse;
import cz.kofron.foodinventory.client.util.DateUtil;

/**
 * Created by kofee on 25.3.14.
 */
public class IntentoryCheck
{

	private static class CheckThread extends Thread
	{
		private Context context;
		private ArrayList<InventoryItem> items;

		private CheckThread(Context context)
		{
			this.context = context;
		}

		@Override
		public void run()
		{
			String account = Preferences.getPreferences(context).getString("username", "");

			try
			{
				Communicator communicator = new Communicator(account, false);
				Connection connection = new Connection(Connector.SERVER_ADDR, Connector.SERVER_PORT, false);
				connection.addConnectionListener(communicator);
				connection.connect();

				GetInventoryResponse rip = communicator.getInventory(false, 0, "", "");
				items = rip.getIntentoryItems();

				connection.disconnect();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public ArrayList<InventoryItem> getItems()
		{
			return items;
		}
	}

	public static void checkInventory(Context context)
	{
		CheckThread checkThread = new CheckThread(context);
		checkThread.start();
		try
		{
			checkThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		ArrayList<InventoryItem> items = checkThread.getItems();

		if(items != null)
		{
			processItems(context, items);
		}
	}

	private static void processItems(Context context, ArrayList<InventoryItem> items)
	{
		int days = Preferences.getPreferences(context).getInt("notifications_notify_days", 1);

		ArrayList<InventoryItem> expiringItems = new ArrayList<>();
		for(InventoryItem item : items)
		{
			long msMax = days * DateUtil.ROUGH_MS_PER_DAY;
			
			long msToEnd = item.getUseBy() - System.currentTimeMillis();
			
			if(msMax >= msToEnd)
			{
				expiringItems.add(item);
			}
		}

		if(expiringItems.size() > 0)
		{
			presentExpiring(context, expiringItems);
		}
	}

	private static void presentExpiring(Context context, ArrayList<InventoryItem> items)
	{
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

		String str = "";

		boolean first = true;
		for (InventoryItem item : items)
		{
			if (first)
			{
				str += item.getFoodName();
				first = false;
			}
			else
			{
				str += ", " + item.getFoodName();
			}
		}


		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
				.setContentTitle("Food expires soon!")
				.setContentText(str)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pIntent)
				.setAutoCancel(true);

		notificationManager.notify(0, builder.build());
	}
}
