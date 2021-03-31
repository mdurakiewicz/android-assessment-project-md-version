package com.vp.favorites;

import android.content.res.Configuration;
import android.os.Bundle;

import com.vp.favorites.model.ListItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.AndroidInjection;

public class FavoriteActivity extends AppCompatActivity implements ListAdapter.OnItemClickListener {

    @Inject
    FavoriteNavigator favoriteNavigator;
    private ListAdapter listAdapter;
    private RecyclerView recyclerView;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        recyclerView = findViewById(R.id.recyclerView);

        initList();
    }

    private void initList() {
        listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3);
        recyclerView.setLayoutManager(layoutManager);
        setItemsData(listAdapter, sharedPreferencesManager.getFavorites());
    }


    private void setItemsData(@NonNull ListAdapter listAdapter, @NonNull List<String> posters) {
        List<ListItem> listItems = new ArrayList<>();
        for(int i = 0; i < posters.size(); i++){
            ListItem listItem = new ListItem();
            listItem.setPoster(posters.get(i));
            listItems.add(listItem);
        }
        listAdapter.setItems(listItems);
    }

    @Override
    public void onItemClick(String imdbID) {

    }
}
