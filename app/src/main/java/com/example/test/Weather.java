package com.example.test;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather extends AppCompatActivity {
    String city = "Casablanca";
    String country = "ma";
    String API = "e722b193d66086a731afec1c6e5510dd";
    String url = "https://api.openweathermap.org/data/2.5/weather";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"heel");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather);
        getWeatherDetails();



    }
    private static String formatSunriseTime(long sunriseTimestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return sdf.format(new Date(sunriseTimestamp));
    }
    public void getWeatherDetails() {
        String tempUrl = "";
        tempUrl = url + "?q=" + city + "," + country + "&appid=" + API + "&units=metric";
        Log.e(TAG,"I m here");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG,"heelzdzdzd");
                    Log.d("response",response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject main = jsonResponse.getJSONObject("main");
                        JSONObject sys = jsonResponse.getJSONObject("sys");
                        JSONObject wind = jsonResponse.getJSONObject("wind");

                        JSONArray weatherArray = jsonResponse.getJSONArray("weather");
                        JSONObject weather = weatherArray.getJSONObject(0);
                        long updatedAt = jsonResponse.getLong("dt");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
                        String updatedAtText = "Updated at: " + sdf.format(new Date(updatedAt * 1000));

                        String temp = main.getString("temp") + "°C";
                        String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                        String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                        String pressure = main.getString("pressure");
                        String humidity = main.getString("humidity");

                        long sunrise = sys.getLong("sunrise");
                        long sunset = sys.getLong("sunset");
                        String windSpeed = wind.getString("speed");
                        String weatherDescription = weather.getString("description");

                        String address = jsonResponse.getString("name") + ", " + sys.getString("country");
                        TextView address_text = (TextView)findViewById(R.id.address);
                        address_text.setText(address);
                        TextView updatedat_text = (TextView)findViewById(R.id.updated_at);
                        updatedat_text.setText(updatedAtText);
                        TextView status_text = (TextView)findViewById(R.id.status);
                        status_text.setText(weatherDescription);
                        TextView temp_text = (TextView)findViewById(R.id.temp);
                        temp_text.setText(temp);
                        TextView tempmin_text = (TextView)findViewById(R.id.temp_min);
                        tempmin_text.setText(tempMin);
                        TextView tempmax_text = (TextView)findViewById(R.id.temp_max);
                        tempmax_text.setText(tempMax);
                        TextView sunrise_text = (TextView)findViewById(R.id.sunrise);
                        sunrise_text.setText(formatSunriseTime(sunrise));
                        TextView sunset_text = (TextView)findViewById(R.id.sunset);
                        sunset_text.setText( formatSunriseTime(sunset));
                        TextView wind_text = (TextView)findViewById(R.id.wind);
                        wind_text.setText(windSpeed);
                        TextView pressure_text = (TextView)findViewById(R.id.pressure);
                        pressure_text.setText(pressure);
                        TextView humidity_text = (TextView)findViewById(R.id.humidity);
                        humidity_text.setText(humidity);
                        findViewById(R.id.loader).setVisibility(View.GONE);
                        findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    /* Views populated, Hiding the loader, Showing the main design */




                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"error");
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        }
    }
