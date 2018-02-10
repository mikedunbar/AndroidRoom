package com.mike.room.data

import com.mike.room.Author
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 *
 */
interface BooksRepository {
    fun getAllAuthors(): Single<List<Author>>
    fun saveAuthors(vararg authors: Author): Completable
    fun deleteAllAuthors(): Completable

    fun deleteAllBooks(): Completable
}