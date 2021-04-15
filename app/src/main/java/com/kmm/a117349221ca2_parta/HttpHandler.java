package com.kmm.a117349221ca2_parta;

import android.util.Log;

import com.kmm.a117349221ca2_parta.heroCRUD.Hero;
import com.squareup.okhttp.internal.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * Created by Michael Gleeson on 14/02/2019
 */

public class HttpHandler {

    private static String streamToString(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        for(String line = br.readLine(); line != null; line = br.readLine())
            out.append(line);
        br.close();
        return out.toString();
    }

    public static final String HttpGetExec(String uri) {

        String s = "no response";
        HttpURLConnection conn = null;
        int http_status = 0;

        try {
            URL url = new URL(uri);
            conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            http_status = conn.getResponseCode();
            if (http_status == 200) {
                s = streamToString(in);
                Log.d("HTTPGet", s);
            } else {
                s = "bad response";
                Log.d("HTTPGet", s);
            }


        } catch (MalformedURLException m) {
            s = "malformed URL exception";
            Log.d("HTTPGet", s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return s;

    }

    public static final String HttpDeleteExec (String uri) {

        String s = "no response";
        HttpURLConnection conn = null;
        int http_status = 0;

        try {
            URL url = new URL(uri);

            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("DELETE"); //We be doing a DELETE

            InputStream in = conn.getInputStream();
            http_status = conn.getResponseCode();

            if (http_status == 200) {
                s = streamToString(in);
            } else {
                s = "bad response";
            }
        } catch (MalformedURLException m) {
            s = "malformed URL exception";
        } catch (IOException e) {
            s = "IO exception";
        } finally {
            conn.disconnect();
        }
        return s;

    } //END

/* Code below is based on:
StackOverflow Anser to Question: "Java - sending HTTP parameters via POST method easily",
answered by: Chirag Patel,
https://stackoverflow.com/questions/4205980/java-sending-http-parameters-via-post-method-easily/51924763#51924763
 */

    public static final String HttpPostExec (String uri, Hero hero) {

        String s = "no response";
        HttpURLConnection conn = null;
        int http_status = 0;

        try {

            URL url = new URL(uri);
            Map<String,String> params = new LinkedHashMap<>();
            params.put("id", String.valueOf(hero.getHeroID()));
            params.put("name", hero.getHeroName());
            params.put("realname", hero.getHeroName());
            params.put("rating",String.valueOf(hero.getRating()));
            params.put("teamaffiliation", hero.getTeamAffiliation());

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            String urlParameters = postData.toString();
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(urlParameters);
            writer.flush();
            writer.close(); //END

            InputStream in = conn.getInputStream();

            http_status = conn.getResponseCode();

            if (http_status == 200) {
                s = streamToString(in);


           } else {
              Log.d("POST_EXEC", s);
            }
        } catch (MalformedURLException m) {
            s = "malformed URL exception";
            Log.d("POST_EXEC", m.toString());
        } catch (IOException e) {
            s = "IO exception";
         e.printStackTrace();
                    } finally {
            conn.disconnect();
        }
        return s;

    }




}
