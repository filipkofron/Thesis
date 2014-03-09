package cz.kofron.foodinventory.client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.kofron.foodinventory.client.R;

/**
 * Created by kofee on 3/8/14.
 */
public class HomeFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.home, container, false);

		return view;
	}
}
