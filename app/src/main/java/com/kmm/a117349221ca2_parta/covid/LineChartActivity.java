package com.kmm.a117349221ca2_parta.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
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
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        lcCases = findViewById(R.id.lcCovidCases);
        covidData = new ArrayList<>(IConstants.COVID_LIST);
        tvLineTitle = findViewById(R.id.tvLineTitle);

        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        spDays = findViewById(R.id.spNoDays);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CovidActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
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

        setSupportActionBar(toolbar);


    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
   Log.d("ItemSelected", String.valueOf(position));
        int days;
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
        ArrayList<ILineDataSet> dataSets1;
            if(!chart.equals("All")) {
                LineDataSet lineDataSet = new LineDataSet((LineChartData( days, chart)), chart);
                lineDataSet.setFillColor(Color.TRANSPARENT);
                lineDataSet.setColor(Color.RED);
                lineDataSet.setCircleColor(Color.BLUE);
                lineDataSet.setLineWidth(5f);
                lineDataSet.setCircleRadius(4f);
                lineDataSet.setDrawCircleHole(true);
                lineDataSet.setValueTextSize(10f);
                lineDataSet.setDrawFilled(true);
                 dataSets1 = new ArrayList<>();
                dataSets1.add(lineDataSet);
                Legend leg = lcCases.getLegend();
                leg.setEnabled(false);
            String title = chart.toUpperCase() + " CASES FROM COVID-19";
            toolbar.setTitle(title);
            } else{
                LineDataSet activeDataSet= new LineDataSet((LineChartData( days,"Active")), "Active");
                LineDataSet confirmedDataSet= new LineDataSet((LineChartData( days,"Confirmed")), "Confirmed");
                LineDataSet deathDataSet= new LineDataSet((LineChartData( days,"Deaths")), "Deaths");
                LineDataSet recoveredDataSet= new LineDataSet((LineChartData(days,"Recovered")), "Recovered");

                activeDataSet.setColor(Color.RED);
                activeDataSet.setCircleColor(Color.BLUE);
                activeDataSet.setLineWidth(5f);
                activeDataSet.setCircleRadius(4f);
                activeDataSet.setDrawCircleHole(true);
                activeDataSet.setValueTextSize(10f);
                activeDataSet.setDrawFilled(true);

                deathDataSet.setColor(Color.BLACK);
                deathDataSet.setCircleColor(Color.BLUE);
                deathDataSet.setLineWidth(5f);
                deathDataSet.setCircleRadius(4f);
                deathDataSet.setDrawCircleHole(true);
                deathDataSet.setValueTextSize(10f);
                deathDataSet.setDrawFilled(true);

                recoveredDataSet.setColor(Color.GREEN);
                recoveredDataSet.setCircleColor(Color.BLUE);
                recoveredDataSet.setLineWidth(5f);
                recoveredDataSet.setCircleRadius(4f);
                recoveredDataSet.setDrawCircleHole(true);
                recoveredDataSet.setValueTextSize(10f);
                recoveredDataSet.setDrawFilled(true);

                confirmedDataSet.setColor(Color.GRAY);
                confirmedDataSet.setCircleColor(Color.BLUE);
                confirmedDataSet.setLineWidth(5f);
                confirmedDataSet.setCircleRadius(4f);
                confirmedDataSet.setDrawCircleHole(true);
                confirmedDataSet.setValueTextSize(10f);
                confirmedDataSet.setDrawFilled(true);


                dataSets1 = new ArrayList<>();
                dataSets1.add(activeDataSet);
                dataSets1.add(recoveredDataSet);
                dataSets1.add(deathDataSet);
                dataSets1.add(confirmedDataSet);
                Legend leg = lcCases.getLegend();
                leg.setEnabled(true);
                String title = "BREAKDOWN OF CASES FROM COVID-19";
                toolbar.setTitle(title);

            }
        data = new LineData(dataSets1);
        lcCases.setData(data);
        Log.d("ArraySize", String.valueOf(covidData.size()));
        lcCases.notifyDataSetChanged();
        lcCases.setTouchEnabled(true);
        lcCases.setPinchZoom(true);
        YAxis leftaxis = lcCases.getAxisLeft();
        leftaxis.setDrawLabels(false);
        XAxis xAxis = lcCases.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        Description des = lcCases.getDescription();
        des.setEnabled(false);

        lcCases.invalidate();

    }

    private ArrayList<Entry> LineChartData ( int days, String cases){

    ArrayList<Entry> entries = new ArrayList<>();

        if(province == null){
            int i;
            int numbers =0;
            for(i = covidData.size()-days; i< covidData.size();i++) {
                Covid covid = covidData.get(i);
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



