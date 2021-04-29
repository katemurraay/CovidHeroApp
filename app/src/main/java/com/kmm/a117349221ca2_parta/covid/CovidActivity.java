package com.kmm.a117349221ca2_parta.covid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.kmm.a117349221ca2_parta.utils.IConstants;
import com.kmm.a117349221ca2_parta.utils.NetworkReceiver;
import com.kmm.a117349221ca2_parta.utils.NetworkService;
import com.kmm.a117349221ca2_parta.utils.ShowToast;

import java.util.ArrayList;
import java.util.List;

public class CovidActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Covid>>, View.OnClickListener, AdapterView.OnItemSelectedListener {
   TextView tvResult, tvNumDeaths, tvNumConfirmed, tvNumActive, tvNumRecovered;
   NetworkReceiver receiver;
   ScrollView scrollView;
   String strProvince;
    Spinner spCountry, spProvince;
   CardView cvConfirmed, cvActive, cvRecovered, cvDeaths;
   String strCountry;
   ArrayList<Covid> currentCases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        tvResult = findViewById(R.id.tvResult);
        scrollView = findViewById(R.id.scrollView);
        tvNumActive = findViewById(R.id.tvNoActiveCases);
        tvNumDeaths = findViewById(R.id.tvNoDeathCases);
        tvNumConfirmed = findViewById(R.id.tvNoConfirmedCases);
        tvNumRecovered = findViewById(R.id.tvNoRecoveredCases);
        cvActive = findViewById(R.id.cvActive);
        cvConfirmed = findViewById(R.id.cvConfirmed);
        cvRecovered = findViewById(R.id.cvRecovered);
        cvDeaths = findViewById(R.id.cvDeaths);
        spCountry = findViewById(R.id.spCountry);
        spProvince = findViewById(R.id.spProvince);
        currentCases = new ArrayList<>();
        receiver = new NetworkReceiver();
        strProvince = "";
        String[] countries;
        countries = getResources().getStringArray(R.array.european_countries);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        spCountry.setAdapter(arrayAdapter);
        spCountry.setSelection(20);
        spCountry.setOnItemSelectedListener(this);
        spProvince.setOnItemSelectedListener(this);

        cvDeaths.setOnClickListener(this);
        cvActive.setOnClickListener(this);
        cvRecovered.setOnClickListener(this);
        cvConfirmed.setOnClickListener(this);

    }


    @NonNull
    @Override
    public Loader<ArrayList<Covid>> onCreateLoader(int id,  @Nullable Bundle args) {
        scrollView.setVisibility(View.GONE);
        return new com.kmm.a117349221ca2_parta.LoaderManager.GetCOVIDLoader(this, strCountry);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Covid>> loader, ArrayList<Covid> data) {
        if(data!=null){
            currentCases= new ArrayList<>(data);
            scrollView.setVisibility(View.VISIBLE);
            ArrayList<String> provinces = new ArrayList<>();
            for(Covid covid: data){
                String province = covid.getProvince().trim();

                if (!province.isEmpty()){
                 provinces.add(covid.getProvince());
                }
                else{
                    provinces = null;

                }
            }
            if(provinces!= null) {
                spProvince.setVisibility(View.VISIBLE);

                ArrayAdapter<String> pvArraryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinces);
                spProvince.setAdapter(pvArraryAdapter);
                 spProvince.setSelection(0);
            } else{
                spProvince.setVisibility(View.GONE);
            }
            tvNumDeaths.setText(String.valueOf(data.get(0).getDeaths()));
            tvNumActive.setText(String.valueOf(data.get(0).getActive()));
            tvNumRecovered.setText(String.valueOf(data.get(0).getRecovered()));
            tvNumConfirmed.setText(String.valueOf(data.get(0).getConfirmed()));


        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Covid>> loader) {
        new com.kmm.a117349221ca2_parta.LoaderManager.GetCOVIDLoader(this, strCountry);
    }




    boolean checkInternet(Context context) {
        NetworkService serviceManager = new NetworkService(context);
        return serviceManager.isNetworkAvailable();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);

    }


    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(CovidActivity.this, LineChartActivity.class);
        if(spProvince.getVisibility() == View.VISIBLE){
            strProvince = spProvince.getSelectedItem().toString();

        } else{
            strProvince =null;
        }
        intent.putExtra("PROVINCE", strProvince);
        switch (v.getId()) {

            case R.id.cvActive:
                intent.putExtra("CHART", "Active");
                startActivity(intent);
                break;
            case R.id.cvConfirmed:
                intent.putExtra("CHART", "Confirmed");
                startActivity(intent);
                break;
            case R.id.cvDeaths:
                intent.putExtra("CHART", "Deaths");
                startActivity(intent);
                break;
            case R.id.cvRecovered:
                intent.putExtra("CHART", "Recovered");

                startActivity(intent);
                break;


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spCountry){
            String item = parent.getItemAtPosition(position).toString();
        if(checkInternet(this)){
            if(strCountry != null) {
                strCountry = null;
                strCountry = item.toLowerCase();
                Log.d("StrCountry", strCountry);
                androidx.loader.app.LoaderManager.getInstance(this).restartLoader(IConstants.GETCOVIDLOADERID, null, this);

            } else{
                strCountry = item.toLowerCase();
                Log.d("StrCountry", strCountry);
                androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.GETCOVIDLOADERID, null, this);

            }

        } else{
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);

        }
        } else if (parent.getId() == R.id.spProvince){

            tvNumDeaths.setText(String.valueOf(currentCases.get(position).getDeaths()));
            tvNumActive.setText(String.valueOf(currentCases.get(position).getActive()));
            tvNumRecovered.setText(String.valueOf(currentCases.get(position).getRecovered()));
            tvNumConfirmed.setText(String.valueOf(currentCases.get(position).getConfirmed()));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}