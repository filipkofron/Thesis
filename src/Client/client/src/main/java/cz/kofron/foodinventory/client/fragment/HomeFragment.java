package cz.kofron.foodinventory.client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.MainActivity;

/**
 * Created by kofee on 3/8/14.
 */
public class HomeFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.home, container, false);

		View homeAdd = (View) view.findViewById(R.id.home_add);
		View homeRemove = (View) view.findViewById(R.id.home_remove);
		View homeBrowse = (View) view.findViewById(R.id.home_browse);
		View homeSearch = (View) view.findViewById(R.id.home_search);

		homeAdd.setClickable(true);
		homeAdd.setFocusable(true);
		homeAdd.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				onFragmentSelected(new AddScanFragment());
				((MainActivity) getActivity()).onSectionAttached(3);
			}
		});

		homeRemove.setClickable(true);
		homeRemove.setFocusable(true);
		homeRemove.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				onFragmentSelected(new RemoveScanFragment());
				((MainActivity) getActivity()).onSectionAttached(4);
			}
		});

		homeBrowse.setClickable(true);
		homeBrowse.setFocusable(true);
		homeBrowse.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				onFragmentSelected(new InventoryListFragment());
				((MainActivity) getActivity()).onSectionAttached(1);
			}
		});

		homeSearch.setClickable(true);
		homeSearch.setFocusable(true);
		homeSearch.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				onFragmentSelected(new FoodSearchFragment());
				((MainActivity) getActivity()).onSectionAttached(2);
			}
		});

		return view;
	}

	private void onFragmentSelected(Fragment fragment)
	{
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, fragment)
				.commit();
	}
}
