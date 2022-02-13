
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

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }
}
*/
package com.example.sdkaccesscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.sdkaccesscontrol.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;


public class Settings extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
                        intent = new Intent(Settings.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_working:
                        intent = new Intent(Settings.this, WorkingFromHome.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_hours:
                        intent = new Intent(Settings.this, WorkingHours.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_profile:
                        intent = new Intent(Settings.this, UserProfile.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_settings:
                        onOptionsItemSelected(menuItem);
                        break;
                    case  R.id.nav_logout:
                        intent = new Intent(Settings.this, LoginActivity.class);
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


    //public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public void sendMessage(View view) {
        // Do something in response to button
        System.out.println("Request: not implemented...");
        /*
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        */
    }





}