package com.kmm.a117349221ca2_parta;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.kmm.a117349221ca2_parta.heroCRUD.HeroAdapter;

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

                Log.d("Loader", "Get_Heroes_Loader" );
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

    public static class DeleteHeroLoader extends AsyncTaskLoader<Hero> {
int heroID;

        public DeleteHeroLoader(@NonNull Context context, int heroId) {
            super(context);
            this.heroID = heroId;

        }
        @Nullable
        @Override
        public Hero loadInBackground() {
        Hero hero;
            try {
               hero = HeroAdapter.deleteHero(heroID);
                Message m = new Message();
                m.obj = hero;

                Log.d("Loader", "Delete_Hero_Loader" );
            } catch (Exception e){
                hero = null;

            }
            return hero;
        }



        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.d("Delete_Hero_Loader", "onStartCalled");
        } //END
}

    public static class UpdateHeroLoader extends AsyncTaskLoader<Hero> {
       Hero hero;

        public UpdateHeroLoader(@NonNull Context context, Hero hero) {
            super(context);
            this.hero= hero;

        }
        @Nullable
        @Override
        public Hero loadInBackground() {
            Hero updatedHero;
            try {
                updatedHero = HeroAdapter.updateHero(hero);
                Message m = new Message();
                m.obj = updatedHero;

                Log.d("Loader", "Update_Hero_Loader" );
            } catch (Exception e){
                updatedHero = null;

            }
            return  updatedHero;
        }



        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.d("Update_Hero_Loader", "onStartCalled");
        } //END
    }
}
