package com.mike.room.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getAll(): List<BookEntity>

    @Query("SELECT * FROM books WHERE title = :title")
    fun findByTitle(title: String): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg books: BookEntity)

    @Update
    fun updateAll(vararg books: BookEntity)

    @Delete
    fun delete(book: BookEntity)

    @Query("DELETE FROM books")
    fun deleteAll()
}