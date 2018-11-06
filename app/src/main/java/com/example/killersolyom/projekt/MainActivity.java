package com.example.killersolyom.projekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    ImageView full_logo;
    ImageView transparent_logo;
    Animation fadein;
    Animation fadeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread animation = new Thread(){
            @Override
            public void run() {
                full_logo = findViewById(R.id.logo_View2);
                transparent_logo = findViewById(R.id.logo_View);

                fadein = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadein);
                fadeout = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadeout);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            full_logo.startAnimation(fadein);
                        }
                    });

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                }

        };
        animation.start();
    }


}
