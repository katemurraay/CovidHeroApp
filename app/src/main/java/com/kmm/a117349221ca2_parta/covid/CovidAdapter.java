package com.kmm.a117349221ca2_parta.covid;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kmm.a117349221ca2_parta.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CovidAdapter {

    public static final String baseURI = "https://api.covid19api.com/live/country";

    private static final Covid convertToCovidObject (String covidString) {
        Gson g = new Gson();
        Covid covid = g.fromJson(covidString, Covid.class);
        return covid;
    }


    public static final ArrayList<Covid> getIrishCases() throws JsonSyntaxException {
        String covidURI = baseURI + "/ireland" ;
        String covidString = HttpHandler.HttpGetExec(covidURI);

        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<Covid> covidArrayList = new ArrayList<>();
        try {
            /* Code below is based on:
            StackOverflow Answer to Question: "Parse JSON Array without Key in Android""
            Answered by: Bidhan
            https://stackoverflow.com/a/30586115
             */
            JSONArray itemArray=new JSONArray(covidString);
            for (int i = 0; i < itemArray.length(); i++) {
                stringArrayList.add(itemArray.get(i).toString());
            } //END

            for(String string: stringArrayList){
                covidArrayList.add(convertToCovidObject(string));
            }



        return covidArrayList;
    } catch (JSONException e) {
            e.printStackTrace();
        }
        return covidArrayList;
    }
}
