package ro.sapientia.ms.sapinews.javaActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
    private ImageView profilePicture;
    private ImageView getProfilePicture;

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

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://www.revell.de/out/pictures/master/product/1/07777_smpw_trabant_601_limousine.jpg";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Trabi");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Megosztás"));
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
