package com.example.sdkaccesscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sdkaccesscontrol.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    final String[] spicaToken = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        ProgressBar pgsBar = (ProgressBar)findViewById(R.id.pBar);
        pgsBar.setVisibility(View.GONE);

        //getSpicaToken();
    }

    private void setNavigationDrawer() {
        //drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout); // initiate a DrawerLayout

        NavigationView navView = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View
        // implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Fragment frag = null; // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
                // check selected menu item's id and replace a Fragment Accordingly
                Intent intent = null;
                switch (itemId ) {
                    case  R.id.nav_access:
                        onOptionsItemSelected(menuItem);
                        break;
                    case  R.id.nav_working:
                        intent = new Intent(MainActivity.this, WorkingFromHome.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_hours:
                        intent = new Intent(MainActivity.this, WorkingHours.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_profile:
                        intent = new Intent(MainActivity.this, UserProfile.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_settings:
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_logout:
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
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

    //WORKING FOR TEST WEBPAGE
    //public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public void sendMessage(View view) throws IOException {
        // Do something in response to button
        //setInfoMessage("Welcome, door are successfully opened.");
        System.out.println("Request: open the door");

        //getSpicaToken();

        ProgressBar pgsBar = (ProgressBar)findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);

        //String url = "https://ptsv2.com/t/k0sta-1642328743/post";
        String url = "http://"+  getString(R.string.server_ip) + ":5000/openDoor";

        JSONObject userData = new JSONObject();
        try {
            userData.put("id", getString(R.string.user_id) );
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //System.out.println("token gotten: " + spicaToken[0]);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, userData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //setInfoMessage("Response: " + response.toString());
                        pgsBar.setVisibility(View.GONE);
                        try {
                            setInfoMessage(response.getString("msg"), false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*
                        try {
                            if(response.getString("success").equals("1"))
                                setInfoMessage("Welcome, door are successfully opened.", false);
                            else
                                setInfoMessage("Please, connect to company wifi first.", true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                         */
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pgsBar.setVisibility(View.GONE);
                        System.out.println(error.toString());
                        //setInfoMessage(error.toString(), true);
                        setInfoMessage("Access is not allowed", true);
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    /*
    //public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public void sendMessage(View view) throws IOException {
        // Do something in response to button
        //setInfoMessage("Welcome, door are successfully opened.");
        System.out.println("Request: open the door");

        ProgressBar pgsBar = (ProgressBar)findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);

        getSpicaToken();

        String url = "http://161.53.167.71/TimeAPI/TimeEvent?SkipHolidays=True&numberOfDays=1&SkipWeekend1=True&SkipWeekend2=True&SkipDaysWithoutPlan=true&api-version=2";


        // Input
        Date date = new Date(System.currentTimeMillis());
        // Conversion
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String currentTime = sdf.format(date);

        JSONObject userData = new JSONObject();
        try {
            userData.put("id", 143);
            userData.put("UserId", 780);
            userData.put("Datetime", currentTime);
            userData.put("Data", 0);
            userData.put("Type", 4);
            //arival == 66
            userData.put("EventDefinitionId", 66);
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
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final String requestBody = userData.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println(response);
                pgsBar.setVisibility(View.GONE);
                Log.i("VOLLEY", response);
                setInfoMessage("Welcome, door are successfully opened.", false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
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
                    System.out.println("headers: " + response.headers);
                    //System.out.println("parsed response:" + parseNetworkResponse(response));

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


    }
    */

        /*
        //send GET request
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        setInfoMessage("Response is: "+ response.substring(0,500));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setInfoMessage("That didn't work!");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        */

        /*
        //change activity on button click
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        */

    public void getSpicaToken() {
        String url = "http://161.53.167.71/timeapi/Session/GetSession";
        /*
        curl --request POST \
        --url http://161.53.167.71/timeapi/Session/GetSession \
        --header 'Authorization: SpicaToken 9FD1ED9A-302B-4246-80BD-C88D82BFE74C' \
        --header 'Content-Type: application/json' \
        --data '{
        "Username": "demo",
                "Password": "demo",
                "Sid": ""
    }'
    */

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
}