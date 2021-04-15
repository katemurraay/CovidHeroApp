package com.kmm.a117349221ca2_parta;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.kmm.a117349221ca2_parta.covid.Covid;
import com.kmm.a117349221ca2_parta.covid.CovidAdapter;
import com.kmm.a117349221ca2_parta.heroCRUD.GeneralInfo;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.kmm.a117349221ca2_parta.heroCRUD.HeroAdapter;
import com.kmm.a117349221ca2_parta.utils.IConstants;

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
                GeneralInfo generalInfo = HeroAdapter.getAllHeroes();
                if(!generalInfo.getError()) {
                    heroes = new ArrayList<>(generalInfo.getHeroes());
                    IConstants.generalInfo = generalInfo;
                    Message m = new Message();
                    m.obj = heroes;
                } else{
                    heroes =null;
                }
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
        Hero hero = null;
            try {
               GeneralInfo generalInfo = HeroAdapter.deleteHero(heroID);
                if(!generalInfo.getError()){
                ArrayList<Hero> heros = new ArrayList<>(generalInfo.getHeroes());
                for (Hero example : heros){
                    if (example.getHeroID() == heroID){
                        hero = example;
                    }
                }
                }
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
       GeneralInfo generalInfo;
        Hero updatedHero;

        public UpdateHeroLoader(@NonNull Context context, Hero hero, GeneralInfo generalInfo) {
            super(context);
            this.hero= hero;
            this.generalInfo = generalInfo;

        }
        @Nullable
        @Override
        public Hero loadInBackground() {

           try{
               generalInfo.setHeroes(IConstants.HERO_LIST);
               generalInfo = HeroAdapter.updateHero(hero);
               if(!generalInfo.getError()){
                updatedHero = hero;
               } else{
                   updatedHero = null;
               }
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


    public static class GetCOVIDLoader extends AsyncTaskLoader<Covid> {
    public GetCOVIDLoader(Context context){
    super(context);

    }

        @Nullable
        @Override
        public Covid loadInBackground() {

       ArrayList<Covid> covidArrayList= CovidAdapter.getIrishCases();
       int size = covidArrayList.size()-1;
       Covid covid = covidArrayList.get(size);
       IConstants.COVID_LIST = covidArrayList;

       return covid;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.d("GET_COVID_LOADER", "onStartCalled");
        } //END
    }
}
