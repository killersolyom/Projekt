package ro.sapientia.ms.sapinews.javaActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.Advertisment;
import ro.sapientia.ms.sapinews.javaClasses.MyAdapter;
import ro.sapientia.ms.sapinews.javaClasses.User;

import java.util.ArrayList;

public class MyAdvertismentsActivity extends AppCompatActivity {

    private ArrayList<Advertisment> advertisments = new ArrayList<>();
    private RecyclerView recyclerView;
    private String TAG = "TAG_MYADVERTISMENTSACTIVITY";
    private MyAdapter adapter;
    public MyAdvertismentsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisments);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ads = database.getReference();

        for(int i = 0; i < User.getInstance().getAdKeys().size(); i++){
            String key = User.getInstance().getAdKeys().get(i);
            Log.d(TAG,"key: " + key);
            ads.child("advertisments").child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        //Log.d(TAG,"KULCS: "+dataSnapshot.getKey());
                        //for(DataSnapshot value : dataSnapshot.getChildren()) {
                            //if(Objects.equals(value.getKey(), "advertisments")){
                            Log.d(TAG,"tartalma: " + dataSnapshot.getValue());
                            Advertisment adv = dataSnapshot.getValue(Advertisment.class);
                            // Log.d(TAG,"tartalma: " + adv.toString());
                            advertisments.add(adv);

                            // }
                        //}
                    }
                    else {
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "dataSnapshot is not extist.");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    adapter.notifyDataSetChanged();
                }
            });
        }

        /*for(int i = 0; i < 20; i++){
            advertisments.add(generateAdvertisment());
            advertisments.get(i).setAdvertismentTitle( advertisments.get(i).getAdvertismentTitle()+" " + i );
        }*/


        MyAdapter adapter = new MyAdapter(this.getApplicationContext(),advertisments,"MyAdvertisment");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
