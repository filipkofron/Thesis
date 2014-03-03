package cz.kofron.foodinventory.client;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kofee on 3/2/14.
 */
public class InventoryListFragment extends ListFragment implements OnFragmentMenuCreationListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new InventoryListAdapter(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inventory_list_fragment, container, false);
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
}
