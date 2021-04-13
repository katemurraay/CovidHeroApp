package com.kmm.a117349221ca2_parta.heroCRUD.heroUpdate;

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
import com.kmm.a117349221ca2_parta.heroCRUD.heroDelete.DeleteDialogFragment;
import com.kmm.a117349221ca2_parta.heroCRUD.heroRead.HeroRecyclerAdapter;
import com.kmm.a117349221ca2_parta.utils.IConstants;
import com.kmm.a117349221ca2_parta.utils.ShowToast;

import java.util.Objects;

public class EditDialogFragment extends DialogFragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Hero> {
        int position;
        Context context;
        Hero hero;
        HeroRecyclerAdapter adapter;
        View view;
    EditText etRealName, etHeroName, etAffiliatedTeam;
    RatingBar ratingBar;
public EditDialogFragment (Context context, HeroRecyclerAdapter adapter, int position){
    this.adapter = adapter;
    this.context = context;
    this.position = position;}

    public static EditDialogFragment newInstance(Context context, int position, HeroRecyclerAdapter adapter, String title){
        EditDialogFragment editDialogFragment = new EditDialogFragment(context, adapter, position);
        Bundle args = new Bundle();
        args.putString("title", title);
      editDialogFragment.setArguments(args);


        editDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return    editDialogFragment;
}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_fragment_edit, container, false);
        Button btnBack = view.findViewById(R.id.btnBack);
        Button btnSave = view.findViewById(R.id.btnSave);
         etRealName = view.findViewById(R.id.etRealName);
            etHeroName = view.findViewById(R.id.etHeroName);
         etAffiliatedTeam = view.findViewById(R.id.etTeamAffiliation);
         ratingBar = view.findViewById(R.id.rating_bar);



        hero = IConstants.HERO_LIST.get(position);
        etHeroName.setText(hero.getHeroName());
        etRealName.setText(hero.getRealName());
        etAffiliatedTeam.setText(hero.getTeamAffiliation());
        ratingBar.setRating(hero.getRating());
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
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
            case R.id.btnSave:
                if(checkInternetConnection()) {
                    String heroName = etHeroName.getText().toString();
                    String realName = etRealName.getText().toString();
                    String team = etAffiliatedTeam.getText().toString();
                    hero.setHeroName(heroName);
                    hero.setRealName(realName);
                    hero.setTeamAffiliation(team);
                    hero.setRating(ratingBar.getNumStars());

                    androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.UPDATEHEROLOADERID, null, this);
                } else{
                    ShowToast toast = new ShowToast();
                    toast.makeImageToast(getContext(), R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
                }
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
        return new com.kmm.a117349221ca2_parta.LoaderManager.UpdateHeroLoader(context, hero);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Hero> loader, Hero data) {
    ShowToast toast = new ShowToast();
if(data != null){
    toast.makeImageToast(getContext(), R.drawable.ic_check, R.string.delete_hero, Toast.LENGTH_LONG);
    adapter.notifyItemChanged(position);
    Objects.requireNonNull(getDialog()).dismiss();
}else{
    toast.makeImageToast(getContext(), R.drawable.ic_error, R.string.conn_error, Toast.LENGTH_LONG);
}
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Hero> loader) {
        new com.kmm.a117349221ca2_parta.LoaderManager.UpdateHeroLoader(context, hero);
    }
}
