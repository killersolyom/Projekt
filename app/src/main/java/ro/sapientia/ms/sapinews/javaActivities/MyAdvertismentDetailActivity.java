package ro.sapientia.ms.sapinews.javaActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.Advertisment;
import ro.sapientia.ms.sapinews.javaClasses.ImageContainer;
import ro.sapientia.ms.sapinews.javaClasses.OnSwipeTouchListener;
import ro.sapientia.ms.sapinews.javaClasses.User;

public class MyAdvertismentDetailActivity extends AppCompatActivity {

    private ImageView shareButton;
    private ImageView postPicture;
    private ImageView deleteButton;
    private ImageView editButton;
    private ImageView profilePicture;
    private EditText longDescription;
    private TextView postTitle;
    private TextView phoneNumber;
    private EditText shortDescription;
    private EditText location;
    private String key;
    private ImageContainer advertismentImage = new ImageContainer();
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisment_detail);
        shareButton = findViewById(R.id.shareButton);
        editButton = findViewById(R.id.editButton);
        postPicture = findViewById(R.id.postPicture);
        location = findViewById(R.id.locationText);
        deleteButton = findViewById(R.id.deleteButton);
        longDescription = findViewById(R.id.longDescription);
        shortDescription= findViewById(R.id.shortDescription);
        postTitle = findViewById(R.id.postTitle);
        phoneNumber = findViewById(R.id.phoneNumber);
        location.setEnabled(false);
        longDescription.setEnabled(false);
        shortDescription.setEnabled(false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("Title");
            String advertismentShortDescription = extras.getString("advertismentShortDescription");
            String advertismentLongDescription = extras.getString("advertismentLongDescription");
            String ownerPhoneNumber = extras.getString("ownerPhoneNumber");
            String locationS = extras.getString("location");
            advertismentImage.overrideImage(extras.getStringArrayList("advertismentImage"));
            String isDeleted = extras.getString("isDeleted");
            key = extras.getString("key");

            postTitle.setText(title);
            longDescription.setText(advertismentLongDescription);
            shortDescription.setText(advertismentShortDescription);
            phoneNumber.setText(ownerPhoneNumber);
            Glide.with(getApplicationContext()).load(advertismentImage.getCurrentImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(postPicture);
            location.setText(locationS);
        }

        postPicture.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = postTitle.getText().toString()+"\n\n";
                String details = longDescription.getText().toString()+"\n";
                String owner = User.getInstance().getLastName()+": ";
                String phone = phoneNumber.getText().toString()+"\n";
                String place = "Itt: "+location.getText().toString()+"\n\n";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = title+details+owner+phone+"\nLink: "+advertismentImage.getCurrentImage();
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Megosztás"));
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    location.setEnabled(true);
                    location.setFocusable(true);
                    location.setClickable(true);
                    location.setFocusableInTouchMode(true);

                    longDescription.setEnabled(true);
                    longDescription.setFocusable(true);
                    longDescription.setClickable(true);
                    longDescription.setFocusableInTouchMode(true);

                    shortDescription.setEnabled(true);
                    shortDescription.setFocusable(true);
                    shortDescription.setClickable(true);
                    shortDescription.setFocusableInTouchMode(true);
            }
        });
        editButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isValidContent()){
                    Toast.makeText(getApplicationContext(), "Szerkesztve", Toast.LENGTH_SHORT).show();
                    databaseReference.child("advertisments").child(key).setValue(new Advertisment(advertismentImage.getImages(),title,shortDescription.getText().toString(),longDescription.getText().toString(), User.getInstance().getImageUrl(),0, User.getInstance().getPhoneNumb(),location.getText().toString(),"false", key));
                }else {
                    Toast.makeText(getApplicationContext(), "Helytelenül kitöltött mezők!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("advertisments").child(key).child("isDeleted").setValue("true");
                Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isValidContent(){
        if(isEmpty() && isValidDataInserted()){
            return true;
        }
        return false;
    }

    public boolean isValidDataInserted(){
        if(location.getText().toString().matches("[^*/\\}[{}?%^#@$!`'\"~])(=;><]*?")){
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        if(!shortDescription.getText().toString().isEmpty() &&
                !longDescription.getText().toString().isEmpty() &&
                !location.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }
}
