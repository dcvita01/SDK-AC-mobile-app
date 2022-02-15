package com.example.sdkaccesscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sdkaccesscontrol.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class WorkingFromHome extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    final private String startButtonMessage = "Start working";
    final private String stopButtonMessage = "Stop working";
    final String[] spicaToken = new String[1];

    // Number of seconds displayed
    // on the stopwatch.
    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;

    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_from_home);

        setButtonText(startButtonMessage);
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationDrawer();

        if (savedInstanceState != null) {
            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds = savedInstanceState
                    .getInt("seconds");
            running = savedInstanceState
                    .getBoolean("running");
            wasRunning = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();
        //getSpicaToken();

    }

    // Save the state of the stopwatch
    // if it's about to be destroyed.
    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {

        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        seconds = savedInstanceState.getInt("seconds");
        running = savedInstanceState.getBoolean("running");
        wasRunning = savedInstanceState.getBoolean("wasRunning");
    }


    // If the activity is paused,
    // stop the stopwatch.
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private void runTimer()
    {
        // Get the text view.
        final TextView timeView
                = (TextView)findViewById(
                R.id.textView);
        timeView.setVisibility(View.GONE);

        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                if(seconds == 0)
                    timeView.setVisibility(View.INVISIBLE);
                else {
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;

                    // Format the seconds into hours, minutes,
                    // and seconds.
                    String time
                            = String
                            .format(Locale.getDefault(),
                                    "%d:%02d:%02d", hours,
                                    minutes, secs);

                    // Set the text view text.
                    timeView.setVisibility(View.VISIBLE);
                    timeView.setBackgroundResource(R.color.yellow_background);
                    timeView.setTextColor(getResources().getColor(R.color.yellow_text));
                    timeView.setText(time);
                }
                    // If running is true, increment the
                    // seconds variable.
                    if (running) {
                        seconds++;
                    }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }


    private void setButtonText(String msg){
        Button mainButton = (Button) findViewById(R.id.button);
        mainButton.setText(msg);
    }

    private void setNavigationDrawer() {
        //drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout); // initiate a DrawerLayout

        NavigationView navView = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View
        // implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment frag = null; // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
                // check selected menu item's id and replace a Fragment Accordingly
                Intent intent = null;
                switch (itemId ) {
                    case  R.id.nav_access:
                        intent = new Intent(WorkingFromHome.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_working:
                        onOptionsItemSelected(menuItem);
                        break;
                    case  R.id.nav_hours:
                        intent = new Intent(WorkingFromHome.this, WorkingHours.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_profile:
                        intent = new Intent(WorkingFromHome.this, UserProfile.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_settings:
                        intent = new Intent(WorkingFromHome.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_logout:
                        intent = new Intent(WorkingFromHome.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setInfoMessage(String msg, Boolean error){
        TextView textViewMsg = (TextView) findViewById(R.id.textView);

        if(!error){
            textViewMsg.setBackgroundResource(R.color.green_background);
            textViewMsg.setTextColor(getResources().getColor(R.color.green_text));
        }
        else{
            textViewMsg.setBackgroundResource(R.color.red_background);
            textViewMsg.setTextColor(getResources().getColor(R.color.red_text));
        }

        textViewMsg.setVisibility(View.VISIBLE);
        textViewMsg.setText(msg);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // Hide your View after 3 seconds
                textViewMsg.setVisibility(View.GONE);
            }
        }, 3000);


    }

    public void getSpicaToken() {
        String url = "http://161.53.167.71/timeapi/Session/GetSession";

        JSONObject userData = new JSONObject();
        try {
            userData.put("Username", "demo");
            userData.put("Password", "demo");
            userData.put("Sid", "");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(userData);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, userData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //System.out.println("Response: " + response.toString());
                        try {
                            //System.out.println("token gotten");
                            spicaToken[0] = response.getString("Token").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        //setInfoMessage("Get Token: That didn't work!", true);
                    }
                }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String GUID = "9FD1ED9A-302B-4246-80BD-C88D82BFE74C";
                headers.put("Authorization", "SpicaToken "+GUID);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

        //return spicaToken[0];
    }


    //public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public void sendMessageSpica(View view) throws JSONException {
        // Do something in response to button

        Button mainButton = (Button) findViewById(R.id.button);
        String currentStatus = mainButton.getText().toString();

        getSpicaToken();

        String url = "http://161.53.167.71/TimeAPI/TimeEvent?SkipHolidays=True&numberOfDays=1&SkipWeekend1=True&SkipWeekend2=True&SkipDaysWithoutPlan=true&api-version=2";


        // Input
        Date date = new Date(System.currentTimeMillis());
        // Conversion
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String currentTime = sdf.format(date);

        JSONObject userData = new JSONObject();
        userData.put("id", 143);
        userData.put("UserId", 780);
        userData.put("Datetime", currentTime);
        userData.put("Data", 0);
        userData.put("Type", 4);

        userData.put("Authentic", true);
        userData.put("IsDeleted", false);
        userData.put("JobDefinitionId", 0);
        userData.put("AdditionalData", 0);
        userData.put("TimeStamp", currentTime);
        userData.put("Comment", "Added via SDK mobile app");
        userData.put("Origin", 7);
        userData.put("CustomField1URL", "");
        userData.put("CustomField2Text", "");
        userData.put("Longitude", "");
        userData.put("Latitude", "");

        if (currentStatus.equals(startButtonMessage)) {
            //setButtonText(stopBottunMessage);
            //userData.put("request", "start");
            System.out.println("Request: start working");
            //start == 66
            userData.put("EventDefinitionId", 66);
        } else {
            //setButtonText(startBottunMessage);
            //userData.put("request", "stop");
            System.out.println("Request: stop working");
            //stop == 67
            userData.put("EventDefinitionId", 67);
        }


        final String requestBody = userData.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println(response);
                Log.i("VOLLEY", response);
                if (currentStatus.equals(startButtonMessage)) {
                    setButtonText(stopButtonMessage);
                    running = true;
                    //setInfoMessage("started...", false);
                } else {
                    setButtonText(startButtonMessage);
                    running = false;
                    //setInfoMessage("stoped...", false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                setInfoMessage("Error, not allowed.", true);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers

                    //System.out.println("headers: " + response.headers);

                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String GUID = "9FD1ED9A-302B-4246-80BD-C88D82BFE74C";
                headers.put("Authorization", "SpicaToken " + GUID + ":" + spicaToken[0] );
                headers.put("Content-Type", "application/json");
                return headers;
            }
        } ;

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
/*
        String url = "https://ptsv2.com/t/qxgsr-1642335088/post";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, userData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //setInfoMessage("Response: " + response.toString());
                        try {
                            if (response.getString("success").equals("1")) {

                                if (currentStatus.equals(startBottunMessage)) {
                                    setButtonText(stopBottunMessage);
                                    running = true;
                                    //setInfoMessage("started...", false);
                                } else {
                                    setButtonText(startBottunMessage);
                                    running = false;
                                    //setInfoMessage("stoped...", false);
                                }
                            } else
                                setInfoMessage("Error, not allowed.", true);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //System.out.println(error.toString());
                        setInfoMessage("That didn't work!", true);
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
*/

    }


    public void sendMessage(View view) throws JSONException {
        // Do something in response to button

        Button mainButton = (Button) findViewById(R.id.button);
        String currentStatus = mainButton.getText().toString();

        //String url = "https://ptsv2.com/t/qxgsr-1642335088/post";
        String url = "http://"+  getString(R.string.server_ip) + ":5000/homeWork";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String thisDate = timestamp.toString().substring(8, 10) + "/" + timestamp.toString().substring(5, 7) + "/" + timestamp.toString().substring(0, 4);
        String thisTime = timestamp.toString().substring(11, 16);

        JSONObject userData = new JSONObject();
        try {
            userData.put("id", getString(R.string.user_id));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (currentStatus.equals(startButtonMessage)) {
            //setButtonText(stopBottunMessage);
            System.out.println("Request: start working");

        } else {
            //setButtonText(startBottunMessage);
            System.out.println("Request: stop working");
        }


        final String requestBody = userData.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println(response);
                Log.i("VOLLEY", response);
                if (currentStatus.equals(startButtonMessage)) {
                    setButtonText(stopButtonMessage);
                    running = true;
                    //setInfoMessage("started...", false);
                } else {
                    setButtonText(startButtonMessage);
                    running = false;
                    //setInfoMessage("stoped...", false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                setInfoMessage("Error, not allowed.", true);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }


        } ;

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
/*
        String url = "https://ptsv2.com/t/qxgsr-1642335088/post";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, userData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //setInfoMessage("Response: " + response.toString());
                        try {
                            if (response.getString("success").equals("1")) {

                                if (currentStatus.equals(startBottunMessage)) {
                                    setButtonText(stopBottunMessage);
                                    running = true;
                                    //setInfoMessage("started...", false);
                                } else {
                                    setButtonText(startBottunMessage);
                                    running = false;
                                    //setInfoMessage("stoped...", false);
                                }
                            } else
                                setInfoMessage("Error, not allowed.", true);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //System.out.println(error.toString());
                        setInfoMessage("That didn't work!", true);
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
*/

    }

}