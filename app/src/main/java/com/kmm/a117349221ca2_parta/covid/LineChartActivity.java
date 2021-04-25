package com.kmm.a117349221ca2_parta.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.transition.MaterialSharedAxis;
import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.utils.IConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LineChartActivity extends AppCompatActivity {
    LineChart lcCases;
    ArrayList<Covid> covidData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        lcCases = findViewById(R.id.lcCovidCases);
        covidData = new ArrayList<>(IConstants.COVID_LIST);
        String extra = getIntent().getExtras().getString("CHART");
        LineDataSet dataSet = new LineDataSet(lineChartDataSet(extra), (extra));

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(dataSet);



        LineData lineData = new LineData(lineDataSets);
        lcCases.setData(lineData);

        XAxis xAxis = lcCases.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        lcCases.invalidate();
    }



    private ArrayList<Entry> lineChartDataSet(String cases){
        ArrayList<Entry> dataSet = new ArrayList<>();
        int i;
        int numbers =0;
        for(i = covidData.size()-5; i< covidData.size();i++) {
            Covid covid = covidData.get(i);
            Date date = covid.getDate();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd.MM");
            String strDate = formatter.format(date);
            Log.d("DATE", strDate);
            float f = Float.parseFloat(strDate);
            Log.d("Float", String.valueOf(f));
            switch (cases){
                case "Deaths":
                    numbers = covid.getDeaths();
                    break;
                case "Confirmed":
                    numbers = covid.getConfirmed();
                    break;
                case "Recovered":
                    numbers = covid.getRecovered();
                    break;
                case "Active":
                    numbers = covid.getActive();
                    break;


            }
            dataSet.add(new Entry(f, numbers));
        }
        return dataSet;
    }


}