package com.example.killersolyom.projekt;

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
        full_logo = findViewById(R.id.logo_View2);
        transparent_logo = findViewById(R.id.logo_View);

        fadein = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadeout);

        full_logo.startAnimation(fadein);
        full_logo.clearAnimation();
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        full_logo.startAnimation(fadeout);
        full_logo.clearAnimation();

        Thread animation = new Thread(){

            @Override
            public void run() {


                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                transparent_logo.startAnimation(fadein);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                full_logo.startAnimation(fadein);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                transparent_logo.startAnimation(fadein);
                /*
                for(int i = 0; i<9 ; i++){
                    full_logo.startAnimation(fadeout);
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    full_logo.startAnimation(fadein);
                }
                */
            }
        };
        //animation.start();
    }


}
