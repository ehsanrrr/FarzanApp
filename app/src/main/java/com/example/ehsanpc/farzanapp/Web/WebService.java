package com.example.ehsanpc.farzanapp.Web;

import android.util.Log;

import com.example.ehsanpc.farzanapp.Models.FarzanModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WebService {

    private String URL = "https://api.foursquare.com/v2/venues/search";
    String charset = "UTF-8";

    private String connectToServerByJson(String address, String requestMethod, String JsonDATA) {

        String JsonResponse = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Accept-Charset", charset);
            urlConnection.setRequestProperty("Content-Type", "application/json");
//set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);

            int responseStatusCode = urlConnection.getResponseCode();
            if (responseStatusCode == 200) {

            }

// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine);
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            JsonResponse = buffer.toString();
//response data
            Log.i(TAG, JsonResponse);
            //send to post execute
            return JsonResponse;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;

    }

    public List<FarzanModel> getMainList(boolean isInternetAvailable) {

        if (isInternetAvailable) {

//            String req = "{\"ll\":\"35.3968\",\"v\":\"20180919\",\"client_id\":\"PTKE1HNLFIRDFKZRQZ0V2BUZ5ERPTG5JWAHQOWWEI3U0I5QS\",\"client_secret\":\"4XYBYXDLNGTNBECNTKJKRY1PAZKAQ3DWRPM2NS2KAXAFNCMV\"}";
//            String response = connectToServerByJson(URL, "POST", req);
//            Log.i("LOG", response + "");

            String response = httpPostRequest(URL);

            List<FarzanModel> farzanModelList = new ArrayList<>();

            if (response != null) {

                try {
                    JSONObject mainObject = new JSONObject(response);
                    JSONObject responseObject = mainObject.getJSONObject("response");
//                    JSONObject venuesObject = responseObject.getJSONObject("venues");
                    JSONArray venuesArrey = responseObject.getJSONArray("venues");
                    for (int i = 0; i < venuesArrey.length(); i++){
                        JSONObject object = venuesArrey.getJSONObject(i);
                        FarzanModel model = new FarzanModel();
                        model.name = object.getString("name");
                        JSONObject locationObject = object.getJSONObject("location");
                        model.distance = locationObject.getInt("distance");
                        JSONArray formattedAddressArrey = locationObject.getJSONArray("formattedAddress");
                        model.formatterdAddress = formattedAddressArrey.getString(0);

                        farzanModelList.add(model);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }

            }

            return farzanModelList;
        } else
            return null;
    }

    public String httpPostRequest(String url) {
        String response = "";
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        try {
//            LogUtils.d("RequestManager", url + " ");
//            LogUtils.e("data::", " " + data);
            URL urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            String data = "";


            data += "&" + URLEncoder.encode("ll", "UTF-8") + "="
                    + URLEncoder.encode("35.396887,51.600169", "UTF-8");

            data += "&" + URLEncoder.encode("v", "UTF-8") + "="
                    + URLEncoder.encode("20180919", "UTF-8");

            data += "&" + URLEncoder.encode("client_id", "UTF-8") + "="
                    + URLEncoder.encode("PTKE1HNLFIRDFKZRQZ0V2BUZ5ERPTG5JWAHQOWWEI3U0I5QS", "UTF-8");

            data += "&" + URLEncoder.encode("client_secret", "UTF-8") + "="
                    + URLEncoder.encode("4XYBYXDLNGTNBECNTKJKRY1PAZKAQ3DWRPM2NS2KAXAFNCMV", "UTF-8");


            wr.write(data);
            wr.flush();

//            LogUtils.d("post response code", conn.getResponseCode() + " ");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200){

            }


            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
//            LogUtils.d("Error", "error");
        } finally {
            try {
                reader.close();
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception ex) {
            }
        }
//        LogUtils.d("RESPONSE POST", response);
        return response;
    }



}
