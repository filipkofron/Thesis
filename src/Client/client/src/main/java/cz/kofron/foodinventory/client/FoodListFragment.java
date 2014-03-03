package cz.kofron.foodinventory.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Filip Kofron on 3/1/14.
 */
public class FoodListFragment extends ListFragment implements OnFragmentMenuCreationListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new FoodListAdapter(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.food_list_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.food_list, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public int onGetMenuId() {
        return R.menu.food_list;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("On list item click position: " + position);
    }
}
