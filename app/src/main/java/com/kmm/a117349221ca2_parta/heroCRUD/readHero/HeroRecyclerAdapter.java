package com.kmm.a117349221ca2_parta.heroCRUD.readHero;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kmm.a117349221ca2_parta.R;
import com.kmm.a117349221ca2_parta.heroCRUD.Hero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HeroRecyclerAdapter extends RecyclerView.Adapter<HeroViewHolder> implements Filterable {
private List<Hero> heroList;
private List<Hero> heroListComplete;
private Context context;

    public HeroRecyclerAdapter(List<Hero> heroList, Context context){

        this.heroList = heroList;
        this.context = context;
        heroListComplete = new ArrayList<>(heroList);


    }



    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_list_item,
                parent, false);

        return new HeroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Hero currentHero = heroList.get(position);
        String heroName= currentHero.getHeroName();

        holder.tvHeroName.setText(heroName);
        holder.tvTeamAffiliation.setText(currentHero.getTeamAffiliation());
        holder.tvRealName.setText(currentHero.getRealName());
        holder.ratingBar.setRating((float) currentHero.getRating());
        holder.imgHero.setImageResource(setImageResources(heroName.toLowerCase()));




    }

    public int setImageResources (String heroName){
    String[] superheros = context.getResources().getStringArray(R.array.superhero);
    int imageRes = 0;
    if(heroName.equals(superheros[0])){
        imageRes=R.drawable.ic_batman;
        } else if(heroName.equals(superheros[1])){
        imageRes=R.drawable.ic_deadpool;
    } else if(heroName.equals(superheros[2])){
        imageRes=R.drawable.ic_ironman;
    } else if(heroName.equals(superheros[3])){
        imageRes=R.drawable.ic_captainamerica;
    } else if(heroName.equals(superheros[4])){
        imageRes=R.drawable.ic_spiderman;
        } else if(heroName.equals(superheros[5])){
        imageRes=R.drawable.ic_superman;
    }else{
        imageRes=R.drawable.ic_cape;
    }
    return imageRes;
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    @Override
    public Filter getFilter() {
        return heroFilter;
    }
    /**
     Code below is based on: Github Repository: codinginflow/ExampleAdapter.java,
     Coding In Flow,
     https://gist.github.com/codinginflow/eec0211b4fab5e5426319389377d71af
     */
    private Filter heroFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Hero> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(heroListComplete);
            } else {
                String filterPattern = constraint.toString().toLowerCase();
                for (Hero item : heroListComplete) {
                    if (item.getHeroName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                }
            FilterResults results = new FilterResults();
            results.values = filteredList;
                return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            heroList.clear();
            heroList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        } //END
    };
}
