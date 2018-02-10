package com.mike.room.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(AuthorEntity::class), (BookEntity::class)], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAuthorDao(): AuthorDao
    abstract fun getBookDao(): BookDao
}