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
/**
 * External Libraries used in this Application:
 * 1. MPAndroidChart ('com.github.PhilJay:MPAndroidChart:v3.1.0'),
 * Created by Philipp Jahoda,
 * Available from: https://github.com/PhilJay/MPAndroidChart
 * License:https://github.com/PhilJay/MPAndroidChart/blob/master/LICENSE
 * Used in:
 * C:\Users\katem\AndroidStudioProjects\IS4448\117349221CA2\PARTA\117349221CA2_PARTA\app\src\main\java\com\kmm\a117349221ca2_parta\covid\LineChartActivity.java
 * C:\Users\katem\AndroidStudioProjects\IS4448\117349221CA2\PARTA\117349221CA2_PARTA\app\src\main\res\layout\activity_line_chart.xml
 *
 * 2. Lottie Animation Files ('com.airbnb.android:lottie:3.5.0')
 * airbnb,
 * Available from: https://github.com/airbnb/lottie-android
 * License: https://github.com/airbnb/lottie-android/blob/master/LICENSE,
 * Used in:
 * C:\Users\katem\AndroidStudioProjects\IS4448\117349221CA2\PARTA\117349221CA2_PARTA\app\src\main\res\layout\activity_splash.xml,
 * C:\Users\katem\AndroidStudioProjects\IS4448\117349221CA2\PARTA\117349221CA2_PARTA\app\src\main\res\layout\activity_covid.xml,
 * C:\Users\katem\AndroidStudioProjects\IS4448\117349221CA2\PARTA\117349221CA2_PARTA\app\src\main\java\com\kmm\a117349221ca2_parta\covid\CovidActivity.java,
 *
 *3. GSON ('com.google.code.gson:gson:2.8.5'),
 * Google,
 * Available from: https://github.com/google/gson
 * License: https://github.com/google/gson/blob/master/LICENSE
 * Used in:
 * C:\Users\katem\AndroidStudioProjects\IS4448\117349221CA2\PARTA\117349221CA2_PARTA\app\src\main\java\com\kmm\a117349221ca2_parta\covid\CovidAdapter.java,
 * C:\Users\katem\AndroidStudioProjects\IS4448\117349221CA2\PARTA\117349221CA2_PARTA\app\src\main\java\com\kmm\a117349221ca2_parta\heroCRUD\HeroAdapter.java,
 *
 *
 *
 **/
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
        /*
         * Code below is based on Youtube tutorial: "Splash Home Mobile UI Design Animation Adobe Xd to Android Studio Tutorial",
         * Angga Risky,
         * https://www.youtube.com/watch?v=uUnap0j8wfc&t=1717s
         **/
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        rlSplash.animate().translationY(-1700).setDuration(800).setStartDelay(4000);
        layoutMenu = findViewById(R.id.layout_menu);
        layoutMenu.startAnimation(fromBottom);
        //END

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
