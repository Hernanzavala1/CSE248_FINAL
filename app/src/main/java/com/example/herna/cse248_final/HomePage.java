package com.example.herna.cse248_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends Activity {
   public TextView password;
   public ImageView weatherView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        password = findViewById(R.id.userInfo);
        weatherView = findViewById(R.id.weatherView);

        weatherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText("weather icon ");
            }
        });





    }
}
