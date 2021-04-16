package com.kmm.a117349221ca2_parta.covid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.kmm.a117349221ca2_parta.utils.IConstants;
import com.kmm.a117349221ca2_parta.utils.NetworkReceiver;
import com.kmm.a117349221ca2_parta.utils.NetworkService;
import com.kmm.a117349221ca2_parta.utils.ShowToast;

public class CovidActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Covid>{
   TextView tvResult, tvNumDeaths, tvNumConfirmed, tvNumActive, tvNumRecovered;
   NetworkReceiver receiver;
   Button btnLineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        tvResult = findViewById(R.id.tvResult);
        tvNumActive = findViewById(R.id.tvNoActiveCases);
        tvNumDeaths = findViewById(R.id.tvNoDeathCases);
        tvNumConfirmed = findViewById(R.id.tvNoConfirmedCases);
        tvNumRecovered = findViewById(R.id.tvNoRecoveredCases);
        btnLineChart = findViewById(R.id.btnLineChart);
        btnLineChart.setOnClickListener((v)->{
            Intent intent = new Intent(CovidActivity.this, LineChartActivity.class);
            startActivity(intent);

        });
        receiver = new NetworkReceiver();
        if(checkInternet(this)){
            androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.GETCOVIDLOADERID, null, this);


        } else{
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);

        }

    }


    @NonNull
    @Override
    public Loader<Covid> onCreateLoader(int id, @Nullable Bundle args) {
        return new com.kmm.a117349221ca2_parta.LoaderManager.GetCOVIDLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<Covid> loader, Covid data) {
        if(data!=null){
            String country = "COVID Cases in " + data.getCountry();
            tvResult.setText( country);
            tvNumDeaths.setText(String.valueOf(data.getDeaths()));
            tvNumActive.setText(String.valueOf(data.getActive()));
            tvNumRecovered.setText(String.valueOf(data.getRecovered()));
            tvNumConfirmed.setText(String.valueOf(data.getConfirmed()));

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Covid> loader) {
        new com.kmm.a117349221ca2_parta.LoaderManager.GetCOVIDLoader(this);
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
}