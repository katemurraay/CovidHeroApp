package com.kmm.a117349221ca2_parta.heroCRUD;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kmm.a117349221ca2_parta.HttpHandler;

public class HeroAdapter {

    /** Code below is based
     Android in Class Project: IS4448BeerServiceDemo,
     Created by Michael Gleeson on 14/02/2019
     */

    public static final String baseURI = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=";



    public static GeneralInfo getAllHeroes() throws JsonSyntaxException {
        String heroURI = baseURI + "getheroes" ;
        String heroString = HttpHandler.HttpGetExec(heroURI);
        Gson g = new Gson();
        return g.fromJson(heroString, GeneralInfo.class);
    }


    public static GeneralInfo createHero (Hero hero) throws JsonSyntaxException {
        String heroURI = baseURI + "createhero";
        Gson g = new Gson();
        String heroString = HttpHandler.HttpPostExec(heroURI, hero);
        return g.fromJson(heroString, GeneralInfo.class);
    }

    public static GeneralInfo updateHero (Hero hero) throws JsonSyntaxException {
        String heroURI = baseURI + "updatehero&id="+ hero.getHeroID();
        Gson g = new Gson();

        String heroString = HttpHandler.HttpPostExec(heroURI, hero);
        return g.fromJson(heroString, GeneralInfo.class);
    }

    public static GeneralInfo deleteHero (int heroId) throws JsonSyntaxException {
        String heroURI = baseURI + "deletehero&id=" + heroId;
        String heroString = HttpHandler.HttpDeleteExec(heroURI);
        Gson g = new Gson();
        return g.fromJson(heroString, GeneralInfo.class);
    } //END
}
