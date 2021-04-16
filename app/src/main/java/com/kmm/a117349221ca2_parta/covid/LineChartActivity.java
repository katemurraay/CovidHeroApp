package com.kmm.a117349221ca2_parta.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.utils.IConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class LineChartActivity extends AppCompatActivity {
    LineChart lcCases;
    ArrayList<Covid> covidData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        lcCases = findViewById(R.id.lcCovidCases);
        covidData = new ArrayList<>(IConstants.COVID_LIST);
        LineDataSet deathsDataSet = new LineDataSet(lineChartDataSet("deaths"), "Deaths");
        LineDataSet confirmedDataSet = new LineDataSet(lineChartDataSet("confirmed"), "Confirmed");
        LineDataSet activeDataSet = new LineDataSet(lineChartDataSet("active"), "Active");
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(deathsDataSet);
        lineDataSets.add(confirmedDataSet);
        lineDataSets.add(activeDataSet);

        LineData lineData = new LineData(lineDataSets);
        lcCases.setData(lineData);
        lcCases.invalidate();

    }

    private ArrayList<Entry> lineChartDataSet(String cases){
        ArrayList<Entry> dataSet = new ArrayList<>();
        int i;
        int numbers =0;
        for(i = covidData.size() -6; i< covidData.size()-1;i++) {
            Covid covid = covidData.get(i);
            Date date = covid.getDate();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM");
            String strDate = formatter.format(date);
            Float f = Float.parseFloat(strDate);
            switch (cases){
                case "deaths":
                    numbers = covid.getDeaths();
                    break;
                case "confirmed":
                    numbers = covid.getConfirmed();
                    break;
                case "recovered":
                    numbers = covid.getRecovered();
                    break;
                case "active":
                    numbers = covid.getActive();
                    break;


            }
            dataSet.add(new Entry(f, numbers));
        }
        return dataSet;
    }
}