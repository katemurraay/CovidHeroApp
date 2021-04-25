package com.kmm.a117349221ca2_parta.heroCRUD.readHero;

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
import java.util.List;
//https://gist.github.com/codinginflow/eec0211b4fab5e5426319389377d71af
public class HeroRecyclerAdapter extends RecyclerView.Adapter<HeroViewHolder> implements Filterable {
private List<Hero> heroList;
private List<Hero> heroListComplete;

    public HeroRecyclerAdapter(List<Hero> heroList){

        this.heroList = heroList;
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
        holder.tvHeroName.setText(currentHero.getHeroName());
        holder.tvTeamAffiliation.setText(currentHero.getTeamAffiliation());
        holder.tvRealName.setText(currentHero.getRealName());
        holder.ratingBar.setRating(currentHero.getRating());

    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    @Override
    public Filter getFilter() {
        return heroFilter;
    }

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
        }
    };
}
