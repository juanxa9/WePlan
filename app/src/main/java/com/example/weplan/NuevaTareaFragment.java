package com.example.weplan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;

public class NuevaTareaFragment extends DialogFragment {
    String date;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        date = getArguments().getString("date");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_crear_tarea, null))
                // Add action buttons
                .setPositiveButton(R.string.anyadir, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       NuevaTareaFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


    /*public void writeToDataBase(View view){
        String app = getResources().getString(R.string.app_name);

        TareaHelper dbHelper = new TareaHelper();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EditText editText = (EditText) rootView.findViewById(R.id.et_taskName);
        String name = editText.getText().toString();



        ContentValues values = new ContentValues();
        values.put(TareaContract.TareaEntry.COLUMN_TASKNAME, name);
        values.put(TareaContract.TareaEntry.COLUMN_DONE, 0);
        values.put(TareaContract.TareaEntry.COLUMN_DATE, date);

        long newRowId = db.insert(TareaContract.TareaEntry.TABLE_NAME, null, values);
        Intent intent = new Intent(this, TareasParaHoy.class);
        startActivity(intent);
    }*/

}
