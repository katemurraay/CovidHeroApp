package com.kmm.a117349221ca2_parta.heroCRUD.heroRead;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.kmm.a117349221ca2_parta.heroCRUD.heroDelete.DeleteSwipe;
import com.kmm.a117349221ca2_parta.heroCRUD.heroUpdate.EditSwipe;
import com.kmm.a117349221ca2_parta.utils.IConstants;
import com.kmm.a117349221ca2_parta.utils.NetworkReceiver;
import com.kmm.a117349221ca2_parta.utils.NetworkService;
import com.kmm.a117349221ca2_parta.utils.ShowToast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Hero>> {
    NetworkReceiver receiver;
    SearchView searchView;
    RecyclerView recyclerView;
    DeleteSwipe deleteSwipe;
    EditSwipe editSwipe;

    private HeroRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        receiver =new NetworkReceiver();
        if(checkInternet(this)){
            androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.GETHEROESLOADERID, null, this);


        } else{
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return true;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Hero>> onCreateLoader(int id, @Nullable Bundle args) {
        return new com.kmm.a117349221ca2_parta.LoaderManager.GetHeroesLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Hero>> loader, ArrayList<Hero> data) {
        if(data != null){
        IConstants.HERO_LIST = new ArrayList<>(data);
        adapter = new HeroRecyclerAdapter(IConstants.HERO_LIST);
        setUpRecyclerView();
        }
    }

    public void setUpRecyclerView(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        deleteSwipe = new DeleteSwipe(this, this, adapter);
        editSwipe = new EditSwipe(this, this, adapter);
        new ItemTouchHelper(deleteSwipe).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(editSwipe).attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
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


}