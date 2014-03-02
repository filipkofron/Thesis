package cz.kofron.foodinventory.client;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Filip Kofron on 3/1/14.
 */
public class FoodListAdapter extends ArrayAdapter {

    private Context context;

    FoodListAdapter(Context context) {

        super(context, R.layout.food_list_item);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_list_item, parent, false);
        TextView foodName = (TextView) view.findViewById(R.id.food_list_item_food_name);
        TextView foodGtin = (TextView) view.findViewById(R.id.food_list_item_food_gtin);
        View card = view.findViewById(R.id.food_list_item_card);

        int d = 255;
        if((position & 1) == 1)
        {
            d = 248;
        }
        card.setBackgroundColor(Color.argb(255, d, d, d));

        foodName.setText(foodName.getText() + " #" + (position + 1));
        foodGtin.setText(foodGtin.getText() + " 38166351763");

        return view;
    }

    @Override
    public int getCount() {
        return 40;
    }
}
