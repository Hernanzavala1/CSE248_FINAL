package com.example.herna.cse248_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class HomePage extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    public TextView password;
   public ImageView weatherView;
   public NavigationView navigationView;

   private ImageView profileImage;
   private Uri uri;


   private View headerLayout;


    private FirebaseAuth mAuth;
   private FirebaseUser user;
   private  FirebaseStorage storage;
    private StorageReference mStorageRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();

        //Hooking up the xml fields with java
        weatherView = findViewById(R.id.weatherView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        headerLayout = navigationView.getHeaderView(0);
        profileImage = (ImageView)headerLayout.findViewById(R.id.userProfilePic);

        setSupportActionBar(toolbar);
        setHeaderInfo(user);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfilePicture();
            }
        });


    }

    private void setProfilePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Picture"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
        uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profileImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setHeaderInfo(FirebaseUser user) {
       TextView email = headerLayout.findViewById(R.id.email_User);
       TextView name = headerLayout.findViewById(R.id.name_User);
       email.setText(user.getEmail());
        System.out.println(user.getDisplayName());
       name.setText(user.getDisplayName());
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
