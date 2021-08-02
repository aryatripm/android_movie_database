package com.arya.latihan2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;


import android.app.SearchManager;

import android.content.Context;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


import com.arya.latihan2.adapter.ViewPagerAdapter;
import com.arya.latihan2.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(null);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.share, R.string.load_more);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this::onOptionsItemSelected);


        binding.tabLayout.setupWithViewPager(binding.tabView);
        viewPagerPopular(binding.tabView);
    }

    private void viewPagerPopular(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(MovieFragment.newInstance(), "Movie");
        adapter.addFragment(TvFragment.newInstance(), "Series");
        viewPager.setAdapter(adapter);
    }

    private void viewPagerTopRated(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(MovieTopRatedFragment.newInstance(), "Movie");
        adapter.addFragment(TvFragment.newInstance(), "Series");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setSubmitButtonEnabled(true);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popular:
                break;
            case R.id.menu_topRated:
                break;
            case R.id.menu_newest:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}