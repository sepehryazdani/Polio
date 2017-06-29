package com.autoremoteexample.polio.common;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by unknwon on 6/13/2017.
 */

class SendDataToServer extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String[] params) {
        Log.v("navidi", this.getClass().getCanonicalName() + "::doinbackground::star");
        String polioJsonResponse = null;
        String polioJsonData = params[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://poliostudy.vpsoft.co/api/v1/entries/"+params[1]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            //set headers and method
            Log.v("navidi", this.getClass().getCanonicalName() + "::doinbackground::after opening connection");
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(polioJsonData);
            Log.v("navidi", this.getClass().getCanonicalName() + "::doinbackground::after writing data");
            writer.close();


            InputStream inputStream = urlConnection.getInputStream();
            Log.v("navidi", this.getClass().getCanonicalName() + "::doinbackground::getting inputstream");
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Do Nothing
                Log.e("navidi", "Input Stream was NULL!!!!");
                return null;
            }
            Log.v("navidi", this.getClass().getCanonicalName() + "::doinbackground::getting buffer stream");
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            Log.v("navidi", this.getClass().getCanonicalName() + "::doinbackground::" + buffer.toString());
            if (buffer.length() == 0) {
                // Stream was empty.
                Log.e("navidi", this.getClass().getCanonicalName() + "::doinbackground::rsp from server was empty");
                return null;
            }

            Log.v("navidi", this.getClass().getCanonicalName() + "::doinbackground::aftrea reading the input from server");
            polioJsonResponse = buffer.toString();

            Log.v("navidi", "API rsp:" + polioJsonResponse + "");


//            try {
            //send to post execute
//                this.onPostExecute(PolioJsonResponse);
            return polioJsonResponse;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;

        } catch (IOException e) {
            Log.e("navidi", this.getClass().getCanonicalName() + "::ioexception");
            Toast.makeText(null, "ERROR: Calling API.", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        } catch (Exception e) {
            Log.e("navidi", this.getClass().getCanonicalName() + "::exception");
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("navidi", this.getClass().getCanonicalName() + "Error closing stream", e);
                }
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        Log.i("navidi", "We are on post excecute" );
    }

}

