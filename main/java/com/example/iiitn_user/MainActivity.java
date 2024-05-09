package com.example.iiitn_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private DrawerLayout drawerlayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.frame_layout);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        drawerlayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerlayout, R.string.start, R.string.close);

        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);




    }
//    Button otherButton;
//    protected void onCreate1(){
//
//        View view = LayoutInflater.from(this).inflate(R.layout.mobile_navigation, null);
//
//        otherButton = view.findViewById(R.id.navigation_notice);
//
//        otherButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Code to execute when button is clicked
//            }
//        });





    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
//        switch (item.getItemId()){
//            case R.id.navigation_developer:
//                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
//                break;
//        }
        if(id==R.id.navigation_developer){
            Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.navigation_ebook){
            String url = "https://elibrary.iiitn.ac.in/"; // Replace this with your desired URL
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);

            Toast.makeText(this, "Ebooks", Toast.LENGTH_SHORT).show();

        }
        else if(id==R.id.navigation_themes){
            Toast.makeText(this, "Themes", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.navigation_website){
            String url = "https://iiitn.ac.in/"; // Replace this with your desired URL
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            Toast.makeText(this, "Website", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.navigation_share){
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }
        return true;
    }




}
    