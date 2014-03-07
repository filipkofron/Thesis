package cz.kofron.foodinventory.client;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Created by kofee on 3/7/14.
 */
public class FoodSearchAutoCompleteAdapter extends ArrayAdapter implements Filterable {

    public FoodSearchAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);
    }

    private ArrayList<String> resultList = new ArrayList<>();

    public ArrayList<String> autocomplete(String constr)
    {
        ArrayList<String> temp = new ArrayList<>();

        constr = constr.toLowerCase();

        temp.add("Ahoj");
        temp.add("Svete");
        temp.add("Ahoj svete");
        temp.add("Svet smrdi");

        ArrayList<String> results = new ArrayList<>();

        for(String src : temp)
        {
            String str =  src.toLowerCase();
            if(str.contains(constr))
            {
                results.add(src);
            }
        }

        return results;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}
