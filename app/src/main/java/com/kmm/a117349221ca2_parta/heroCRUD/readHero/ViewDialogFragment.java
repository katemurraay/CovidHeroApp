package com.kmm.a117349221ca2_parta.heroCRUD.readHero;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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

import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.kmm.a117349221ca2_parta.heroCRUD.updateHero.EditDialogFragment;
import com.kmm.a117349221ca2_parta.utils.IConstants;
import com.kmm.a117349221ca2_parta.utils.ShowToast;

import java.util.ArrayList;
import java.util.Objects;

public class ViewDialogFragment extends DialogFragment implements View.OnClickListener {
    int position;
    Context context;
    Hero hero;
    HeroRecyclerAdapter adapter;
    View view;
    TextView tvRealName, tvHeroName, tvAffiliatedTeam;
    RatingBar ratingBar;
    ArrayList<Hero> heroes;


    public ViewDialogFragment (Context context, HeroRecyclerAdapter adapter, int position, ArrayList<Hero> heroes){
        this.adapter = adapter;
        this.context = context;
        this.position = position;
        this.heroes = heroes;

    }

    public static ViewDialogFragment newInstance(Context context, int position, HeroRecyclerAdapter adapter, String title, ArrayList<Hero> heroes) {
        ViewDialogFragment viewDialogFragment = new ViewDialogFragment(context, adapter, position, heroes);
        Bundle args = new Bundle();
        args.putString("title", title);
        viewDialogFragment.setArguments(args);


        viewDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return    viewDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_fragment_edit, container, false);
        Button btnBack = view.findViewById(R.id.btnBack);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        tvRealName = view.findViewById(R.id.tvRealName);
        tvHeroName = view.findViewById(R.id.tvHeroName);
        tvAffiliatedTeam = view.findViewById(R.id.tvTeamAffiliation);
        ratingBar = view.findViewById(R.id.rating_bar);
        hero = heroes.get(position);
        tvHeroName.setText(hero.getHeroName());
        tvRealName.setText(hero.getRealName());
        tvAffiliatedTeam.setText(hero.getTeamAffiliation());
        ratingBar.setRating(hero.getRating());
        btnBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
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
            case R.id.btnEdit:
                EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(context, position, adapter, "Edit Hero", heroes);
                editDialogFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "1234");
                break;

}
    }
}
