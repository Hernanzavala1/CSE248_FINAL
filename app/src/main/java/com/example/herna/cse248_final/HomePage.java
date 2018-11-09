package com.example.herna.cse248_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.BiPredicate;

public class HomePage extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    private static final int LOAD_IMAGE = 102;

    public TextView password;
   public ImageView weatherView;
   private ImageView newsView;
   private  ImageView eventsView;
   public NavigationView navigationView;

   private ImageView profileImage;
   private String profileImageURL;
   private Uri uri;


   private View headerLayout;


    private FirebaseAuth mAuth;
   private FirebaseUser user;


    //  private  FirebaseStorage storage;
    //private StorageReference mStorageRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


      //  mStorageRef = storage.getReference();

        //Hooking up the xml fields with java
        weatherView = findViewById(R.id.weatherView);
        newsView = findViewById(R.id.newsIcon);
        eventsView = findViewById(R.id.eventIcon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        headerLayout = navigationView.getHeaderView(0);
        profileImage = (ImageView)headerLayout.findViewById(R.id.userProfilePic);

        setSupportActionBar(toolbar);



            setHeaderInfo(user);



        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfilePictureUpdate();
            }
        });



    }

    private void setProfilePictureUpdate() {
        // this creates the intent to open all of the gallery pics
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

         String path = "profilepics/"+System.currentTimeMillis()+
                 ".jpg";
    StorageReference storage = FirebaseStorage.getInstance().getReference(path);


         UploadTask uploadTask = storage.putFile(uri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storage.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        profileImageURL = downloadUri.toString();
                        Toast.makeText(HomePage.this, "download url got it!", Toast.LENGTH_SHORT).show();
                        UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(profileImageURL))
                                .build();
                        user.updateProfile(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Picasso.get().load(user.getPhotoUrl()).into(profileImage);
                                Toast.makeText(HomePage.this, "picture has been updated!", Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });







        }
    }

    /**
     *
     * @param user current user
     *             method loads all of the user information such as name, email address and profile picture
     */

    private void setHeaderInfo( FirebaseUser user)  {

        TextView email = headerLayout.findViewById(R.id.email_User);
        TextView name = headerLayout.findViewById(R.id.name_User);
        email.setText(user.getEmail());
        System.out.println(user.getDisplayName());
        name.setText(user.getDisplayName());
        //System.out.println("USER PHOTO URI"+ user.getPhotoUrl().toString());
        if(user.getPhotoUrl() != null) {
            Picasso.get().load(user.getPhotoUrl()).into(profileImage);
        }
//        Bitmap bitmap;
//        try{
//            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(user.getPhotoUrl()));
//            profileImage.setImageBitmap(bitmap);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // It is here where the image does not load and nothing appears for the profile image. I print the uri above and
        // it does contain a image uri the problem is loading
        //this is what prints out USER PHOTO URI content://com.android.providers.media.documents/document/image%3A208843 so i know it is saving it to user
       // Picasso.get().load(user.getPhotoUrl()).into(profileImage);

        // tried to do the below but it requires Manage_Document permission which is only allow for special apps, not to sure what it meant.
      //  Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(getContentResolver(), user.getPhotoUrl());
       //profileImage.setImageBitmap(bitmap);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void goToNewsPage(View v){
        Intent intent = new Intent(HomePage.this, newsActivity.class);
        startActivity(intent);
    }

    public void goToWeatherPage(View v){

        Intent intent = new Intent(HomePage.this, Weather.class);
        startActivity(intent);

    }
    public void goToEventsPage(View view){
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }
}
