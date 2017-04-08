package com.example.anshuman_hp.todo_list;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Anshuman-HP on 07-04-2017.
 */

public class AddDialogFragment extends DialogFragment {
    NumberPicker priority;
    EditText todoDescription;
    ProgressBar progressBar;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.dialog,null);
        priority=(NumberPicker)v.findViewById(R.id.numberPicker);
        priority.setMaxValue(10);
        priority.setMinValue(0);
        priority.computeScroll();
        priority.setWrapSelectorWheel(true);
        priority.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING);
        todoDescription=(EditText)v.findViewById(R.id.tododescription);
        progressBar=(ProgressBar)v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        builder.setView(v)
                .setPositiveButton("Add Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        Log.e("listitem",todoDescription.getText().toString()+String.valueOf(priority.getValue()));
                        listItem item=new listItem(todoDescription.getText().toString(),String.valueOf(priority.getValue()));
                        FirebaseDatabase
                                .getInstance()
                                .getReference()
                                .child("Todo")
                                .push()
                                .setValue(item);
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        progressBar.setVisibility(View.GONE);
//                                        dialog.dismiss();
//                                    }
//                                });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(),"Operation Cancelled",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });
        return builder.create();

    }
}
