package com.reihanalavi.jetpackdogs.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dog::class], version = 1)
abstract class DogDatabase: RoomDatabase() {

    abstract fun dogDao(): DogDao

    companion object {
        @Volatile private var instace: DogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instace ?: synchronized(LOCK) {
            instace ?: buildDatabase(context).also {
                instace = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dogdatabase"
        ).build()
    }

}