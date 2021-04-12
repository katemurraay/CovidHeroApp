package com.kmm.a117349221ca2_parta.hero;

public class Hero {




    private int id;
    private String name;
    private String realname;
    private int rating;
    private String teamaffiliation;
    public Hero(){
        id= 0;
        name = "";
        realname = "";
        rating =0;
        teamaffiliation = "";
    }

    public Hero (int id, String heroName, String realName, int rating, String teamAffiliation){
        this.id = id;
        this.name = heroName;
        this.realname = realName;
        this.rating = rating;
        this.teamaffiliation = teamAffiliation;
    }


    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getHeroID() {
        return id;
    }

    public void setHeroID(int heroID) {
        this.id = heroID;
    }

    public String getHeroName() {
        return name;
    }

    public void setHeroName(String heroName) {
        this.name = heroName;
    }
    public String getRealName() {
        return realname;
    }

    public void setRealName(String realName) {
        this.realname = realName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTeamAffiliation() {
        return teamaffiliation;
    }

    public void setTeamAffiliation(String teamAffiliation) {
        this.teamaffiliation = teamAffiliation;
    }

    // </editor-fold>
}
