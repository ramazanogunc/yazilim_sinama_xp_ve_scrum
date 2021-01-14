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
/*
Veritabanı singleton sınıfımız
Room database
 */
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
    // proje crud işlemleri için
    abstract val projectDao: ProjectDao
    // card crud işlemleri için
    abstract val cardDao: CardDao
    // user crud işlemleri için
    abstract val userDao: UserDao
    // iş takibi crud işlemleri
    abstract val cardStatusDao: CardStatusDao

    // static fonksiyon ve propertyler
    companion object {
        // veritabanı adı
        private const val DATABASE_NAME = "xp_and_scrum_db"

        // singleton property
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