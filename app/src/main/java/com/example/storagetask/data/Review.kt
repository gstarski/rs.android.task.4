package com.example.storagetask.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storagetask.data.common.DbConstants
import java.util.*

@Entity(tableName = DbConstants.ReviewsTable.NAME)
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = DbConstants.ReviewsTable.COL_TITLE) val title: String,
    @ColumnInfo(name = DbConstants.ReviewsTable.COL_RATING) val rating: Float,
    @ColumnInfo(name = DbConstants.ReviewsTable.COL_SUMMARY) val summary: String,
    @ColumnInfo(name = DbConstants.ReviewsTable.COL_CREATED_AT) val createdAt: Date
) {
    companion object {
        fun new(title: String, rating: Float, summary: String): Review {
            return Review(0, title, rating, summary, Date())
        }
    }
}
