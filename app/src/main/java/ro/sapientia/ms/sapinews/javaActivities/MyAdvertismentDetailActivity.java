package ro.sapientia.ms.sapinews.javaActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.OnSwipeTouchListener;

public class MyAdvertismentDetailActivity extends AppCompatActivity {

    private ImageView postPicture;
    private ImageView edit;
    private EditText longDescription;
    private EditText shortDescription;
    private EditText location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisment_detail);
        postPicture = findViewById(R.id.postPicture);
        longDescription = findViewById(R.id.longDescription);
        shortDescription = findViewById(R.id.shortDescription);
        location = findViewById(R.id.locationText);
        edit = findViewById(R.id.editButton);
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
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidContent()){

                }
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
