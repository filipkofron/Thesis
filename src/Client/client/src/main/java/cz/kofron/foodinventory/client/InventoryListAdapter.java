package cz.kofron.foodinventory.client;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by kofee on 3/2/14.
 */
public class InventoryListAdapter extends ArrayAdapter {
    private Context context;

    InventoryListAdapter(Context context) {

        super(context, R.layout.inventory_list_item);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
        TextView foodName = (TextView) view.findViewById(R.id.inventory_list_item_food_name);
        View card = view.findViewById(R.id.inventory_list_item_card);

        int d = 255;
        if((position & 1) == 1)
        {
            d = 248;
        }
        card.setBackgroundColor(Color.argb(255, d, d, d));
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InventoryFoodDialogFragment(R.string.title_inventory_food).show((FragmentActivity) context);
            }
        });

        foodName.setText(foodName.getText() + " #" + (position + 1));

        return view;
    }

    @Override
    public int getCount() {
        return 20;
    }
}
