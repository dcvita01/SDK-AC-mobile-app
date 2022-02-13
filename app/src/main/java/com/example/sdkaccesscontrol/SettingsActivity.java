package com.example.sdkaccesscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceFragmentCompat;

import com.example.sdkaccesscontrol.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
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
                        intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_working:
                        intent = new Intent(SettingsActivity.this, WorkingFromHome.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_hours:
                        intent = new Intent(SettingsActivity.this, WorkingHours.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_profile:
                        intent = new Intent(SettingsActivity.this, UserProfile.class);
                        startActivity(intent);
                        break;
                    case  R.id.nav_settings:
                        onOptionsItemSelected(menuItem);
                        break;
                    case  R.id.nav_logout:
                        intent = new Intent(SettingsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

}