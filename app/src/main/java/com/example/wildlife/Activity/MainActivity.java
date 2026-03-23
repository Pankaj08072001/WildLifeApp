package com.example.wildlife.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlife.Adapter.AnimalAdapter;
import com.example.wildlife.ApiInterface;
import com.example.wildlife.Fragment.FavoritesFragment;
import com.example.wildlife.Fragment.ProfileFragment;
import com.example.wildlife.Model.Animal;
import com.example.wildlife.MyData;
import com.example.wildlife.R;
import com.example.wildlife.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    RecyclerView recyclerView;
    AnimalAdapter adapter;
    List<Animal> animalList;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        initViews();
        // Step 3: Setup UI
        setupRecyclerView();
        setUpToolbar();
        setupBottomNavigation();
        setupDrawer();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiService = retrofit.create(ApiInterface.class);

        Call<MyData> call = apiService.getProductData();

        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(@NonNull Call<MyData> call, @NonNull Response<MyData> response) {
                if(response.isSuccessful()){
                    MyData data = response.body();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyData> call, @NonNull Throwable t) {

            }
        });

    }


    private void initViews() {
        bottomNav = findViewById(R.id.bottomNavigation);
        recyclerView = findViewById(R.id.recyclerAnimals);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
    }


    // RecyclerView setup
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        animalList = new ArrayList<>();
        animalList.add(new Animal("Lion", R.drawable.lions));
        animalList.add(new Animal("Tiger", R.drawable.tigers));
        animalList.add(new Animal("Elephant", R.drawable.elephant));
        animalList.add(new Animal("Zebra", R.drawable.zebra));

        adapter = new AnimalAdapter(this, animalList);
        recyclerView.setAdapter(adapter);
    }

    // Toolbar setup with tree icon
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Wildlife");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_green));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black));

        // Set tree icon instead of back arrow
        toolbar.setNavigationIcon(R.drawable.ic_tree);
        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setTint(ContextCompat.getColor(this, android.R.color.black));
        }

        toolbar.setNavigationOnClickListener(v -> Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show());
    }

    // Bottom navigation setup
    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {

                recyclerView.setVisibility(View.VISIBLE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Wildlife");
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new Fragment())
                        .commit();

                return true;

            } else if (item.getItemId() == R.id.nav_favorites) {

                recyclerView.setVisibility(View.GONE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Favorites");
                }

                loadFragment(new FavoritesFragment());
                return true;

            } else if (item.getItemId() == R.id.nav_profile) {

                recyclerView.setVisibility(View.GONE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Profile");
                }

                loadFragment(new ProfileFragment());
                return true;
            }

            return false;
        });
    }

    // Load fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    // Toolbar menu (search icon)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Search Clicked ", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Drawer setup
    private void setupDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.drawer_home) {
                Toast.makeText(this, "Drawer Home", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.drawer_profile) {
                Toast.makeText(this, "Drawer Profile", Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }
}