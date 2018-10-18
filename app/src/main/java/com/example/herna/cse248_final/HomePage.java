package com.example.herna.cse248_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {
   public TextView password;
   public ImageView weatherView;
   public NavigationView navigationView;

   private FirebaseAuth mAuth;
   private FirebaseUser user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        weatherView = findViewById(R.id.weatherView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);


        setHeaderInfo(user);




    }

    private void setHeaderInfo(FirebaseUser user) {

       View headerLayout = navigationView.getHeaderView(0);
       TextView email = headerLayout.findViewById(R.id.email_User);

       email.setText(user.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void ButtonOnClick(View v){

        Intent intent = new Intent(HomePage.this, Weather.class);
        startActivity(intent);

    }
}
