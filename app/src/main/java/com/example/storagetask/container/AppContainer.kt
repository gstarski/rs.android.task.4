package com.example.storagetask.container

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.storagetask.ExamplePreferences
import com.example.storagetask.data.ReviewRepository
import com.example.storagetask.data.room.ExampleRoomDatabase

object AppContainer {
    var reviewRepository: ReviewRepository? = null
        private set

    var preferences: ExamplePreferences? = null
        private set

    fun initialize(context: Context) {
        val db = ExampleRoomDatabase.getDatabase(context)
        reviewRepository = ReviewRepository(db.reviewDao())

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
        preferences = ExamplePreferences(sharedPrefs)
    }
}