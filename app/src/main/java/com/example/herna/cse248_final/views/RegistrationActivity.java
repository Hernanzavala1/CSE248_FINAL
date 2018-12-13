package com.example.herna.cse248_final.views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.herna.cse248_final.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends Activity {
    private Button cancelButton;
    private Button signUpButton;
    private  EditText Name;
    private EditText email, password;

    private FirebaseAuth mAuth;
    private  FirebaseUser user;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        mAuth = FirebaseAuth.getInstance();

        cancelButton = (Button)findViewById(R.id.Cancel_Btn);
        signUpButton = (Button)findViewById(R.id.Sign_UpBtn);
        email = (EditText)findViewById(R.id.Email) ;
        password =(EditText)findViewById(R.id.Password) ;
        Name = (EditText)findViewById(R.id.userName2);


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
                             user = mAuth.getCurrentUser();
                            setUserInfo();

                            Toast.makeText(RegistrationActivity.this, "Authentication succesful",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this,HomePage.class );
                            startActivity(intent);

                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"You are already registered!",Toast.LENGTH_SHORT).show();
                            }


                        }

                    }


                });

    }

    private void setUserInfo() {
    String name = Name.getText().toString().trim();
    if(name != null){
        Uri uri = Uri.parse("android.resource://com.example.herna.cse248_final/drawable/crazy_supra.jpg");


        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).setPhotoUri(uri).build();
        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegistrationActivity.this, "User created!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }

    public void setUpCancelBtn(){
        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
