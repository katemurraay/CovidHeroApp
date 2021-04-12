package com.kmm.a117349221ca2_parta.hero;

import java.util.ArrayList;

public class GeneralInfo {

    private boolean error;
    private String message;
    private ArrayList<Hero> heroes;

    public GeneralInfo(boolean error, String message, ArrayList<Hero> heroes) {
        this.error = error;
        this.message = message;
        this.heroes = heroes;
    }
    public GeneralInfo() {
        this.error = false;
        this.message = "";
        this.heroes = null;
    }
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }
//</editor-fold>
}
