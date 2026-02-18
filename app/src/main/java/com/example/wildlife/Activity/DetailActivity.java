package com.example.wildlife.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import com.example.wildlife.R;
import com.google.android.material.appbar.MaterialToolbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //statusBar();

        ImageView image = findViewById(R.id.detailImage);
        TextView name = findViewById(R.id.detailName);

        String animalName = getIntent().getStringExtra("name");
        int animalImage = getIntent().getIntExtra("image", 0);

        name.setText(animalName);
        image.setImageResource(animalImage);


        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

//    private void statusBar(){
//        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
//
//        Window window = getWindow();
//
//        // Enable status bar background
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//        // Set status bar color
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_green));
//    }
}