package com.vp.favorites;

import android.content.res.Configuration;
import android.os.Bundle;

import com.vp.commonaddons.ListAdapter;
import com.vp.commonaddons.SharedPreferencesManager;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.AndroidInjection;

public class FavoriteActivity extends AppCompatActivity implements ListAdapter.OnItemClickListener {

    @Inject
    FavoriteNavigator favoriteNavigator;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    private ListAdapter listAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = findViewById(R.id.recyclerView);

        initList();
    }

    private void initList() {
        listAdapter = new ListAdapter();
        listAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3);
        recyclerView.setLayoutManager(layoutManager);
        listAdapter.setItems(sharedPreferencesManager.getFavorites());
    }

    @Override
    public void onItemClick(String imdbID) {
        favoriteNavigator.showDetails(this, imdbID);
    }
}
