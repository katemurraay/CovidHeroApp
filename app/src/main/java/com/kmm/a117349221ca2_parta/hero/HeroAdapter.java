package com.kmm.a117349221ca2_parta.hero;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    public static final ArrayList<Hero> getAllHeroes() throws JsonSyntaxException {
        String beerURI = baseURI + "getheroes" ;
        String heroString = HeroHttpHandler.HttpGetExec(beerURI);
        Gson g = new Gson();
        GeneralInfo generalInfo = g.fromJson(heroString, GeneralInfo.class);
        return generalInfo.getHeroes();
    }


    public static final Hero createHero (Hero hero) throws JsonSyntaxException {
        String heroURI = baseURI + "createhero";
        Gson g = new Gson();
        String payload = g.toJson(hero, Hero.class);
        String heroString = HeroHttpHandler.HttpPostExec(heroURI, payload);
        hero = g.fromJson(heroString, Hero.class);
        return hero;
    }

    public static final Hero updateHero (Hero hero) throws JsonSyntaxException {
        String heroURI = baseURI + "updatehero&id="+hero.getHeroID();
        Gson g = new Gson();
        String payload = g.toJson(hero, Hero.class);
        String heroString = HeroHttpHandler.HttpPostExec(heroURI, payload);
        hero = g.fromJson(heroString, Hero.class);
        return hero;
    }

    public static final Hero deleteHero (int heroId) throws JsonSyntaxException {
        String heroURI = baseURI + "deletehero&id=" + heroId;
        String heroString = HeroHttpHandler.HttpDeleteExec(heroURI);
        Gson g = new Gson();
        Hero hero = g.fromJson(heroString, Hero.class);
        return hero;
    }
}
