package cz.kofron.foodinventory.client.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.background.AlarmScheduler;
import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;
import cz.kofron.foodinventory.client.fragment.AccountSelectDialogFragment;
import cz.kofron.foodinventory.client.fragment.AddScanFragment;
import cz.kofron.foodinventory.client.fragment.FoodSearchFragment;
import cz.kofron.foodinventory.client.fragment.InventoryListFragment;
import cz.kofron.foodinventory.client.fragment.NavigationDrawerFragment;
import cz.kofron.foodinventory.client.fragment.RemoveScanFragment;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.preference.Preferences;
import cz.kofron.foodinventory.client.protocol.message.MessageInitializer;

public class MainActivity extends ActionBarActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks, ReloadCallback
{

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private Fragment currentFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private int lastPosition = 0;

	static
	{
		MessageInitializer.initialize();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));


		ConnectionDialogManager.initialize(this);
		tryConnect();

		boolean firstLaunch = Preferences.getPreferences(this).getBoolean("firstLaunch", true);
		if(firstLaunch)
		{
			AlarmScheduler.scheduleAlarm(this);
			SharedPreferences.Editor editor = Preferences.getPreferences(this).edit();
			editor.putBoolean("firstLaunch", false);
			editor.commit();
		}
	}

	public void tryConnect()
	{
		runOnUiThread(new AccountEnforcer());
	}

	@Override
	public void update()
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				onNavigationDrawerItemSelected(lastPosition);
			}
		});
	}

	private class AccountEnforcer implements Runnable
	{
		@Override
		public void run()
		{
			String account = Preferences.getPreferences(MainActivity.this).getString("username", "");
			if(account == null || account.trim().length() < 3)
			{
				new AccountSelectDialogFragment().show(MainActivity.this);
			}
			else
			{
				NetworkInstance.prepare(account);
				NetworkInstance.connector.start();
			}
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		if(NetworkInstance.isPrepared())
		{
			NetworkInstance.connector.stop();
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position)
	{
		// update the main content by replacing fragments
		Fragment fragment = null;

		lastPosition = position;
		switch (position)
		{
			case 0:
				fragment = new InventoryListFragment();
				break;
			case 1:
				fragment = new FoodSearchFragment();
				break;
			case 2:
				fragment = new AddScanFragment();
				break;
			case 3:
				fragment = new RemoveScanFragment();
				break;
		}

		if (fragment == null)
		{
			fragment = new InventoryListFragment();
			mTitle = getString(R.string.title_inventory);
			getSupportActionBar().setTitle(mTitle);
		}

		currentFragment = fragment;

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, fragment)
				.commit();
	}

	@Override
	public void onSectionAttached(int number)
	{
		switch (number)
		{
			case 0:
				mTitle = getString(R.string.title_inventory);
				break;
			case 1:
				mTitle = getString(R.string.title_search_food);
				break;
			case 2:
				mTitle = getString(R.string.title_add_via_barcode);
				break;
			case 3:
				mTitle = getString(R.string.title_remove_via_barcode);
				break;
		}
		getSupportActionBar().setTitle(mTitle);
	}

	public void restoreActionBar()
	{
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if (!mNavigationDrawerFragment.isDrawerOpen())
		{
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			int menuId = R.menu.main;
			getMenuInflater().inflate(menuId, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		ConnectionDialogManager.initialize(this);
	}
}
