package cz.kofron.foodinventory.client.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;
import cz.kofron.foodinventory.client.fragment.GtinCaptureScanFragment;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 25.3.14.
 */
public class GtinCaptureActivity extends ActionBarActivity implements OnGtinSelectListener, ReloadCallback
{
	
	/** The on gtin select listener. */
	private OnGtinSelectListener onGtinSelectListener;

	/** The initial on gtin select listener. */
	public static OnGtinSelectListener initialOnGtinSelectListener;

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		onGtinSelectListener = initialOnGtinSelectListener;
		initialOnGtinSelectListener = null;

		getSupportActionBar().setTitle(R.string.title_gtin_capture);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.gtin_capture);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.container, new GtinCaptureScanFragment(this));
		ft.commit();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.activity.OnGtinSelectListener#onGtinSelected(java.lang.String)
	 */
	@Override
	public void onGtinSelected(String gtin)
	{
		if(onGtinSelectListener != null)
		{
			onGtinSelectListener.onGtinSelected(gtin);
		}
		finish();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				this.finish();
				return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		ConnectionDialogManager.initialize(this);
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.adapter.ReloadCallback#update()
	 */
	@Override
	public void update()
	{

	}
}
