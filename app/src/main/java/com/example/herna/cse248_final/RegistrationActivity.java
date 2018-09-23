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
        signUpButton = (Button)findViewById(R.id.Sign_UpBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpCancelBtn();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpSignBtn();
            }
        });


    }

    private void setUpSignBtn() {
        //it would be here where all of the information will be saved to database;
        Intent intent = new Intent(this,HomePage.class );
        startActivity(intent);
    }

    public void setUpCancelBtn(){
        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class );
        startActivity(intent);
    }
}
