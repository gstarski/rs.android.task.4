package com.example.storagetask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storagetask.container.AppContainer
import com.example.storagetask.data.Review
import com.example.storagetask.data.ReviewRepository
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {

    private val repository: ReviewRepository by lazy { AppContainer.reviewRepository!! }

    val reviews by lazy { repository.allReviews }

    fun update(review: Review) = viewModelScope.launch {
        repository.update(review)
    }

    fun add(review: Review) = viewModelScope.launch {
        repository.insert(review)
    }

    fun delete(review: Review) = viewModelScope.launch {
        repository.delete(review)
    }
}