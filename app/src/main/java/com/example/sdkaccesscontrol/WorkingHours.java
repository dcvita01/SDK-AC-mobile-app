/*package com.example.sdkaccesscontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class WorkingHours extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);
    }
}*/

package com.example.sdkaccesscontrol;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
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
        import com.android.volley.toolbox.JsonArrayRequest;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.sdkaccesscontrol.ui.login.LoginActivity;
        import com.github.mikephil.charting.charts.BarChart;
        import com.github.mikephil.charting.components.AxisBase;
        import com.github.mikephil.charting.components.XAxis;
        import com.github.mikephil.charting.components.YAxis;
        import com.github.mikephil.charting.data.BarData;
        import com.github.mikephil.charting.data.BarDataSet;
        import com.github.mikephil.charting.data.BarEntry;
        import com.github.mikephil.charting.formatter.IAxisValueFormatter;
        import com.github.mikephil.charting.formatter.ValueFormatter;
        import com.github.mikephil.charting.utils.ColorTemplate;
        import com.github.mikephil.charting.utils.ViewPortHandler;
        import com.google.android.material.navigation.NavigationView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.UnsupportedEncodingException;
        import java.sql.Timestamp;
        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.time.Instant;
        import java.time.LocalDate;
        import java.time.LocalTime;
        import java.time.format.DateTimeFormatter;
        import java.time.temporal.TemporalAccessor;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Map;
        import java.util.NavigableMap;
        import java.util.TreeMap;


public class WorkingHours extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    final String[] spicaToken = new String[1];

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);

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

        //getSpicaToken();

        //getData(this);

        getServerData(this);

        //setChart();

    }

    public void setChart(){
        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);

        // calling method to set bar entries.
        //setBarEntriesToZero();
        barChart.setExtraOffsets(20, 20, 20, 20);
        // creating a new bar data set.
        barChart.getXAxis().setDrawGridLines(false);

        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        rightYAxis.setDrawGridLines(false);

        rightYAxis.setDrawLabels(false);
        //barChart.getXAxis().setDrawLabels(false);

        //String[] values = new String[] { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};



        barDataSet = new BarDataSet(barEntriesArrayList, "");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        barData.setBarWidth(0.5f);

        // adding color to our bar data set.

        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        barDataSet.setDrawValues(false);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);



    }



    private void setBarEntriesToZero() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 0));
        barEntriesArrayList.add(new BarEntry(2f, 0));
        barEntriesArrayList.add(new BarEntry(3f, 0));
        barEntriesArrayList.add(new BarEntry(4f, 0));
        barEntriesArrayList.add(new BarEntry(5f, 0));
        barEntriesArrayList.add(new BarEntry(6f, 0));
        barEntriesArrayList.add(new BarEntry(7f, 0));
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
                        intent = new Intent(WorkingHours.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_working:
                        intent = new Intent(WorkingHours.this, WorkingFromHome.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_hours:
                        onOptionsItemSelected(menuItem);
                        break;
                    case  R.id.nav_profile:
                        intent = new Intent(WorkingHours.this, UserProfile.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_settings:
                        intent = new Intent(WorkingHours.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_logout:
                        intent = new Intent(WorkingHours.this, LoginActivity.class);
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

    public void getSpicaData(Context context){

        //creating views for showing data
        LinearLayout linearLayout = findViewById(R.id.rootLayout);
        //setContentView(linearLayout);
        //linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(100,0,0,0);
        //linearLayout.setLayoutParams(params);

        RequestQueue queue = Volley.newRequestQueue(this);


        //todo: get data from spica

        System.out.println("SpicaToken: " + spicaToken[0]);

//send GET request
// Instantiate the RequestQueue.

        String url ="http://161.53.167.71/TimeApi/TimeEvent?dateFrom=2022-01-24T16:50:59.7630000Z&dateTo=2022-01-28T17:00:59.7630000Z&userId=780";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Response: " + response.toString());
                        try {
                            parseSpicaData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println("VolleyError: " + error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String GUID = "9FD1ED9A-302B-4246-80BD-C88D82BFE74C";
                headers.put("Authorization", "SpicaToken " + GUID + ":" + spicaToken[0] );
                headers.put("Content-Type", "application/json");
                return headers;
            }
        } ;

        queue.add(jsonArrayRequest);

    }

    public void getData(Context context){

        //creating views for showing data
        LinearLayout linearLayout = findViewById(R.id.rootLayout);
        //setContentView(linearLayout);
        //linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(100,0,0,0);
        //linearLayout.setLayoutParams(params);

        RequestQueue queue = Volley.newRequestQueue(this);


        //todo: get data from spica

        System.out.println("SpicaToken: " + spicaToken[0]);

//send GET request
// Instantiate the RequestQueue.

        String url ="https://ptsv2.com/t/y7yus-1642696553/post";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //System.out.println("Response: " + response.toString());
                        try {
                            Iterator<String> keys = response.keys();

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(100,0,0,0);


                            while(keys.hasNext()) {
                                String date = keys.next();
                                //if (response.get(date) instanceof JSONArray)
                                //System.out.println(date);
                                JSONArray workingPeriodObjects = response.getJSONArray(date);

                                TextView textViewDate = new TextView(context);
                                textViewDate.setLayoutParams(params);
                                textViewDate.setPadding(30,80,30,5);
                                textViewDate.setTextSize(18);
                                textViewDate.setText(date);
                                linearLayout.addView(textViewDate);

                                List<String> workingPeriods = new ArrayList<String>();
                                for(int i=0; i<workingPeriodObjects.length(); i++) {

                                    String time1 = workingPeriodObjects.getJSONObject(i).getString("start");
                                    //System.out.print(time1);

                                    TextView textViewWorkingPeriod = new TextView(context);
                                    textViewWorkingPeriod.setLayoutParams(params);
                                    textViewWorkingPeriod.setPadding(30,5,30,5);
                                    textViewWorkingPeriod.setTextSize(18);
                                    textViewWorkingPeriod.setBackgroundResource(R.color.white);
                                    textViewWorkingPeriod.setText(time1);


                                    //System.out.println("test");
                                    String time2 = workingPeriodObjects.getJSONObject(i).optString("stop", "");
                                    boolean noStop = false;
                                    if(time2.equals("")) {
                                        noStop = true;
                                        time2 = new Date().toString().substring(11, 19);
                                    }
                                    System.out.println(time2);

                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                    Date date1 = format.parse(time1);
                                    Date date2 = format.parse(time2);

                                    long diff = date2.getTime() - date1.getTime();
                                    long diffSeconds = diff / 1000 % 60;
                                    long diffMinutes = diff / (60 * 1000) % 60;
                                    long diffHours = diff / (60 * 60 * 1000) % 24;

                                    String total="";
                                    if(diffHours<10)
                                        total += 0;
                                    total += diffHours+":";
                                    if(diffMinutes<10)
                                        total += 0;
                                    total += diffMinutes+":";
                                    if(diffSeconds<10)
                                        total += 0;
                                    total += diffSeconds;

                                    workingPeriods.add(total);
                                    //System.out.print(" - " + time2 + "\t\t\t" + diffHours+":"+diffMinutes+":"+diffSeconds);
                                    //textViewWorkingPeriod.setLayoutParams(params);
                                    //textViewWorkingPeriod.setBackgroundResource(R.color.white);
                                    if(noStop)
                                        textViewWorkingPeriod.append("\t\t\t\t\t\t\t\t\t\t\t\t");

                                    else
                                        textViewWorkingPeriod.append(" - " + time2);
                                    textViewWorkingPeriod.append( "\t\t\t\t\t\t\t\t\t\t\t" + total);

                                    linearLayout.addView(textViewWorkingPeriod);


                                   // System.out.println();
                                }

                                //todo: print sum for day
                                //SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                //long sum=0;
                                int sumSeconds = 0;
                                int sumMinutes = 0;
                                int sumHours = 0;
                                for(int i=0; i<workingPeriods.size(); i++){
                                    sumHours += Integer.parseInt(workingPeriods.get(i).substring(0,2));
                                    sumMinutes += Integer.parseInt(workingPeriods.get(i).substring(3,5));
                                    sumSeconds +=  Integer.parseInt(workingPeriods.get(i).substring(6,8));
                                }

                                String dayTotal="";

                                sumMinutes+=sumSeconds/60;
                                sumSeconds=sumSeconds%60;
                                if (sumSeconds < 10)
                                    dayTotal = "0";
                                dayTotal = dayTotal + sumSeconds;

                                sumHours+=sumMinutes/60;
                                sumMinutes=sumMinutes%60;
                                dayTotal = sumMinutes+":"+dayTotal;
                                if(sumMinutes<10)
                                    dayTotal = 0 + dayTotal;

                                dayTotal = sumHours+":"+dayTotal;
                                if(sumHours<10)
                                    dayTotal = 0 + dayTotal;

                                TextView textViewDailyTotal = new TextView(context);
                                textViewDailyTotal.setLayoutParams(params);
                                textViewDailyTotal.setPadding(30,5,30,5);
                                textViewDailyTotal.setTextSize(18);
                                textViewDailyTotal.setTypeface(null,  Typeface.BOLD);
                                textViewDailyTotal.setTextColor(getResources().getColor(R.color.purple_700));
                                textViewDailyTotal.setBackgroundResource(R.color.white);
                                textViewDailyTotal.setText("Daily total\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   " + dayTotal);

                                linearLayout.addView(textViewDailyTotal);
                            }

                       } catch (JSONException | ParseException e ) {
                        //} catch (JSONException  e ) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        System.out.println("That didn't work!");
                    }
                });

        queue.add(jsonObjectRequest);

    }


    public void getServerData(Context context){


        //creating views for showing data
        LinearLayout linearLayout = findViewById(R.id.rootLayout);
        //setContentView(linearLayout);
        //linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(100,0,0,0);
        //linearLayout.setLayoutParams(params);

        barEntriesArrayList = new ArrayList<>();
        setBarEntriesToZero();

        RequestQueue queue = Volley.newRequestQueue(this);

//send GET request
// Instantiate the RequestQueue.

        //String url ="https://ptsv2.com/t/33f7g-1644001911/post";
        String url ="http://"+  getString(R.string.server_ip) + ":5000/userDates";


        JSONObject userData = new JSONObject();
        try {
            userData.put("id", getString(R.string.user_id));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /////////////////////////////////////


        final String requestBody = userData.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseString) {
                //System.out.println("onResponse: " + responseString);

                JSONArray response = null;
                try {
                    response = new JSONArray( responseString);
                    //System.out.println("onResponse JSON: " + response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(100, 0, 0, 0);

                TextView textViewWeekTotal = new TextView(context);
                textViewWeekTotal.setLayoutParams(params);
                textViewWeekTotal.setPadding(30, 30, 30, 5);
                textViewWeekTotal.setTextSize(24);
                textViewWeekTotal.setTypeface(null, Typeface.BOLD);
                textViewWeekTotal.setTextColor(getResources().getColor(R.color.purple_700));
                textViewWeekTotal.setText("This week total:");
                linearLayout.addView(textViewWeekTotal);




                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                String thisDate = timestamp.toString().substring(8, 10) + "/" + timestamp.toString().substring(5, 7) + "/" + timestamp.toString().substring(0, 4);
                String thisTime = timestamp.toString().substring(11, 16);



                Date currentDate=new Date();
                Calendar c = Calendar.getInstance();

                c.setTime(currentDate);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);

                //System.out.println("Today: " + currentDate.toString());

                c.add(Calendar.DATE, -1);
                Date yesterdayDate = c.getTime();
                Timestamp yesterdayTimestamp = new Timestamp(c.getTime().getTime());
                String yesterday = yesterdayTimestamp.toString().substring(8, 10) + "/" + yesterdayTimestamp.toString().substring(5, 7) + "/" + yesterdayTimestamp.toString().substring(0, 4);
                //System.out.println("Yesterday: " + yesterday);

                Date lastMondayDate = new Date();
                Boolean isMonday = false;
                while(!isMonday) {
                    c.add(Calendar.DATE, -1);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    if(dayOfWeek==2) {
                        isMonday = true;
                        lastMondayDate = c.getTime();
                        Timestamp lastMondayTimestamp = new Timestamp(c.getTime().getTime());
                        String lastMonday = lastMondayTimestamp.toString().substring(8, 10) + "/" + lastMondayTimestamp.toString().substring(5, 7) + "/" + lastMondayTimestamp.toString().substring(0, 4);
                        //System.out.println("Last monday: " + lastMonday);
                    }
                }

                List<String> dailyWorking = new ArrayList<String>();

                //for (int i = 0; i < response.length(); i++) {
                for (int i=response.length()-1; i >= 0; i--) {
                    try {
                        JSONObject dateRecord = (JSONObject) response.get(i);
                        //skip this date if there is no record for user
                        if(dateRecord.getJSONArray("users").length()==0)
                            continue;

                        String date = dateRecord.getString("date");

                        TextView textViewDate = new TextView(context);
                        textViewDate.setLayoutParams(params);
                        textViewDate.setPadding(30, 30, 30, 5);
                        textViewDate.setTextSize(18);

                        if(date.equals(thisDate))
                            textViewDate.setText("Today");
                        else if(date.equals(yesterday))
                            textViewDate.setText("Yesterday");
                        else
                            textViewDate.setText(date);

                        linearLayout.addView(textViewDate);

                        List<String> workingPeriods = new ArrayList<String>();
                        JSONArray records = ((JSONObject)dateRecord.getJSONArray("users").get(0) ).getJSONArray("userWorkTime");

                        for (int j = 0; j < records.length(); j++) {
                            JSONObject record = (JSONObject) records.get(j);

                            String start = record.getString("startTime");
                            TextView textViewWorkingPeriod = new TextView(context);
                            textViewWorkingPeriod.setLayoutParams(params);
                            textViewWorkingPeriod.setPadding(30, 5, 30, 5);
                            textViewWorkingPeriod.setTextSize(18);
                            textViewWorkingPeriod.setBackgroundResource(R.color.white);
                            textViewWorkingPeriod.setText(start);

                            String end = record.getString("endTime");

                            if (end.equals("-")) {
                                if (date.equals(thisDate)) {
                                    end = thisTime;
                                    textViewWorkingPeriod.append("\t\t\t\t\t\t\t\t");
                                } else {
                                    end = "18:00";
                                    textViewWorkingPeriod.append(" - " + end);
                                }

                            } else {
                                textViewWorkingPeriod.append(" - " + end);
                            }


                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            Date date1 = format.parse(start + ":00");
                            Date date2 = format.parse(end + ":00");

                            long diff = date2.getTime() - date1.getTime();
                            //long diffSeconds = diff / 1000 % 60;
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000) % 24;

                            String total = "";
                            if (diffHours < 10)
                                total += 0;
                            total += diffHours + ":";
                            if (diffMinutes < 10)
                                total += 0;
                            total += diffMinutes;

                            workingPeriods.add(total);

                            textViewWorkingPeriod.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + total);
                            linearLayout.addView(textViewWorkingPeriod);
                        }

                        //calculate daily total
                        String dayTotal = calculateHourSum(workingPeriods);

                        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                        //todo save just working periods for one week
                        //if(lastMondayDate<=date1)
                        if(date1.after(lastMondayDate) || date1.equals(lastMondayDate)) {
                            //MONDAY == 1
                            Calendar c1 = Calendar.getInstance();
                            c1.setTime(date1);
                            float dayOfWeek = c1.get(Calendar.DAY_OF_WEEK)-1;
                            if(dayOfWeek<=0)
                                dayOfWeek+=7;
                            dailyWorking.add(dayTotal);
                            barEntriesArrayList.add(new BarEntry(dayOfWeek, Float.parseFloat(dayTotal.substring(0,2)) + Float.parseFloat(dayTotal.substring(3,5))/60));

                        }



                        LinearLayout.LayoutParams paramsLast = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        paramsLast.setMargins(100, 0, 0, 50);

                        TextView textViewDailyTotal = new TextView(context);
                        textViewDailyTotal.setLayoutParams(paramsLast);
                        textViewDailyTotal.setPadding(30, 5, 30, 5);
                        textViewDailyTotal.setTextSize(18);
                        textViewDailyTotal.setTypeface(null, Typeface.BOLD);
                        textViewDailyTotal.setTextColor(getResources().getColor(R.color.purple_700));
                        textViewDailyTotal.setBackgroundResource(R.color.white);
                        textViewDailyTotal.setText("Daily total\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + dayTotal);

                        linearLayout.addView(textViewDailyTotal);


                    } catch (JSONException | ParseException jsonException) {
                        jsonException.printStackTrace();
                    }

                }

                String weeklyTotal = calculateHourSum(dailyWorking);
                textViewWeekTotal.append("\t\t\t\t\t\t\t\t\t\t" + weeklyTotal);
                setChart();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    System.out.println(error.toString());
                    System.out.println("That didn't work!");
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

        queue.add(stringRequest);

        /*

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //System.out.println("Response: " + response.toString());

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(100, 0, 0, 0);

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                        String thisDate = timestamp.toString().substring(8, 10) + "/" + timestamp.toString().substring(5, 7) + "/" + timestamp.toString().substring(0, 4) ;
                        String thisTime = timestamp.toString().substring(11, 16);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject dateRecord = (JSONObject) response.get(i);

                                String date = dateRecord.getString("date");
                                TextView textViewDate = new TextView(context);
                                textViewDate.setLayoutParams(params);
                                textViewDate.setPadding(30, 30, 30, 5);
                                textViewDate.setTextSize(18);
                                textViewDate.setText(date);
                                linearLayout.addView(textViewDate);

                                List<String> workingPeriods = new ArrayList<String>();
                                JSONArray records = dateRecord.getJSONArray("users");
                                for (int j = 0; j < records.length(); j++) {
                                    JSONObject record = (JSONObject) records.get(j);

                                    String start = record.getString("startTime");
                                    TextView textViewWorkingPeriod = new TextView(context);
                                    textViewWorkingPeriod.setLayoutParams(params);
                                    textViewWorkingPeriod.setPadding(30, 5, 30, 5);
                                    textViewWorkingPeriod.setTextSize(18);
                                    textViewWorkingPeriod.setBackgroundResource(R.color.white);
                                    textViewWorkingPeriod.setText(start);

                                    String end = record.getString("endTime");

                                    if(end.equals("-") ){
                                        if (date.equals(thisDate)) {
                                            end = thisTime;
                                            textViewWorkingPeriod.append("\t\t\t\t\t\t\t\t");
                                        }
                                        else {
                                            end = "18:00";
                                            textViewWorkingPeriod.append(" - " + end);
                                        }

                                    }
                                    else{
                                        textViewWorkingPeriod.append(" - " + end);
                                    }



                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                    Date date1 = format.parse(start+":00");
                                    Date date2 = format.parse(end+":00");

                                    long diff = date2.getTime() - date1.getTime();
                                    //long diffSeconds = diff / 1000 % 60;
                                    long diffMinutes = diff / (60 * 1000) % 60;
                                    long diffHours = diff / (60 * 60 * 1000) % 24;

                                    String total = "";
                                    if (diffHours < 10)
                                        total += 0;
                                    total += diffHours + ":";
                                    if (diffMinutes < 10)
                                        total += 0;
                                    total += diffMinutes ;

                                    workingPeriods.add(total);

                                    textViewWorkingPeriod.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + total);
                                    linearLayout.addView(textViewWorkingPeriod);
                                }

                                //print sum for day

                                //SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                //long sum=0;
                                //int sumSeconds = 0;
                                int sumMinutes = 0;
                                int sumHours = 0;
                                for (int k = 0; k < workingPeriods.size(); k++) {
                                    sumHours += Integer.parseInt(workingPeriods.get(k).substring(0, 2));
                                    sumMinutes += Integer.parseInt(workingPeriods.get(k).substring(3, 5));
                                    //sumSeconds += Integer.parseInt(workingPeriods.get(k).substring(6, 8));
                                }

                                String dayTotal = "";

                                sumHours += sumMinutes / 60;
                                sumMinutes = sumMinutes % 60;
                                dayTotal += sumMinutes ;
                                if (sumMinutes < 10)
                                    dayTotal = 0 + dayTotal;

                                dayTotal = sumHours + ":" + dayTotal;
                                if (sumHours < 10)
                                    dayTotal = 0 + dayTotal;

                                LinearLayout.LayoutParams paramsLast = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                paramsLast.setMargins(100, 0, 0, 50);

                                TextView textViewDailyTotal = new TextView(context);
                                textViewDailyTotal.setLayoutParams(paramsLast);
                                textViewDailyTotal.setPadding(30, 5, 30, 5);
                                textViewDailyTotal.setTextSize(18);
                                textViewDailyTotal.setTypeface(null, Typeface.BOLD);
                                textViewDailyTotal.setTextColor(getResources().getColor(R.color.purple_700));
                                textViewDailyTotal.setBackgroundResource(R.color.white);
                                textViewDailyTotal.setText("Daily total\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + dayTotal);

                                linearLayout.addView(textViewDailyTotal);


                            } catch (JSONException | ParseException jsonException) {
                                jsonException.printStackTrace();
                            }

                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        System.out.println("That didn't work!");
                    }
                });


        queue.add(jsonArrayRequest);
*/
    }

    public String calculateHourSum(List<String> workingPeriods){
        String total = "";

        //int sumSeconds = 0;
        int sumMinutes = 0;
        int sumHours = 0;
        for (int k = 0; k < workingPeriods.size(); k++) {
            sumHours += Integer.parseInt(workingPeriods.get(k).substring(0, 2));
            sumMinutes += Integer.parseInt(workingPeriods.get(k).substring(3, 5));
            //sumSeconds += Integer.parseInt(workingPeriods.get(k).substring(6, 8));
        }

        sumHours += sumMinutes / 60;
        sumMinutes = sumMinutes % 60;
        total += sumMinutes;
        if (sumMinutes < 10)
            total = 0 + total;

        total = sumHours + ":" + total;
        if (sumHours < 10)
            total = 0 + total;

        return total;
    }


    public void parseSpicaData(JSONArray response) throws JSONException, ParseException {
        NavigableMap<Date, List<String>> events = new TreeMap<>();

        //List<List<String>> listOfLists = new ArrayList<List<String>>();

        for(int i=0; i<response.length(); i++){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String string = response.getJSONObject(i).getString("Datetime");
            Date resultKey = df.parse(string);

            if(events.containsKey(resultKey)) {
                DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
                String stringTime = response.getJSONObject(i).getString("Datetime");
                //Date resultTime = df.parse(string);

                events.get(resultKey).add(stringTime);
                events.get(resultKey).add(String.valueOf(response.getJSONObject(i).getInt("EventDefinitionId")));

            }
            else{
                List<String> tmp = new ArrayList<>();

                DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
                String stringTime = response.getJSONObject(i).getString("Datetime");
                //Date resultTime = df.parse(string);

                tmp.add(stringTime);
                tmp.add(String.valueOf(response.getJSONObject(i).getInt("EventDefinitionId")));

                events.put( resultKey, tmp);
            }
            /*
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String string = response.getJSONObject(i).getString("Datetime");
            Date result = df.parse(string);
            events.put( result, response.getJSONObject(i).getInt("EventDefinitionId"));

             */
        }

        //creating views for showing data
        LinearLayout linearLayout = findViewById(R.id.rootLayout);
        //setContentView(linearLayout);
        //linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(100,0,0,0);
        //linearLayout.setLayoutParams(params);

        String currentPrintDate = "";
        TextView textViewWorkingPeriod =  new TextView(this);

        for (Map.Entry<Date, List<String>> e : events.descendingMap().entrySet()) {
            System.out.println(e);
            String thisDate = e.getKey().toString();

            if (!currentPrintDate.equals(thisDate)) {
                currentPrintDate = thisDate;
                TextView textViewDate = new TextView(this);
                textViewDate.setLayoutParams(params);
                textViewDate.setPadding(30, 80, 30, 5);
                textViewDate.setTextSize(18);
                textViewDate.setText(thisDate);
                linearLayout.addView(textViewDate);
            }

            if(e.getValue().get(1).equals("66")) {
                textViewWorkingPeriod = new TextView(this);
                textViewWorkingPeriod.setLayoutParams(params);
                textViewWorkingPeriod.setPadding(30, 5, 30, 5);
                textViewWorkingPeriod.setTextSize(18);
                textViewWorkingPeriod.setBackgroundResource(R.color.white);
                textViewWorkingPeriod.setText(e.getKey().toString().substring(11, 19));

            }
            else{
                textViewWorkingPeriod.append(" - " + e.getKey().toString().substring(11, 19));
                linearLayout.addView(textViewWorkingPeriod);
            }

        }
    }

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
        Context context = this;
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
                            getSpicaData(context);
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