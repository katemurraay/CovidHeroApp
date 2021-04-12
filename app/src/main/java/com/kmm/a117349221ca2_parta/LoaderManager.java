package com.kmm.a117349221ca2_parta;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.kmm.a117349221ca2_parta.hero.Hero;
import com.kmm.a117349221ca2_parta.hero.HeroAdapter;

import java.util.ArrayList;

/* Code below is based on :
 StackOverflow Answer to Question: "AsyncTaskLoader basic example. (Android)",
 Sanjeev
https://stackoverflow.com/a/44473762
*/
public class LoaderManager {

    public static class GetHeroesLoader extends AsyncTaskLoader<ArrayList<Hero>> {


        public GetHeroesLoader(@NonNull Context context) {
            super(context);

        }
        @Nullable
        @Override
        public ArrayList<Hero> loadInBackground() {
           ArrayList<Hero> heroes ;
            try {
              heroes = new ArrayList<>(HeroAdapter.getAllHeroes());
                Message m = new Message();
                m.obj = heroes;

                Log.d("Loader", "TYPE" );
            } catch (Exception e){
                heroes = null;

            }
            return heroes;
        }



        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.d("Get_Heroes_Loader", "onStartCalled");
        } //END
    }
}
