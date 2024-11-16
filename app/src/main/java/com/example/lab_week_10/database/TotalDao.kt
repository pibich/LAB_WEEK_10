package com.example.lab_week_10.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao

interface TotalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(total: Total)

    @Update
    fun update(total: Total)

    @Delete
    fun delete(total: Total)

    @Query("SELECT * FROM total WHERE id = :id")
    fun getTotal(id: Long): List<Total>
}