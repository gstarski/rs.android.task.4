package com.example.storagetask.data

import com.example.storagetask.data.room.ReviewRoomDao
import kotlinx.coroutines.flow.Flow

class ReviewRepository(private val reviewDao: ReviewRoomDao) {

    // TODO: add DAO for bare cursors; switch DAO

    val allReviews: Flow<List<Review>> = reviewDao.getAllReviews()

    suspend fun insert(review: Review) {
        reviewDao.insert(review)
    }

    suspend fun update(review: Review) {
        reviewDao.update(review)
    }

    suspend fun delete(review: Review) {
        reviewDao.delete(review)
    }
}