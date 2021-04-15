package com.kmm.a117349221ca2_parta.heroCRUD;

import com.google.gson.annotations.SerializedName;

public  class Hero {




    private int id;
    @SerializedName("name")
    private String heroName;
    @SerializedName("realname")
    private String realName;
    private int rating;
    @SerializedName("teamaffiliation")
    private String teamAffiliation;
    public Hero(){
        id= 0;
        heroName = "";
        realName = "";
        rating =0;
        teamAffiliation = "";
    }

    public Hero (int id, String heroName, String realName, int rating, String teamAffiliation){
        this.id = id;
        this.heroName = heroName;
        this.realName = realName;
        this.rating = rating;
        this.teamAffiliation = teamAffiliation;
    }


    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getHeroID() {
        return id;
    }

    public void setHeroID(int heroID) {
        this.id = heroID;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTeamAffiliation() {
        return teamAffiliation;
    }

    public void setTeamAffiliation(String teamAffiliation) {
        this.teamAffiliation = teamAffiliation;
    }

    // </editor-fold>
}
