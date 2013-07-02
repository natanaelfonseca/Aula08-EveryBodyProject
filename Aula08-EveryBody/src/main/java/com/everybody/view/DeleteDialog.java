package com.everybody.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.everybody.R;
import com.everybody.controller.MainActivity;

/**
 * Created by natanaelfonseca on 01/07/13.
 */
public class DeleteDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setIcon( R.drawable.ic_warning )
                .setTitle( R.string.confirm_delete_dialog  )
                .setMessage( R.string.delete_dialog  )
                .setPositiveButton(R.string.delete_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity)getActivity()).doDeleteSelectedContact();
                            }
                        }
                )
                .setNegativeButton(R.string.delete_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                )
                .create();
    }
}
