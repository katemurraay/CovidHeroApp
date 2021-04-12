package com.kmm.a117349221ca2_parta.hero;

import android.media.Rating;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kmm.a117349221ca2_parta.R;

public class HeroViewHolder extends RecyclerView.ViewHolder {

    TextView tvHeroName, tvTeamAffiliation, tvRealName;
    RatingBar ratingBar;

    HeroViewHolder(View itemView) {
        super(itemView);
        tvHeroName = itemView.findViewById(R.id.tvHeroName);
        tvRealName = itemView.findViewById(R.id.tvRealName);
        tvTeamAffiliation = itemView.findViewById(R.id.tvTeamAffiliation);
        ratingBar = itemView.findViewById(R.id.rating_bar);

    }
}
