package com.ramo.xpandscrum.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ramo.xpandscrum.database.dao.CardDao
import com.ramo.xpandscrum.database.dao.CardStatusDao
import com.ramo.xpandscrum.database.dao.ProjectDao
import com.ramo.xpandscrum.database.dao.UserDao
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.model.CardStatus
import com.ramo.xpandscrum.model.Project
import com.ramo.xpandscrum.model.User

@Database(
    entities = [
        Project::class,
        Card::class,
        CardStatus::class,
        User::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val projectDao: ProjectDao
    abstract val cardDao: CardDao
    abstract val userDao: UserDao
    abstract val cardStatusDao: CardStatusDao

    companion object {
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