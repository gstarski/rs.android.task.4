package com.example.storagetask

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

enum class SortingMethod { TITLE, RATING, CREATED }

enum class DbAccessMethod { ROOM, CURSORS }

class ExamplePreferences(private val sharedPrefs: SharedPreferences) {

    val sortingMethod = MutableStateFlow(sharedPrefs.getSortingMethod())
    val dbAccessMethod = MutableStateFlow(sharedPrefs.getDbAccessMethod())

    fun reportChange(key: String) {
        when (key) {
            "reviews_sort_options" -> sortingMethod.value = sharedPrefs.getSortingMethod()
            "db_access_method" -> dbAccessMethod.value = sharedPrefs.getDbAccessMethod()
        }
    }
}

private fun SharedPreferences.getSortingMethod(): SortingMethod {
    val value = this.getString("reviews_sort_options", "dateCreated")
    return when(value) {
        "title" -> SortingMethod.TITLE
        "rating" -> SortingMethod.RATING
        "dateCreated" -> SortingMethod.CREATED
        else -> {
            Log.e("TAG", "Unexpected sorting method $value")
            throw IllegalStateException()
        }
    }
}

private fun SharedPreferences.getDbAccessMethod(): DbAccessMethod {
    val value = this.getString("db_access_method", "room")
    return when(value) {
        "room" -> DbAccessMethod.ROOM
        "cursors" -> DbAccessMethod.CURSORS
        else -> {
            Log.e("TAG", "Unexpected sorting method $value")
            throw IllegalStateException()
        }
    }
}