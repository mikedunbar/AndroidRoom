package com.mike.room.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

/**
 *
 */
class AuthorWithBooksRelation {
    @Embedded lateinit var author: AuthorEntity

    @Relation(parentColumn = "id", entityColumn = "author_id")
    lateinit var books: List<BookEntity>
}