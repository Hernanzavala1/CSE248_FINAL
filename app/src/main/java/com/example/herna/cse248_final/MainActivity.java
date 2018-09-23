package com.example.herna.cse248_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button registerButton;
    private Button logInBtn;

    public EditText UserName;
    public EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButton = (Button)(findViewById(R.id.registerButton));
        logInBtn = (Button)findViewById(R.id.LogInBtn);
        password = (EditText)findViewById(R.id.Password);
        UserName = (EditText)findViewById(R.id.UserName);


        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLogInBtn();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpregisterButton();
            }
        });
    }

    private void setLogInBtn() {
        if(password.getText().toString().equals("Hello!")){
            Intent intent = new Intent(MainActivity.this,HomePage.class);
            intent.putExtra("password",password.getText().toString());
            intent.putExtra("username", UserName.getText().toString());
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this, "Incorrect password or username!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void setUpregisterButton(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }
}
