package com.example.storagetask.container

import android.content.Context
import com.example.storagetask.data.ReviewRepository
import com.example.storagetask.data.room.ExampleRoomDatabase

object AppContainer {
    var reviewRepository: ReviewRepository? = null

    fun initialize(context: Context) {
        val db = ExampleRoomDatabase.getDatabase(context)
        reviewRepository = ReviewRepository(db.reviewDao())
    }
}