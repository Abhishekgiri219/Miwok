package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // viewpager and adapter
        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.view_pager);
        CustomFragmentStateAdapter customFragmentStateAdapter = new CustomFragmentStateAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(customFragmentStateAdapter);

        // caching the number of pages
        viewPager.setOffscreenPageLimit(3);

        String[] categoryName = {"Numbers", "Colors", "Family", "Phrases"};

        TabLayout tabLayout = findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(categoryName[position]))).attach();
    }
}