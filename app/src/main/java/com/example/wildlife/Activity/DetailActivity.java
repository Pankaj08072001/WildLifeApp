package com.example.wildlife.Activity;

import android.os.Build;
import android.os.Bundle;
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

        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        getWindow().setStatusBarColor(
                ContextCompat.getColor(this, R.color.light_green)
        );

        setContentView(R.layout.activity_detail);

        ImageView image = findViewById(R.id.detailImage);
        TextView name = findViewById(R.id.detailName);

        String animalName = getIntent().getStringExtra("animal_name");
        int animalImage = getIntent().getIntExtra("animal_image", 0);

        name.setText(animalName);
        image.setImageResource(animalImage);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(animalName);
        }

        // Enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}