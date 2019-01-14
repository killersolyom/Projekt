package ro.sapientia.ms.sapinews.javaActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaClasses.Advertisement;
import ro.sapientia.ms.sapinews.javaClasses.MyAdapter;
import ro.sapientia.ms.sapinews.javaClasses.User;

import java.util.ArrayList;

public class MyAdvertisementsActivity extends AppCompatActivity {

    private ArrayList<Advertisement> advertisments = new ArrayList<>();
    private RecyclerView recyclerView;
    private String TAG = "TAG_MYADVERTISMENTSACTIVITY";
    private MyAdapter adapter;
    public MyAdvertisementsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisements);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ads = database.getReference();

        for(int i = 0; i < User.getInstance().getAdKeys().size(); i++){
            final String key = User.getInstance().getAdKeys().get(i);
            Log.d(TAG,"key: " + key);
            ads.child("advertisments").child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        //Log.d(TAG,"KULCS: "+dataSnapshot.getKey());
                        //for(DataSnapshot value : dataSnapshot.getChildren()) {
                            //if(Objects.equals(value.getKey(), "advertisments")){
                            Log.d(TAG,"tartalma: " + dataSnapshot.getValue());
                            Advertisement adv = dataSnapshot.getValue(Advertisement.class);
                            // Log.d(TAG,"tartalma: " + adv.toString());
                            adv.setAdvertismentProfilePicture(User.getInstance().getImageUrl());
                            if(adv.getIsDeleted().equals("false")){
                                ads.child("advertisments").child(key).setValue(adv);
                                advertisments.add(adv);
                            }

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

    public Advertisement generateAdvertisment(){
        Advertisement advertisments = new Advertisement();
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
