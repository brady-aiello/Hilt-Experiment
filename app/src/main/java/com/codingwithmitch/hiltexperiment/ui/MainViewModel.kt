package com.codingwithmitch.hiltexperiment.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.codingwithmitch.hiltexperiment.model.Blog
import com.codingwithmitch.hiltexperiment.repository.MainRepository
import com.codingwithmitch.hiltexperiment.ui.MainStateEvent.GetBlogEvents
import com.codingwithmitch.hiltexperiment.ui.MainStateEvent.None
import com.codingwithmitch.hiltexperiment.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    @ExperimentalCoroutinesApi
    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is GetBlogEvents -> {
                    mainRepository.getBlogs()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is None -> {
                    // who cares
                }
            }
        }



    }
}

sealed class MainStateEvent {
    object GetBlogEvents: MainStateEvent()
    object None: MainStateEvent()
}