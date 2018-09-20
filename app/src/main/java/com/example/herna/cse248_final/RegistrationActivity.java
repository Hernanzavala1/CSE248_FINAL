package com.example.herna.cse248_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationActivity extends Activity {
    private Button cancelButton;
    private Button signUpButton;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        cancelButton = (Button)findViewById(R.id.Cancel_Btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this,MainActivity.class );
                startActivity(intent);

            }
        });
    }
}
