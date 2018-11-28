package com.example.killersolyom.projekt;




import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainScreenActivity extends AppCompatActivity implements AddAdvertismentFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,HomeFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    Bundle bundle = new Bundle();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment advFragment = new HomeFragment();
                    FragmentTransaction advTransaction = getSupportFragmentManager().beginTransaction();
                    advTransaction.replace(R.id.container, advFragment);
                    advTransaction.commit();
                    return true;
                case R.id.navigation_account:
                    ProfileFragment accFragment = new ProfileFragment();
                    FragmentTransaction accTransaction = getSupportFragmentManager().beginTransaction();
                    accFragment.setArguments(bundle);
                    accTransaction.replace(R.id.container, accFragment);
                    accTransaction.commit();
                    return true;
                case R.id.navigation_add:
                    AddAdvertismentFragment addFragment = new AddAdvertismentFragment();
                    FragmentTransaction addTransaction = getSupportFragmentManager().beginTransaction();
                    addTransaction.replace(R.id.container, addFragment);
                    addTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);

        Intent i = getIntent();
        String phoneNumberNumber = i.getStringExtra("phoneNumber");
        bundle.putString("phone",phoneNumberNumber);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);
        HomeFragment advFragment = new HomeFragment();
        FragmentTransaction advTransaction = getSupportFragmentManager().beginTransaction();
        advTransaction.replace(R.id.container, advFragment);
        advTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
/*
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }*/
}
