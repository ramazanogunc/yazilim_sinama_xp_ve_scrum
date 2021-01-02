package com.ramo.xpandscrum.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ramo.xpandscrum.database.dao.ProjectDao
import com.ramo.xpandscrum.model.Project

@Database(
    entities = [
        Project::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(BoardTypeConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract val projectDao: ProjectDao

    companion object{
        private const val DATABASE_NAME = "xp_and_scrum_db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }

    }
}