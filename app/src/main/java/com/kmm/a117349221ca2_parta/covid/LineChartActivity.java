package com.kmm.a117349221ca2_parta.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class LineChartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    LineChart lcCases;
    ArrayList<Covid> covidData;
    TextView tvLineTitle;
    Spinner spDays;
    String province, chart;
    ArrayList<Entry> entryArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        lcCases = findViewById(R.id.lcCovidCases);
        covidData = new ArrayList<>(IConstants.COVID_LIST);
        tvLineTitle = findViewById(R.id.tvLineTitle);
        spDays = findViewById(R.id.spNoDays);
        chart = getIntent().getExtras().getString("CHART");
        province = getIntent().getExtras().getString("PROVINCE");
        String[] days;
        days = getResources().getStringArray(R.array.days);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        LineData data = new LineData();

        data.setValueTextColor(Color.BLACK);

        lcCases.setData(data);
        spDays.setAdapter(arrayAdapter);
        spDays.setOnItemSelectedListener(this);
        spDays.setSelection(0);




    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
   Log.d("ItemSelected", String.valueOf(position));
        int days=0;
    if(position == 0){
    days = 5;
    } else{
    days = 10;
        }
            LineData data = lcCases.getData();
            ILineDataSet ds= data.getDataSetByIndex(0);
            if(ds == null){
                ds =createLineChart();
                data.addDataSet(ds);

            }
            LineDataSet lineDataSet = new LineDataSet((LineChartData(entryArrayList, days)), chart);
            lineDataSet.setFillColor(Color.TRANSPARENT);
            lineDataSet.setColor(Color.RED);
            lineDataSet.setCircleColor(Color.BLUE);
            lineDataSet.setLineWidth(5f);
            lineDataSet.setCircleRadius(4f);
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setValueTextSize(10f);
            lineDataSet.setDrawFilled(true);
            ArrayList<ILineDataSet> dataSets1 = new ArrayList<>();
            dataSets1.add(lineDataSet);

            data = new LineData(dataSets1);
            lcCases.setData(data);
            Log.d("ArraySize", String.valueOf(covidData.size()));



        lcCases.notifyDataSetChanged();

        YAxis leftaxis = lcCases.getAxisLeft();
        leftaxis.setDrawLabels(false);
        XAxis xAxis = lcCases.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lcCases.invalidate();

    }

    private ArrayList<Entry> LineChartData (ArrayList<Entry> entries, int days){

     entries = new ArrayList<>();

        if(province == null){
            int i;
            int numbers =0;
            for(i = covidData.size()-days; i< covidData.size();i++) {
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


                entries.add(new Entry(f, numbers));
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
            for(i = provinceList.size()-days; i< provinceList.size();i++) {
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
                entries.add(new Entry(f, numbers));
            }
    }
        return entries;
    }

    private LineDataSet createLineChart() {
        entryArrayList = new ArrayList<>();
        LineDataSet dataSet = new LineDataSet(entryArrayList, (chart));


        return dataSet;

    }




    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



