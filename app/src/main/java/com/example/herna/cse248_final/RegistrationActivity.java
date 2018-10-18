package com.example.herna.cse248_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class RegistrationActivity extends Activity {
    private Button cancelButton;
    private Button signUpButton;
    private EditText email, password;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        mAuth = FirebaseAuth.getInstance();

        cancelButton = (Button)findViewById(R.id.Cancel_Btn);
        signUpButton = (Button)findViewById(R.id.Sign_UpBtn);
        email = (EditText)findViewById(R.id.Email) ;
        password =(EditText)findViewById(R.id.Password) ;


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
        String email = getEmail().getText().toString().trim();
        String password = getPassword().getText().toString().trim();
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(RegistrationActivity.this, "Email or Password cannot be empty!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegistrationActivity.this, "Authentication succesful",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this,HomePage.class );
                            startActivity(intent);
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }


                });

    }

    public void setUpCancelBtn(){
        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class );
        startActivity(intent);
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }
}
