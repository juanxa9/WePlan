package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.TextView;
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

        tvExcelente.setText(Integer.toString(30));
        tvRegular.setText(Integer.toString(5));
        tvMal.setText(Integer.toString(25));

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
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Mal",
                        Integer.parseInt(tvMal.getText().toString()),
                        Color.parseColor("#29B6F6")));


        pieChart.startAnimation();
    }
}
