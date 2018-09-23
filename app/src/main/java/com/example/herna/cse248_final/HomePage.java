package com.example.herna.cse248_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

public class HomePage extends Activity {
   public TextView password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Intent intent = getIntent();

        password= (TextView) findViewById(R.id.userInfo);
        String password2 =intent.getStringExtra("password");
        String name = intent.getStringExtra("username");
        password.setText(name +"\n"+ password2);



    }
}
