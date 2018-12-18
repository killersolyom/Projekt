package ro.sapientia.ms.sapinews.javaActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.OnSwipeTouchListener;

public class AdvertismentDetailActivity extends AppCompatActivity {

    private ImageView shareButton;
    private ImageView postPicture;
    private ImageView reportButton;
    private TextView longDescription;
    private TextView postTitle;
    private TextView shortDescription;
    private TextView location;
    private TextView phoneNumber;
    private ImageView profilePicture;
    private ImageView getProfilePicture;
    private String TAG = "TAG_AdvertismentDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisment_detail);
        shareButton = findViewById(R.id.shareButton);
        postPicture = findViewById(R.id.postPicture);
        reportButton = findViewById(R.id.reportButton);
        longDescription = findViewById(R.id.longDescription);
        shortDescription= findViewById(R.id.shortDescription);
        profilePicture = findViewById(R.id.profilePicture);
        postTitle = findViewById(R.id.postTitle);
        phoneNumber = findViewById(R.id.phoneNumber);
        location = findViewById(R.id.locationText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("Title");
            String advertismentShortDescription = extras.getString("advertismentShortDescription");
            String advertismentLongDescription = extras.getString("advertismentLongDescription");
            String advertismentProfilePicture = extras.getString("advertismentProfilePicture");
            String ownerPhoneNumber = extras.getString("ownerPhoneNumber");
            String locationS = extras.getString("location");
            ArrayList<String> advertismentImage = extras.getStringArrayList("advertismentImage");

            postTitle.setText(title);
            longDescription.setText(advertismentLongDescription);
            shortDescription.setText(advertismentShortDescription);
            Glide.with(getApplicationContext()).load(advertismentProfilePicture).diskCacheStrategy(DiskCacheStrategy.ALL).into(profilePicture);
            phoneNumber.setText(ownerPhoneNumber);
            Glide.with(getApplicationContext()).load(advertismentImage.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).into(postPicture);
            location.setText(locationS);

            Log.d(TAG,"Title: " + title);
            Log.d(TAG,"advertismentShortDescription: " + advertismentShortDescription);
            Log.d(TAG,"advertismentLongDescription: " + advertismentLongDescription);
            Log.d(TAG,"advertismentProfilePicture: " + advertismentProfilePicture);
            Log.d(TAG,"ownerPhoneNumber: " + ownerPhoneNumber);
            Log.d(TAG,"advertismentImage: " + advertismentImage.toString());
        }


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Eladó bucugli\n\n";
                String details = "Jó állapotban van és nem loptt!!! Vedd meg!\n";
                String owner = "Jancsi: ";
                String phone = "0786141697\n\n";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = title+details+owner+phone;
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Megosztás"));
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdvertismentDetailActivity.this);
                builder.setTitle("Jelentés indoka: ");
                final EditText input = new EditText(AdvertismentDetailActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Jelent", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String reportText = input.getText().toString();
                       if(reportText.length() > 10){

                       }else {
                           Toast.makeText(AdvertismentDetailActivity.this,"Bővebben írja körül a problémát!",Toast.LENGTH_SHORT).show();
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

    }

}
