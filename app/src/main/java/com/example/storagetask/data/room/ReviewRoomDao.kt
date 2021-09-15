package com.example.storagetask.data.room

import androidx.room.*
import com.example.storagetask.data.Review
import com.example.storagetask.data.common.DbConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewRoomDao {
    @Query("SELECT * FROM ${DbConstants.ReviewsTable.NAME}")
    fun getAllReviews(): Flow<List<Review>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: Review)

    @Update
    suspend fun update(review: Review)

    @Delete
    suspend fun delete(review: Review)

    @Query("DELETE FROM ${DbConstants.ReviewsTable.NAME}")
    suspend fun deleteAll()
}