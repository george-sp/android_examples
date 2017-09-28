package com.spyridakis.carousel_layout_manager_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setLogoDescription(getResources().getString(R.string.logo_description));

        List<CountryItem> countryItems = generateCountryItems();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        carouselLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setLayoutManager(carouselLayoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, countryItems);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private List<CountryItem> generateCountryItems() {
        List<CountryItem> countryItems = new ArrayList<>();

        countryItems.add(new CountryItem("United States", R.drawable.newyork));
        countryItems.add(new CountryItem("Canada", R.drawable.canada));
        countryItems.add(new CountryItem("United Kingdom", R.drawable.uk));
        countryItems.add(new CountryItem("Germany", R.drawable.germany));
        countryItems.add(new CountryItem("Sweden", R.drawable.sweden));

        return countryItems;
    }
}
