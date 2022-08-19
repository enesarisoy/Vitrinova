package com.ns.vitrinova.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ns.vitrinova.data.model.DiscoverModel
import com.ns.vitrinova.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val postContent: MutableLiveData<DiscoverModel> by lazy {
        MutableLiveData<DiscoverModel>()
    }

    init {
        getContent()
    }

    fun getContent() {
        viewModelScope.launch {
            val response = repository.getContent()

            response.data?.let {
                postContent.postValue(response.data)
            }
        }
    }
}