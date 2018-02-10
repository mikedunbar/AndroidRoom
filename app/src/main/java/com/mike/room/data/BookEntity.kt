package com.mike.room.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "books",
        indices = [(Index("author_id"))],
        foreignKeys =
            [(ForeignKey(
                entity = AuthorEntity::class,
                parentColumns = ["id"],
                childColumns = ["author_id"],
                onDelete = ForeignKey.CASCADE))])
data class BookEntity(@PrimaryKey val title: String, val author_id: Int)