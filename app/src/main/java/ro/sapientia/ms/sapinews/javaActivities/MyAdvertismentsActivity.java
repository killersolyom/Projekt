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
        advertisments.setAdvertismentShortDescription("Ez egy teszt");
        advertisments.setViewedCounter(66);
        ArrayList<String> temp = new ArrayList<>();
        temp.add("https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg");
        advertisments.setAdvertismentImage(temp);
        advertisments.setAdvertismentProfilePicture("https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg");
        return advertisments;
    }

}
