package cz.kofron.foodinventory.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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

    private class RowClickListener implements View.OnClickListener
    {
        private int position;

        private RowClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            System.out.println("On click: " + view);
            Intent intent = new Intent(context, FoodDetailActivity.class);
            context.startActivity(intent);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_list_item, parent, false);

        TextView foodName = (TextView) view.findViewById(R.id.food_list_item_food_name);
        TextView foodGtin = (TextView) view.findViewById(R.id.food_list_item_food_gtin);
        View card = view.findViewById(R.id.food_list_item_card);

        ImageButton ob = (ImageButton) view.findViewById(R.id.add_button);
        final int positionNow = position;
        ob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InventoryAddFoodDialogFragment().show((FragmentActivity) context);
            }
        });

        card.setOnClickListener(new RowClickListener(position));

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