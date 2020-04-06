package com.reihanalavi.jetpackdogs.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {

    @Insert
    suspend fun insertDogs(vararg dog: Dog): List<Long>

    @Query ("SELECT * from dog")
    suspend fun getDogs(): List<Dog>

    @Query ("SELECT * from dog WHERE uid = :uid")
    suspend fun getDog(uid: Int): Dog

    @Query ("DELETE from dog")
    suspend fun deleteDogs()

    @Query ("DELETE from dog WHERE uid = :uid")
    suspend fun deleteDog(uid: Int)
}