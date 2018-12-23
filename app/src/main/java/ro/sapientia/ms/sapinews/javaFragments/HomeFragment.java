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
                advertisments.clear();
                if (dataSnapshot.exists()){
                    Log.d(TAG,"KULCS: "+dataSnapshot.getKey());
                    for(DataSnapshot value : dataSnapshot.getChildren()) {
                        //if(Objects.equals(value.getKey(), "advertisments")){
                            Advertisment adv = value.getValue(Advertisment.class);
                            Log.d(TAG,"tartalma: " + adv.toString());
                            if(adv.getIsDeleted().equals("false")){
                                advertisments.add(adv);
                            }
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
