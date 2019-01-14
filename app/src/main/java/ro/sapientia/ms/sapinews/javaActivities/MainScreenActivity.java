package ro.sapientia.ms.sapinews.javaActivities;

import ro.sapientia.ms.sapinews.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import ro.sapientia.ms.sapinews.javaFragments.AddAdvertisementFragment;
import ro.sapientia.ms.sapinews.javaFragments.HomeFragment;
import ro.sapientia.ms.sapinews.javaFragments.ProfileFragment;
import ro.sapientia.ms.sapinews.javaClasses.User;

public class MainScreenActivity extends AppCompatActivity implements AddAdvertisementFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,HomeFragment.OnFragmentInteractionListener {

    //private TextView mTextMessage;
    private User user = User.getInstance();
    Bundle bundle = new Bundle();
    private boolean doubleBackToExitPressedOnce = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment advFragment = new HomeFragment();
                    FragmentTransaction advTransaction = getSupportFragmentManager().beginTransaction();
                    advTransaction.replace(R.id.fragment_container, advFragment);
                    advTransaction.commit();
                    return true;
                case R.id.navigation_account:
                    ProfileFragment accFragment = new ProfileFragment();
                    FragmentTransaction accTransaction = getSupportFragmentManager().beginTransaction();
                    accFragment.setArguments(bundle);
                    accTransaction.replace(R.id.fragment_container, accFragment);
                    accTransaction.commit();
                    return true;
                case R.id.navigation_add:
                    AddAdvertisementFragment addFragment = new AddAdvertisementFragment();
                    FragmentTransaction addTransaction = getSupportFragmentManager().beginTransaction();
                    addTransaction.replace(R.id.fragment_container, addFragment);
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
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);
        HomeFragment advFragment = new HomeFragment();
        FragmentTransaction advTransaction = getSupportFragmentManager().beginTransaction();
        advTransaction.replace(R.id.fragment_container, advFragment);
        advTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Kilépéshez nyomja meg mégegyszer a vissza gombot!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
