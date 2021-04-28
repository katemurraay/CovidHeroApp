package com.kmm.a117349221ca2_parta.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LineChartActivity extends AppCompatActivity {
    LineChart lcCases;
    ArrayList<Covid> covidData;
    TextView sticky;
    String province, chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        lcCases = findViewById(R.id.lcCovidCases);
        covidData = new ArrayList<>(IConstants.COVID_LIST);
        sticky = findViewById(R.id.tvSticky);
        chart = getIntent().getExtras().getString("CHART");
        province = getIntent().getExtras().getString("PROVINCE");
        LineDataSet dataSet = new LineDataSet(lineChartDataSet(), (chart));
        dataSet.setFillColor(Color.TRANSPARENT);
        dataSet.setColor(Color.RED);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawCircleHole(true);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawFilled(true);

        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);
        lcCases.setData(lineData);
        YAxis leftaxis = lcCases.getAxisLeft();
        leftaxis.setDrawLabels(false);
        XAxis xAxis = lcCases.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



    }



    private ArrayList<Entry> lineChartDataSet(){
        ArrayList<Entry> dataSet = new ArrayList<>();
if(province == null){
    int i;
    int numbers =0;
    for(i = covidData.size()-5; i< covidData.size();i++) {
        Covid covid = covidData.get(i);
        switch (chart){
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
        Date date = covid.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM", Locale.ENGLISH);
        String strDate = formatter.format(date);

        float f = Float.parseFloat(strDate);

        dataSet.add(new Entry(f, numbers));
}
} else{
    ArrayList<Covid> provinceList = new ArrayList<>();
        for(Covid covid : covidData){
            String covidDataProvince = covid.getProvince();
            if(covidDataProvince.equals(province)){
                provinceList.add(covid);
            }
        }
    int i;
    int numbers =0;
    Log.d("LineChartProvince", province);
    for(i = provinceList.size()-5; i< provinceList.size();i++) {
        Covid covid = provinceList.get(i);
        Log.d("LineChartProvince", covid.getProvince());
         switch (chart){
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
        Date date = covid.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM", Locale.ENGLISH);
        String strDate = formatter.format(date);

        float f = Float.parseFloat(strDate);
        Log.d("LineChart", String.valueOf(f));
        dataSet.add(new Entry(f, numbers));

    }




    }
        return dataSet;
    }



}



