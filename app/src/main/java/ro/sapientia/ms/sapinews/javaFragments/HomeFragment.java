package ro.sapientia.ms.sapinews.javaFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ro.sapientia.ms.sapinews.R;
import ro.sapientia.ms.sapinews.javaActivities.LoginActivity;
import ro.sapientia.ms.sapinews.javaActivities.SignUpActivity;
import ro.sapientia.ms.sapinews.javaClasses.Advertisment;
import ro.sapientia.ms.sapinews.javaClasses.MyAdapter;
import ro.sapientia.ms.sapinews.javaClasses.User;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private Context context = null;
    private OnFragmentInteractionListener mListener;
    private User user = User.getInstance();
    private ArrayList<Advertisment> advertisments = new ArrayList<>();
    private RecyclerView recycle;
    private MyAdapter adapter;
    private String TAG = "TAG_HOMEFRAGMENT";

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ads = database.getReference();

        ads.child("advertisments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Log.d(TAG,"KULCS: "+dataSnapshot.getKey());
                    for(DataSnapshot value : dataSnapshot.getChildren()) {
                        //if(Objects.equals(value.getKey(), "advertisments")){
                            Advertisment adv = value.getValue(Advertisment.class);
                            Log.d(TAG,"tartalma: " + adv.toString());
                            advertisments.add(adv);
                            adapter.notifyDataSetChanged();
                       // }
                    }
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

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recycle = view.findViewById(R.id.recycler_view);
        /*for(int i = 0; i < 20; i++){
            advertisments.add(generateAdvertisment());
            advertisments.get(i).setAdvertismentTitle( advertisments.get(i).getAdvertismentTitle()+" " + i );
        }*/
        context = this.getContext();
        adapter = new MyAdapter(this.getContext(),advertisments,"GlobalAdvertisment");
        recycle.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recycle.setAdapter(adapter);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public Advertisment generateAdvertisment(){
        Advertisment advertisments = new Advertisment();
        advertisments.setAdvertismentTitle("Teszt");
        //advertisments.setAdvertismentDetails("Ez egy teszt");
        advertisments.setAdvertismentShortDescription("Ez egy teszt");
        advertisments.setViewedCounter(66);
        //advertisments.setAdvertismentImage("https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg");
        advertisments.setAdvertismentProfilePicture("https://coubsecure-s.akamaihd.net/get/b153/p/coub/simple/cw_timeline_pic/dc084aa3631/dffafd7f8fc57eeaf2c71/ios_large_1482287026_image.jpg");
        return advertisments;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.notifyDataSetChanged();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
