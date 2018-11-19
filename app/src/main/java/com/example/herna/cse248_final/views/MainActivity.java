package com.example.herna.cse248_final.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.herna.cse248_final.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends Activity {

    private Button registerButton;
    private Button logInBtn;

    public EditText Email;
    public EditText password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this is the firebase service
        mAuth = FirebaseAuth.getInstance();

        registerButton = (Button) (findViewById(R.id.registerButton));
        logInBtn = (Button) findViewById(R.id.LogInBtn);
        password = (EditText) findViewById(R.id.Password);
        Email = (EditText) findViewById(R.id.Email);


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
        String email = getEmail().getText().toString().trim();
        String password = getPassword().getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email or password cannot be Empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Authentication succesful!",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, HomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }


                    }
                });
    }


    public void setUpregisterButton() {
        Intent intent = new Intent(this, RegistrationActivity.class);

        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    public EditText getEmail() {
        return Email;
    }

    public void setEmail(EditText email) {
        Email = email;
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }
}
