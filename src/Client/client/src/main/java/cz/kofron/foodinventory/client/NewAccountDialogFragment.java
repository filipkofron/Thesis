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
public class NewAccountDialogFragment extends DialogFragment {
    private View view;
    private Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.signup_dialog, null);
        this.view = view;


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("New account");
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.cancel, onCancelListener);
        builder.setPositiveButton(R.string.ok, onOkListener);

        this.dialog = builder.create();

        return dialog;
    }

    private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    };
    private DialogInterface.OnClickListener onOkListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Would create new account: " + NewAccountDialogFragment.this);
                }
            });
        }
    };

    public void show(FragmentActivity activity)
    {
        show(activity.getSupportFragmentManager(), "new_account_dialog");
    }
}
