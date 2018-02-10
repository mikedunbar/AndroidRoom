package com.mike.room.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import io.reactivex.Single

@Dao
interface AuthorDao {

    @Transaction
    @Query("SELECT * FROM authors")
    fun getAll() :Single<List<AuthorWithBooksRelation>>

    @Transaction
    @Query("SELECT * FROM authors WHERE first_name = :firstName AND last_name = :lastName")
    fun findByName(firstName: String, lastName: String) : Single<AuthorWithBooksRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg authors: AuthorEntity)

    @Delete
    fun delete(author: AuthorEntity)

    @Query("DELETE FROM authors")
    fun deleteAll()
}