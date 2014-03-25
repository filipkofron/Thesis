package cz.kofron.foodinventory.client.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;
import cz.kofron.foodinventory.client.fragment.GtinCaptureScanFragment;

/**
 * Created by kofee on 25.3.14.
 */
public class GtinCaptureActivity extends ActionBarActivity implements OnGtinSelectListener, ReloadCallback
{
	private OnGtinSelectListener onGtinSelectListener;

	public static OnGtinSelectListener initialOnGtinSelectListener;

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

	@Override
	public void onGtinSelected(String gtin)
	{
		if(onGtinSelectListener != null)
		{
			onGtinSelectListener.onGtinSelected(gtin);
		}
		finish();
	}

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

	@Override
	protected void onResume()
	{
		super.onResume();
		ConnectionDialogManager.initialize(this);
	}

	@Override
	public void update()
	{

	}
}
