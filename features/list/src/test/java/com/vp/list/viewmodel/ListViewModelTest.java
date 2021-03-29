package com.vp.list.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.vp.list.model.ListItem;
import com.vp.list.model.SearchResponse;
import com.vp.list.service.SearchService;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.mock.Calls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskRule = new InstantTaskExecutorRule();

    @Test
    public void shouldReturnErrorState() {
        //given
        SearchService searchService = mock(SearchService.class);
        when(searchService.search(anyString(), anyInt())).thenReturn(Calls.failure(new IOException()));
        ListViewModel listViewModel = new ListViewModel(searchService);

        //when
        listViewModel.searchMoviesByTitle("title", 1);

        //then
        assertThat(listViewModel.observeMovies().getValue().getListState()).isEqualTo(ListState.ERROR);
    }

    @Test
    public void shouldReturnInProgressState() {
        //given
        SearchService searchService = mock(SearchService.class);
        when(searchService.search(anyString(), anyInt())).thenReturn(Calls.response(mock(SearchResponse.class)));
        ListViewModel listViewModel = new ListViewModel(searchService);
        Observer<SearchResult> mockObserver = (Observer<SearchResult>) mock(Observer.class);
        listViewModel.observeMovies().observeForever(mockObserver);

        //when
        listViewModel.searchMoviesByTitle("title", 1);

        //then
        verify(mockObserver).onChanged(SearchResult.inProgress());
    }

    @Test
    public void shouldReturnLoadedState() {
        //given
        SearchService searchService = mock(SearchService.class);
        SearchResponse searchResponse = mock(SearchResponse.class);

        ArrayList<ListItem> listOfItems = new ArrayList<>();
        listOfItems.add(mock(ListItem.class));
        when(searchResponse.getSearch()).thenReturn(listOfItems);

        when(searchService.search(anyString(), anyInt())).thenReturn(Calls.response(searchResponse));
        ListViewModel listViewModel = new ListViewModel(searchService);

        //when
        listViewModel.searchMoviesByTitle("title", 1);

        //then
        assertThat(listViewModel.observeMovies().getValue().getListState()).isEqualTo(ListState.LOADED);
    }

    @Test
    public void shouldReturnErrorState_whenNoItems() {
        //given
        SearchService searchService = mock(SearchService.class);
        SearchResponse searchResponse = mock(SearchResponse.class);
        when(searchResponse.getSearch()).thenReturn(new ArrayList<>());
        when(searchService.search(anyString(), anyInt())).thenReturn(Calls.response(searchResponse));
        ListViewModel listViewModel = new ListViewModel(searchService);

        //when
        listViewModel.searchMoviesByTitle("title", 1);

        //then
        assertThat(listViewModel.observeMovies().getValue().getListState()).isEqualTo(ListState.ERROR);
    }

}