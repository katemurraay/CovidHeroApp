package com.kmm.a117349221ca2_parta.heroCRUD.readHero;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.covid.Covid;
import com.kmm.a117349221ca2_parta.covid.CovidActivity;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.kmm.a117349221ca2_parta.heroCRUD.createHero.CreateDialogFragment;
import com.kmm.a117349221ca2_parta.heroCRUD.deleteHero.DeleteSwipe;
import com.kmm.a117349221ca2_parta.heroCRUD.updateHero.EditSwipe;
import com.kmm.a117349221ca2_parta.utils.IConstants;
import com.kmm.a117349221ca2_parta.utils.NetworkReceiver;
import com.kmm.a117349221ca2_parta.utils.NetworkService;
import com.kmm.a117349221ca2_parta.utils.ShowToast;

import java.util.ArrayList;




import static com.kmm.a117349221ca2_parta.utils.IConstants.ID_ADD;
import static com.kmm.a117349221ca2_parta.utils.IConstants.ID_HOME;
import static com.kmm.a117349221ca2_parta.utils.IConstants.ID_COVID;


public class HeroActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Hero>>, View.OnClickListener {
    NetworkReceiver receiver;

    RecyclerView recyclerView;
    DeleteSwipe deleteSwipe;
    EditSwipe editSwipe;
    EditText etSearch;

    ArrayList<Hero> heroes;
    private HeroRecyclerAdapter adapter;
    ExtendedFloatingActionButton fabChart,  fabAddHero;
    ExtendedFloatingActionButton fabAdd;
LinearLayout fabLayout;
    boolean isFabVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        fabAdd = findViewById(R.id.fabAdd);

        fabChart = findViewById(R.id.fabChart);
        fabAddHero = findViewById(R.id.fabAddHero);
        fabLayout = findViewById(R.id.fab_layout);
        fabAdd.setOnClickListener(this);
        fabAddHero.setOnClickListener(this);

        fabChart.setOnClickListener(this);
        etSearch = findViewById(R.id.etSearch);



        recyclerView = findViewById(R.id.recycler_view);
        receiver =new NetworkReceiver();
        if(checkInternet(this)){
            androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.GETHEROESLOADERID, null, this);


        } else{
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);

        }

        fabAdd.shrink();
       fabLayout.setVisibility(View.GONE);


        isFabVisible =false;
    }



    @NonNull
    @Override
    public Loader<ArrayList<Hero>> onCreateLoader(int id, @Nullable Bundle args) {
        return new com.kmm.a117349221ca2_parta.LoaderManager.GetHeroesLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Hero>> loader, ArrayList<Hero> data) {
        if(data != null){
      heroes = new ArrayList<>(IConstants.generalInfo.getHeroes());
        adapter = new HeroRecyclerAdapter(heroes);
        setUpRecyclerView();
        }
    }

    public void setUpRecyclerView(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        deleteSwipe = new DeleteSwipe(this, this, adapter, heroes);
        editSwipe = new EditSwipe(this, this, adapter, heroes);
        new ItemTouchHelper(deleteSwipe).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(editSwipe).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        //https://gist.github.com/codinginflow/ea0d9aeb791fb2eac190befcec448909
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());

            }
        });

    }
    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Hero>> loader) {
        new com.kmm.a117349221ca2_parta.LoaderManager.GetHeroesLoader(this);
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


    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fabAdd:
                if(!isFabVisible){
                    fabLayout.setVisibility(View.VISIBLE);
                    fabAdd.extend();
                    fabAdd.setIcon(getResources().getDrawable(R.drawable.ic_hide));
                    isFabVisible =true;

                } else{
                  hideFloatingActionButtons();
                }
                break;
            case R.id.fabAddHero:
                CreateDialogFragment createDialogFragment = CreateDialogFragment.newInstance(this, adapter,"CREATE HERO", heroes);
                createDialogFragment.show(getSupportFragmentManager(), "DialogFragment");
                hideFloatingActionButtons();
                break;

            case R.id.fabChart:
                hideFloatingActionButtons();
                Intent intent = new Intent(getApplicationContext(), CovidActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
                break;

        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void hideFloatingActionButtons(){
        fabLayout.setVisibility(View.GONE);
        fabAdd.setIcon(getResources().getDrawable(R.drawable.ic_plus));
        fabAdd.shrink();
        isFabVisible =false;
    }
}