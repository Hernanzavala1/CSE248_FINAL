package com.example.herna.cse248_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButton = (Button)(findViewById(R.id.registerButton));
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpregisterButton();
            }
        });
    }
    public void setUpregisterButton(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
