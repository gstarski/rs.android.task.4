package com.example.storagetask.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.storagetask.data.Review
import com.example.storagetask.data.common.DbConstants
import com.example.storagetask.utils.generateRandomReviews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Review::class], version = 1)
@TypeConverters(Converters::class)
abstract class ExampleRoomDatabase : RoomDatabase() {

    abstract fun reviewDao(): ReviewRoomDao

    companion object {
        @Volatile
        private var INSTANCE: ExampleRoomDatabase? = null

        fun getDatabase(context: Context): ExampleRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExampleRoomDatabase::class.java,
                    DbConstants.Database.NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(Callback())
                    .build()
                Log.d("TAG", "Create db instance")
                INSTANCE = instance
                instance
            }
        }

        private class Callback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                Log.d("TAG", "Seed database")

                INSTANCE?.let { db ->
                    GlobalScope.launch(Dispatchers.IO) {
                        populateDatabase(db.reviewDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(reviewDao: ReviewRoomDao) {
            reviewDao.deleteAll()
            generateRandomReviews().take(4).forEach {
                reviewDao.insert(it)
            }
        }
    }
}