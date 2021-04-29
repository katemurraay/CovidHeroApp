package com.kmm.a117349221ca2_parta.heroCRUD.createHero;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.kmm.a117349221ca2_parta.heroCRUD.deleteHero.DeleteDialogFragment;
import com.kmm.a117349221ca2_parta.heroCRUD.readHero.HeroRecyclerAdapter;
import com.kmm.a117349221ca2_parta.utils.IConstants;
import com.kmm.a117349221ca2_parta.utils.ShowToast;

import java.util.ArrayList;
import java.util.Objects;

public class CreateDialogFragment extends DialogFragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Hero> {

    Context context;
    Hero hero;
    HeroRecyclerAdapter adapter;
    View view;
    EditText etRealName, etHeroName, etAffiliatedTeam;
    RatingBar ratingBar;
    ArrayList<Hero> heroes;
    public CreateDialogFragment(Context context, HeroRecyclerAdapter adapter, ArrayList<Hero> heroes){
        this.adapter = adapter;
        this.context = context;
        this.heroes = heroes;


    }

    public static CreateDialogFragment newInstance(Context context, HeroRecyclerAdapter adapter, String title, ArrayList<Hero> heroes){
       CreateDialogFragment createDialogFragment = new CreateDialogFragment(context, adapter, heroes);
        Bundle args = new Bundle();
        args.putString("title", title);
       createDialogFragment.setArguments(args);


        createDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return createDialogFragment;


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_fragment_create, container, false);
        Button btnBack = view.findViewById(R.id.btnBack);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        etRealName = view.findViewById(R.id.etRealName);
        etHeroName = view.findViewById(R.id.etHeroName);
        etAffiliatedTeam = view.findViewById(R.id.etTeamAffiliation);
        ratingBar = view.findViewById(R.id.rating_bar);
        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Objects.requireNonNull(getDialog()).dismiss();
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnAdd:
                String realName = etRealName.getText().toString();
                String heroName = etHeroName.getText().toString();
                String teamAffiliation = etAffiliatedTeam.getText().toString();
                int rating = (int) ratingBar.getRating();

                if(realName.isEmpty() || heroName.isEmpty()|| teamAffiliation.isEmpty()){
                    ShowToast toast = new ShowToast();
                    toast.makeImageToast(getContext(), R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);

                } else{
                if(checkInternetConnection()) {
                     hero = new Hero(1, heroName, realName, rating, teamAffiliation);

                     androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.CREATEHEROLOADERID, null, this);
                } else{
                    ShowToast toast = new ShowToast();
                    toast.makeImageToast(getContext(), R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
                }}
                break;

        }}




    public boolean checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) (requireActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);


        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }// END
    @NonNull
    @Override
    public Loader<Hero> onCreateLoader(int id, @Nullable Bundle args) {

        return new com.kmm.a117349221ca2_parta.LoaderManager.CreateHeroLoader((context), hero);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Hero> loader, Hero data) {
        ShowToast toast = new ShowToast();
        if(data!=null){
            toast.makeImageToast(getContext(), R.drawable.ic_check, R.string.delete_hero, Toast.LENGTH_LONG);
            ArrayList<Hero> allHeroes = new ArrayList<>(IConstants.generalInfo.getHeroes());
            for(Hero hero: allHeroes){
                if(!heroes.contains(hero)){
                    heroes.add(hero);
                }
            }
            adapter.notifyDataSetChanged();
            Objects.requireNonNull(getDialog()).dismiss();

        } else{
            toast.makeImageToast(getContext(), R.drawable.ic_error, R.string.conn_error, Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Hero> loader) {

    }

}
