package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.graphics.Color;
import android.provider.BaseColumns;
import android.widget.TextView;

import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class GraficoPuntuarDia extends AppCompatActivity {


    TextView tvExcelente, tvRegular, tvMal;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_puntuar_dia);

        tvExcelente = findViewById(R.id.tvExcelente);
        tvRegular = findViewById(R.id.tvRegular);
        tvMal = findViewById(R.id.tvMal);
        pieChart = findViewById(R.id.piechart);

        setData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void setData()
    {
        int tres = mostrarPuntuacion("3");
        int dos = mostrarPuntuacion("2");
        int uno = mostrarPuntuacion("1");

        tvExcelente.setText(Integer.toString(mostrarPuntuacion("3")));
        tvRegular.setText(Integer.toString(mostrarPuntuacion("2")));
        tvMal.setText(Integer.toString(mostrarPuntuacion("1")));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Excelente",
                        Integer.parseInt(tvExcelente.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Regular",
                        Integer.parseInt(tvRegular.getText().toString()),
                        Color.parseColor("#29B6F6")));

        pieChart.addPieSlice(
                new PieModel(
                        "Mal",
                        Integer.parseInt(tvMal.getText().toString()),
                        Color.parseColor("#EF5350")));


        pieChart.startAnimation();
    }

    public int mostrarPuntuacion(String tipo){
        TareaHelper dbHelper = new TareaHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                TareaContract.PuntuacionEntry.COLUMN_DATE,
                TareaContract.PuntuacionEntry.COLUMN_PUNTOS
        };


        String selection = TareaContract.PuntuacionEntry.COLUMN_PUNTOS + " = ?";
        String[] selectionArgs = {tipo};

        Cursor cursor = db.query(
                TareaContract.PuntuacionEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int cantidad;
        cantidad= cursor.getCount();


        return cantidad;


    }
}
