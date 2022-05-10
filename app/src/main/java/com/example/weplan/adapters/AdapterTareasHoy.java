package com.example.weplan.adapters;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weplan.R;
import com.example.weplan.database.TareaContract;
import com.example.weplan.database.*;

import java.util.concurrent.RecursiveAction;

public class AdapterTareasHoy extends RecyclerView.Adapter<AdapterTareasHoy.ViewHolder>{
    private Cursor items;
    Context context;
    View.OnClickListener mOnItemClickListener;

    public Cursor getCursor(){return items;}
    public void setCursor(Context newContext, Cursor newCursor){
        context = newContext;
        items = newCursor;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AdapterTareasHoy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarea_recyclerview_tareas_hoy, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTareasHoy.ViewHolder holder, int position) {

        items.moveToPosition(position);
        int taskname_column = items.getColumnIndex(TareaContract.TareaEntry.COLUMN_TASKNAME);
        int taskdone_column = items.getColumnIndex(TareaContract.TareaEntry.COLUMN_DONE);

        String name = items.getString(taskname_column);
        int done = items.getInt(taskdone_column);

        holder.tarea.setOnCheckedChangeListener(null);

        //pongo la información donde toca
        holder.tarea.setText(name);
        boolean a = (done !=0);
        holder.tarea.setChecked(a);

        holder.tarea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    String nombre_tarea = holder.tarea.getText().toString();
                    String app = "WePlan";

                    TareaHelper dbHelper = new TareaHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(TareaContract.TareaEntry.COLUMN_DONE, 1);

                    String selection = TareaContract.TareaEntry.COLUMN_TASKNAME + " LIKE ?";
                    String[] selectionArgs = {nombre_tarea};

                    int count = db.update(
                            TareaContract.TareaEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs
                    );
                }
                else{
                    String nombre_tarea = holder.tarea.getText().toString();
                    String app = "WePlan";

                    TareaHelper dbHelper = new TareaHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(TareaContract.TareaEntry.COLUMN_DONE, 0);

                    String selection = TareaContract.TareaEntry.COLUMN_TASKNAME + " LIKE ?";
                    String[] selectionArgs = {nombre_tarea};

                    int count = db.update(
                            TareaContract.TareaEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs
                    );
                }
            }
        });




     /*holder.tarea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

              if(holder.tarea.isChecked()){
                 String nombre_tarea = holder.tarea.getText().toString();
                  String app = "WePlan";

                  TareaHelper dbHelper = new TareaHelper(context);
                  SQLiteDatabase db = dbHelper.getWritableDatabase();

                  ContentValues values = new ContentValues();
                  values.put(TareaContract.TareaEntry.COLUMN_DONE, 1);

                  String selection = TareaContract.TareaEntry.COLUMN_TASKNAME + " LIKE ?";
                  String[] selectionArgs = {nombre_tarea};

                  int count = db.update(
                          TareaContract.TareaEntry.TABLE_NAME,
                          values,
                          selection,
                          selectionArgs
                  );


             }
              notifyDataSetChanged();


          }
      });*/


    }

    @Override
    public int getItemCount() {
        return (items !=null) ? items.getCount():0/*tasks.size()*/;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CheckBox tarea;

        public ViewHolder(@NonNull View view) {
            super(view);

            tarea = view.findViewById(R.id.checkboxtarea);
            view.setTag(this);

        }

        public void onClick(View v){

        }
    }
}
