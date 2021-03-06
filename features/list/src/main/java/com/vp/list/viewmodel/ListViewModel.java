package com.vp.list.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import com.vp.commonaddons.model.ListItem;
import com.vp.list.model.SearchResponse;
import com.vp.list.service.SearchService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends ViewModel {
    private MutableLiveData<SearchResult> liveData = new MutableLiveData<>();
    private SearchService searchService;

    private String currentTitle = "";
    private List<ListItem> aggregatedItems = new ArrayList<>();
    private Call<SearchResponse> currentCall;

    @Inject
    ListViewModel(@NonNull SearchService searchService) {
        this.searchService = searchService;
    }

    public LiveData<SearchResult> observeMovies() {
        return liveData;
    }

    public void searchMoviesByTitle(@NonNull String title, int page) {

        if (page == 1 && !title.equals(currentTitle)) {
            aggregatedItems.clear();
            currentTitle = title;
            liveData.setValue(SearchResult.inProgress());
        }
        final Call<SearchResponse> currentCall = searchService.search(title, page);
        currentCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                SearchResponse result = response.body();
                int totalResult = result != null ? result.getTotalResults() : Integer.MAX_VALUE;

                if (result != null) {
                    aggregatedItems.addAll(result.getSearch());
                }

                if (aggregatedItems.isEmpty()) {
                    liveData.setValue(SearchResult.error());
                } else {
                    liveData.setValue(SearchResult.success(aggregatedItems, totalResult));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                liveData.setValue(SearchResult.error());
            }
        });
        this.currentCall = currentCall;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        final Call<SearchResponse> currentCall = this.currentCall;
        if(currentCall != null) {
            currentCall.cancel();
        }
    }
}
