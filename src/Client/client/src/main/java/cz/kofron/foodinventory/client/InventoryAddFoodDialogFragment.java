package cz.kofron.foodinventory.client;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by kofee on 3/5/14.
 */
public class InventoryAddFoodDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.inventory_add_food, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(R.string.title_inventory_add_food);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.cancel, onCancelListener);
        builder.setPositiveButton(R.string.add, onAddListener);

        return builder.create();
    }

    private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            System.out.println("Canceled.");
        }
    };
    private DialogInterface.OnClickListener onAddListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            System.out.println("Add.");
        }
    };

    public void show(FragmentActivity activity)
    {
        InventoryAddFoodDialogFragment frag = new InventoryAddFoodDialogFragment();
        frag.show(activity.getSupportFragmentManager(), "invetory_add_food");
    }
}
