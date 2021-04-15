package com.kmm.a117349221ca2_parta.heroCRUD;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kmm.a117349221ca2_parta.HttpHandler;

public class HeroAdapter {
    public static final String baseURI = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=";

    private static final Hero convertToHeroObject (String heroString) {
        Gson g = new Gson();
        Hero hero = g.fromJson(heroString, Hero.class);
        return hero;
    }

    private static final String convertToString (Hero hero) {
        Gson g = new Gson();
        String s = g.toJson(hero);
        return s;
    }

    public static final GeneralInfo getAllHeroes() throws JsonSyntaxException {
        String heroURI = baseURI + "getheroes" ;
        String heroString = HttpHandler.HttpGetExec(heroURI);
        Gson g = new Gson();
        GeneralInfo generalInfo = g.fromJson(heroString, GeneralInfo.class);
        return generalInfo;
    }


    public static final GeneralInfo createHero (Hero hero) throws JsonSyntaxException {
        String heroURI = baseURI + "createhero";
        Gson g = new Gson();
        String heroString = HttpHandler.HttpPostExec(heroURI, hero);
        return g.fromJson(heroString, GeneralInfo.class);
    }

    public static final GeneralInfo updateHero (Hero hero) throws JsonSyntaxException {
        String heroURI = baseURI + "updatehero&id="+ hero.getHeroID();
        Gson g = new Gson();

        String heroString = HttpHandler.HttpPostExec(heroURI, hero);
        return g.fromJson(heroString, GeneralInfo.class);
    }

    public static final GeneralInfo deleteHero (int heroId) throws JsonSyntaxException {
        String heroURI = baseURI + "deletehero&id=" + heroId;
        String heroString = HttpHandler.HttpDeleteExec(heroURI);
        Gson g = new Gson();
        return g.fromJson(heroString, GeneralInfo.class);
    }
}
