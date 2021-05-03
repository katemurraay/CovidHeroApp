package com.kmm.a117349221ca2_parta.covid;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kmm.a117349221ca2_parta.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/** Code below is based
    Android in Class Project: IS4448BeerServiceDemo,
   Created by Michael Gleeson on 14/02/2019
   */


public class CovidAdapter {

    public static final String baseURI = "https://api.covid19api.com/live/country";

    private static Covid convertToCovidObject (String covidString) {
        Gson g = new Gson();
        return g.fromJson(covidString, Covid.class);
    }


    public static ArrayList<Covid> getCovidCases(String country) throws JsonSyntaxException {
        String covidURI = baseURI + "/" + country ;
        String covidString = HttpHandler.HttpGetExec(covidURI);

        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<Covid> covidArrayList = new ArrayList<>();
        try {
            /* (2)Code below is based on:
            StackOverflow Answer to Question: "Parse JSON Array without Key in Android""
            Answered by: Bidhan
            https://stackoverflow.com/a/30586115
             */
            JSONArray itemArray=new JSONArray(covidString);
            for (int i = 0; i < itemArray.length(); i++) {
                stringArrayList.add(itemArray.get(i).toString());
            } //END (2)

            for(String string: stringArrayList){
                covidArrayList.add(convertToCovidObject(string));
            }



        return covidArrayList;
    } catch (JSONException e) {
            e.printStackTrace();
        }
        return covidArrayList;
    } //END
}
