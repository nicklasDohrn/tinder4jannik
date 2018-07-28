package com.example.d064278.myapplication;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    String DEBUG_TAG = "debug";

    final R.raw drawableResources = new R.raw();
    final Class<R.raw> c = R.raw.class;
    final Field[] fields = c.getDeclaredFields();
    List<Integer> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        for (int i = 0, max = fields.length; i < max; i++) {
            final int resourceId;
            try {
                resourceId = fields[i].getInt(drawableResources);
            } catch (Exception e) {
                continue;
            }
            images.add(resourceId);
        }
        Log.i(DEBUG_TAG, "images found: " + images.size());
        view = findViewById(R.id.mainImage);

    }

    public float oldX;
    public float difference;
    int position = 10000000;
    ImageView view;

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                oldX = event.getX();
                return true;
            case (MotionEvent.ACTION_UP) :
                difference = oldX - event.getX();
                if(difference < -10) {
                    view.setImageResource(images.get(++position%5));
                } else if(difference > 10) {
                    view.setImageResource(images.get(--position%5));
                }
                Log.i(DEBUG_TAG, (difference < 0) ? "right": "left");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    public void backToMain(View v) {

        setContentView(R.layout.activity_main_screen);
        view = findViewById(R.id.mainImage);
        view.setImageResource(images.get(position%5));
    }

    public void toInfo(View v) {
        setContentView(R.layout.activity_info);
        view = findViewById(R.id.small_image);
        view.setImageResource(images.get(position%5));
    }
}
