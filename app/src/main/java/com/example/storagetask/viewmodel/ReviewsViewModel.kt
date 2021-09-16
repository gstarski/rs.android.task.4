package com.example.storagetask.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storagetask.ExamplePreferences
import com.example.storagetask.SortingMethod
import com.example.storagetask.container.AppContainer
import com.example.storagetask.data.Review
import com.example.storagetask.data.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {

    private val preferences: ExamplePreferences by lazy { AppContainer.preferences!! }

    private val repository: ReviewRepository by lazy { AppContainer.reviewRepository!! }

    val reviews by lazy { repository.allReviews }

    val title = MutableStateFlow("Reviewsss")

    // TODO: move sorting logic to the repository
    val sortedReviews = MutableStateFlow<List<Review>>(emptyList())
    private var comparator: Comparator<in Review> = compareBy { it.id }

    init {
        reviews
            .onEach { sortedReviews.value = it.sortedWith(comparator) }
            .launchIn(viewModelScope)

        preferences.sortingMethod
            .onEach { sorting ->
                Log.d("TAG", "Got new sorting: $sorting")
                comparators[sorting]?.also { newComparator ->
                    comparator = newComparator
                    updateTitle(sorting)
                    sortedReviews.value = sortedReviews.value.sortedWith(comparator)
                }
            }
            .launchIn(viewModelScope)

        preferences.dbAccessMethod
            .onEach { Log.d("TAG", "Got new db access method: $it") }
            .launchIn(viewModelScope)
    }

    fun update(review: Review) = viewModelScope.launch {
        repository.update(review)
    }

    fun add(review: Review) = viewModelScope.launch {
        repository.insert(review)
    }

    fun delete(review: Review) = viewModelScope.launch {
        repository.delete(review)
    }

    private fun updateTitle(sorting: SortingMethod) {
        title.value = "Reviews ${sortingNames[sorting] ?: ""}"
    }

    companion object {
        private val comparators = mapOf<SortingMethod, Comparator<in Review>>(
            SortingMethod.TITLE to compareBy { it.title.lowercase() },
            SortingMethod.RATING to compareByDescending { it.rating },
            SortingMethod.CREATED to compareByDescending { it.createdAt }
        )

        private val sortingNames = mapOf(
            SortingMethod.TITLE to "by title",
            SortingMethod.RATING to "by rating",
            SortingMethod.CREATED to "by date"
        )
    }
}