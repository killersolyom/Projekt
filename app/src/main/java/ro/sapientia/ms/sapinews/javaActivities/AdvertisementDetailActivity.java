package ro.sapientia.ms.sapinews.javaActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.ImageContainer;
import ro.sapientia.ms.sapinews.javaClasses.OnSwipeTouchListener;

public class AdvertisementDetailActivity extends AppCompatActivity {

    private ImageView shareButton;
    private ImageView postPicture;
    private ImageView reportButton;
    private TextView longDescription;
    private TextView postTitle;
    private TextView shortDescription;
    private TextView location;
    private TextView phoneNumber;
    private ImageView profilePicture;
    private TextView creator;
    private ImageContainer advertismentImage = new ImageContainer();
    private String TAG = "TAG_AdvertismentDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_detail);
        shareButton = findViewById(R.id.shareButton);
        postPicture = findViewById(R.id.postPicture);
        reportButton = findViewById(R.id.reportButton);
        longDescription = findViewById(R.id.longDescription);
        shortDescription= findViewById(R.id.shortDescription);
        profilePicture = findViewById(R.id.profilePicture);
        postTitle = findViewById(R.id.postTitle);
        phoneNumber = findViewById(R.id.phoneNumber);
        location = findViewById(R.id.locationText);
        creator = findViewById(R.id.creator);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("Title");
            String advertismentShortDescription = extras.getString("advertismentShortDescription");
            String advertismentLongDescription = extras.getString("advertismentLongDescription");
            String advertismentProfilePicture = extras.getString("advertismentProfilePicture");
            String ownerPhoneNumber = extras.getString("ownerPhoneNumber");
            String locationS = extras.getString("location");
            advertismentImage.overrideImage(extras.getStringArrayList("advertismentImage"));


            postTitle.setText(title);
            longDescription.setText(advertismentLongDescription);
            shortDescription.setText(advertismentShortDescription);
            Glide.with(getApplicationContext()).load(advertismentProfilePicture).diskCacheStrategy(DiskCacheStrategy.ALL).into(profilePicture);
            phoneNumber.setText(ownerPhoneNumber);
            Glide.with(getApplicationContext()).load(advertismentImage.getCurrentImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(postPicture);
            location.setText(locationS);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference users = database.getReference();

            assert ownerPhoneNumber != null;
            users.child("users").child(ownerPhoneNumber).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot value : dataSnapshot.getChildren()){
                            if(Objects.equals(value.getKey(), "lastName")) {
                                creator.setText(value.getValue().toString());
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });
        }


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = postTitle.getText().toString()+"\n\n";
                String details = longDescription.getText().toString()+"\n";
                String owner = creator.getText().toString()+": ";
                String phone = phoneNumber.getText().toString()+"\n";
                String place = "Itt: "+location.getText().toString()+"\n\n";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = title+details+owner+phone+"\nLink: "+advertismentImage.getCurrentImage();

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Megosztás"));
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdvertisementDetailActivity.this);
                builder.setTitle("Jelentés indoka: ");
                final EditText input = new EditText(AdvertisementDetailActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Jelent", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String reportText = input.getText().toString();
                       if(reportText.length() > 10){

                       }else {
                           Toast.makeText(AdvertisementDetailActivity.this,"Bővebben írja körül a problémát!",Toast.LENGTH_SHORT).show();
                       }
                    }
                });
                builder.setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        postPicture.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeRight() {

                Glide.with(getApplicationContext()).load(advertismentImage.getNextImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(postPicture);
            }
            public void onSwipeLeft() {

                Glide.with(getApplicationContext()).load(advertismentImage.getPreviousImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(postPicture);
            }
        });

    }

}
