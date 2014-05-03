package cz.kofron.foodinventory.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.FoodListActivity;
import cz.kofron.foodinventory.client.adapter.FoodSearchAutoCompleteAdapter;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/7/14.
 */
public class FoodSearchFragment extends Fragment
{
	
	/** The actv. */
	private AutoCompleteTextView actv;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.search_food, container, false);

		actv = (AutoCompleteTextView) view.findViewById(R.id.autocomplete);
		actv.setAdapter(new FoodSearchAutoCompleteAdapter(getActivity(), R.layout.list_item));

		Button searchButton = (Button) view.findViewById(R.id.button_search);
		searchButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent foodEditIntent = new Intent(getActivity(), FoodListActivity.class);
				foodEditIntent.putExtra("SEARCH_VALUE", actv.getText().toString());
				startActivity(foodEditIntent);
			}
		});
		return view;
	}
}
