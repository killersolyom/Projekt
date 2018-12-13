package ro.sapientia.ms.sapinews.javaActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.Advertisment;
import ro.sapientia.ms.sapinews.javaClasses.MyAdapter;

import java.util.ArrayList;

public class MyAdvertismentsActivity extends AppCompatActivity {

    private ArrayList<Advertisment> advertisments = new ArrayList<>();
    private RecyclerView recyclerView;

    public MyAdvertismentsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisments);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        for(int i = 0; i < 20; i++){
            advertisments.add(generateAdvertisment());
            advertisments.get(i).setAdvertismentTitle( advertisments.get(i).getAdvertismentTitle()+" " + i );
        }
        MyAdapter adapter = new MyAdapter(this.getApplicationContext(),advertisments,"MyAdvertisment");
        recyclerView.setAdapter(adapter);

    }

    public Advertisment generateAdvertisment(){
        Advertisment advertisments = new Advertisment();
        advertisments.setAdvertismentTitle("Teszt");
        advertisments.setAdvertismentDetails("Ez egy teszt");
        advertisments.setViewedCounter(66);
        advertisments.setAdvertismentImage("https://coubsecure-s.akamaihd.net/get/b153/p/coub/simple/cw_timeline_pic/dc084aa3631/dffafd7f8fc57eeaf2c71/ios_large_1482287026_image.jpg");
        advertisments.setAdvertismentProfilePicture("http://s3.amazonaws.com/rackcdn.kikvid.com/videos/44b4c338cf1c50ac85e814334039fd4d/thumbnails/thumb_1_1280x720.jpg");
        return advertisments;
    }

}
