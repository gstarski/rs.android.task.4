package com.example.storagetask.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storagetask.data.Review
import com.example.storagetask.utils.generateRandomReviews
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    private var nextId = 0L

    init {
        Log.d("TAG", "Init vm: $this")

        viewModelScope.launch {
            delay(2000)
            _reviews.value = generateRandomReviews().take(10).toList()
            nextId = _reviews.value.last().id + 1
            Log.d("TAG", "list after init: ${_reviews.value.size} elements")
        }
    }

    fun save(review: Review) = viewModelScope.launch {
        val isNew = review.id == -1L

        val list = if (isNew) {
            _reviews.value.plus(review.copy(id = nextId++))
        } else {
            _reviews.value.map {
                if (it.id == review.id) review else it
            }
        }

        _reviews.value = list
    }

    fun delete(review: Review) = viewModelScope.launch {
        _reviews.value = _reviews.value.filter { it.id != review.id }
    }
}