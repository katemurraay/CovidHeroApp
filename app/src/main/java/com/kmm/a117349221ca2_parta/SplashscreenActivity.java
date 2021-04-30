package com.kmm.a117349221ca2_parta;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kmm.a117349221ca2_parta.covid.CovidActivity;
import com.kmm.a117349221ca2_parta.heroCRUD.readHero.HeroActivity;

import java.util.zip.Inflater;

public class SplashscreenActivity extends AppCompatActivity {
RelativeLayout rlSplash;
LinearLayout layoutMenu;
Animation fromBottom;
ImageView ivHero, ivCovid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rlSplash = findViewById(R.id.rlSplash);
        ivCovid = findViewById(R.id.ivCovid);
        ivHero = findViewById(R.id.ivShield);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        rlSplash.animate().translationY(-1700).setDuration(800).setStartDelay(4000);
        layoutMenu = findViewById(R.id.layout_menu);
        layoutMenu.startAnimation(fromBottom);

        ivHero.setOnClickListener((v)->{
            Intent intent = new Intent(this, HeroActivity.class);
            startActivity(intent);
            finish();
        });
        ivCovid.setOnClickListener((v)->{
            Intent intent = new Intent(this, CovidActivity.class);
            startActivity(intent);
            finish();
        });


    }
}
