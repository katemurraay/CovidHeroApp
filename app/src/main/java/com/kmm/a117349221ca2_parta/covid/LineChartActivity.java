package com.kmm.a117349221ca2_parta.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
    String toolbarTitle;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        lcCases = findViewById(R.id.lcCovidCases);
        covidData = new ArrayList<>(IConstants.COVID_LIST);
        tvLineTitle = findViewById(R.id.tvLineTitle);
        spDays = findViewById(R.id.spNoDays);
        chart = getIntent().getExtras().getString(getResources().getString(R.string.covid_chart));
        province = getIntent().getExtras().getString(getResources().getString(R.string.covid_province));
        String[] days;
        days = getResources().getStringArray(R.array.days);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        LineData data = new LineData();
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        data.setValueTextColor(Color.BLACK);

        lcCases.setData(data);
        spDays.setAdapter(arrayAdapter);
        spDays.setOnItemSelectedListener(this);
        spDays.setSelection(0);




    }

/** Code below is based on Web Article: "How to Add and Customize Back Button of Action Bar in Android?"
GeeksforGeeks
https://www.geeksforgeeks.org/how-to-add-and-customize-back-button-of-action-bar-in-android/
 **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(0, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//END

/** Code below is based on Web Article: "Dynamic line chart of MPAndroidChart of Android",
Programmer Sought,
https://www.programmersought.com/article/47345678093/
 **/
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
            lineDataSet.setFillColor(getResources().getColor(R.color.chart_background));
            lineDataSet.setColor(getResources().getColor(R.color.chart_line));
            lineDataSet.setCircleColor(getResources().getColor(R.color.chart_circle));
            lineDataSet.setLineWidth(5f);
            lineDataSet.setCircleRadius(4f);
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setValueTextSize(10f);
            lineDataSet.setDrawFilled(true);
            dataSets1 = new ArrayList<>();
            dataSets1.add(lineDataSet);
            Legend leg = lcCases.getLegend();
            leg.setEnabled(false);
            toolbarTitle =  chart.toUpperCase() + " " +getResources().getString(R.string.covid_title_menu)  ;


        } else{
            LineDataSet activeDataSet= new LineDataSet((LineChartData( days,getResources().getString(R.string.chart_active))), getResources().getString(R.string.chart_active));
            LineDataSet confirmedDataSet= new LineDataSet((LineChartData( days,getResources().getString(R.string.chart_confirmed))), getResources().getString(R.string.chart_confirmed));
            LineDataSet deathDataSet= new LineDataSet((LineChartData( days,getResources().getString(R.string.chart_deaths))), getResources().getString(R.string.chart_deaths));
            LineDataSet recoveredDataSet= new LineDataSet((LineChartData(days,getResources().getString(R.string.chart_recovered))), getResources().getString(R.string.chart_recovered));

            activeDataSet.setFillColor(getResources().getColor(R.color.chart_background));
            activeDataSet.setColor(getResources().getColor(R.color.chart_line_active));
            activeDataSet.setCircleColor(getResources().getColor(R.color.chart_circle));
            activeDataSet.setLineWidth(5f);
            activeDataSet.setCircleRadius(4f);
            activeDataSet.setDrawCircleHole(true);
            activeDataSet.setValueTextSize(10f);
            activeDataSet.setDrawFilled(true);

            deathDataSet.setFillColor(getResources().getColor(R.color.chart_background));
            deathDataSet.setColor(getResources().getColor(R.color.chart_line_deaths));
            deathDataSet.setCircleColor(getResources().getColor(R.color.chart_circle));
            deathDataSet.setLineWidth(5f);
            deathDataSet.setCircleRadius(4f);
            deathDataSet.setDrawCircleHole(true);
            deathDataSet.setValueTextSize(10f);
            deathDataSet.setDrawFilled(true);

            recoveredDataSet.setFillColor(getResources().getColor(R.color.chart_background));
            recoveredDataSet.setColor(getResources().getColor(R.color.chart_line_recovered));
            recoveredDataSet.setCircleColor(getResources().getColor(R.color.chart_circle));
            recoveredDataSet.setLineWidth(5f);
            recoveredDataSet.setCircleRadius(4f);
            recoveredDataSet.setDrawCircleHole(true);
            recoveredDataSet.setValueTextSize(10f);
            recoveredDataSet.setDrawFilled(true);

            confirmedDataSet.setFillColor(getResources().getColor(R.color.chart_background));
            confirmedDataSet.setColor(getResources().getColor(R.color.chart_line_confirmed));
            confirmedDataSet.setCircleColor(getResources().getColor(R.color.chart_circle));
            confirmedDataSet.setLineWidth(5f);
            confirmedDataSet.setCircleRadius(4f);
            confirmedDataSet.setDrawCircleHole(true);
            confirmedDataSet.setValueTextSize(10f);
            confirmedDataSet.setDrawFilled(true);
            toolbarTitle = getResources().getString(R.string.all_covid_title_menu);


            dataSets1 = new ArrayList<>();
            dataSets1.add(activeDataSet);
            dataSets1.add(recoveredDataSet);
            dataSets1.add(deathDataSet);
            dataSets1.add(confirmedDataSet);
            Legend leg = lcCases.getLegend();
            leg.setEnabled(true);

        }
        data = new LineData(dataSets1);
        lcCases.setData(data);
        toolbar.setTitle(toolbarTitle);
        Log.d("ArraySize", String.valueOf(covidData.size()));
        lcCases.notifyDataSetChanged();
        lcCases.setTouchEnabled(true);
        lcCases.setPinchZoom(true);
        YAxis leftaxis = lcCases.getAxisLeft();

        leftaxis.setDrawLabels(false);
        XAxis xAxis = lcCases.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        Description des = lcCases.getDescription();
        des.setEnabled(false);

        lcCases.invalidate();

    }
    private LineDataSet createLineChart() {
        entryArrayList = new ArrayList<>();
        LineDataSet dataSet = new LineDataSet(entryArrayList, (chart));


        return dataSet;

    } //END
    private ArrayList<Entry> LineChartData ( int days, String cases){

        ArrayList<Entry> entries;

        if(province == null){


                entries = new ArrayList<>(getEntryArrayList(covidData, days, cases));
            }
         else {
            ArrayList<Covid> provinceList = new ArrayList<>();
            for (Covid covid : covidData) {
                String covidDataProvince = covid.getProvince();
                if (covidDataProvince.equals(province)) {
                    provinceList.add(covid);
                }
            }
          entries = new ArrayList<>(getEntryArrayList(provinceList, days, cases));
            Log.d("LineChartProvince", province);
        } return entries;
    }



public ArrayList<Entry> getEntryArrayList(ArrayList<Covid> covidList, int days, String cases){
        int numbers =0;
        ArrayList<Entry> entries = new ArrayList<>();
                for(int i = covidList.size()-days; i<covidList.size();i++) {
                    Covid covid = covidList.get(i);
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
                    SimpleDateFormat formatter = new SimpleDateFormat("DD", Locale.ENGLISH);
                    String strDate = formatter.format(date);

                    float f = Float.parseFloat(strDate);
                    Log.d("LineChart", String.valueOf(f));
                    entries.add(new Entry(f, numbers));
                }

            return entries;
        }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


