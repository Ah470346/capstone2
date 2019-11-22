package com.example.landandproperty4d.screen.viewmap4d;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadDataJSON extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {
        StringBuilder dulieu = null;
        try {
            URL url = new URL(strings[0]);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();

            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String dong = "";
            dulieu = new StringBuilder();

            while ((dong = bufferedReader.readLine())!=null){
                dulieu.append(dong);
            }
            bufferedReader.close();
            inputStream.close();
            reader.close();
            httpsURLConnection.disconnect();

            Log.d("dulieu",dulieu.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dulieu.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
