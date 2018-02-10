package com.mike.room.data

import android.util.Log
import com.mike.room.Author
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 *
 */
class DefaultBooksRepository(val authorDao: AuthorDao, val bookDao: BookDao) : BooksRepository {
    private val TAG = "DefaultBooksRep"

    override fun getAllAuthors(): Single<List<Author>> {
        return authorDao.getAll()
                .flatMap {
                    Observable.fromIterable(it)
                            .map {
                                Log.i(TAG, "converting $it")
                                EntityConverter.convert(it) }
                            .toList()
                }
    }

    override fun saveAuthors(vararg authors: Author) : Completable {
        return Completable.fromAction({
            authors.map { EntityConverter.convert(it) }
                    .forEach {
                        authorDao.insertAll(it.author)
                        bookDao.insertAll(*it.books.toTypedArray())
                    }
        })
    }

    override fun deleteAllAuthors(): Completable {
        return Completable.fromAction{authorDao.deleteAll()}
    }

    override fun deleteAllBooks(): Completable {
        return Completable.fromAction{bookDao.deleteAll()}
    }


}