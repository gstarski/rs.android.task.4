package com.example.storagetask.data

import java.util.*

data class Review(
    val id: Long,
    val title: String,
    val rating: Float,
    val summary: String,
    val createdAt: Date
) {
    companion object {
        fun new(title: String, rating: Float, summary: String): Review {
            val millisSinceEpoch = Date().time / 1000
            return Review(-1, title, rating, summary, Date(millisSinceEpoch))
        }
    }
}
