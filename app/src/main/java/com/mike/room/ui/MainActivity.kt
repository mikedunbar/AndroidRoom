package com.mike.room.ui

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.mike.room.Author
import com.mike.room.Book
import com.mike.room.R
import com.mike.room.data.AppDatabase
import com.mike.room.data.BooksRepository
import com.mike.room.data.DefaultBooksRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val TAG = "MAIN"
    private lateinit var textView: TextView
    private lateinit var authors: List<Author>
    private lateinit var booksRepository: BooksRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.message)

        val db  = Room.databaseBuilder < AppDatabase >(applicationContext, AppDatabase::class.java, "room-sample-db")
                .fallbackToDestructiveMigration()
                .build()
        booksRepository = DefaultBooksRepository(db.getAuthorDao(), db.getBookDao())

        val currentDBPath = getDatabasePath("room-sample-db").absolutePath
        Log.i(TAG, "db: $currentDBPath")

        deleteData()
    }

    private fun displayAuthors() {
        var message = "Authors: \n"
        authors.forEach {
            message += "${it.firsName} ${it.lastName}\n"
            if (it.books.isNotEmpty()) {
                message += "\t Books:\n\t\t"
                it.books.forEach {
                    message += "${it.title} \n\t\t"
                }
            }
            textView.text = message
        }
    }

    private fun displayError(error: String) {
        textView.text = error
    }

    private fun deleteData() {
        booksRepository.deleteAllAuthors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.i(TAG, "delete authors completion")
                            createData()
                        },
                        {
                            val error = "delete authors error:  $it"
                            Log.e(TAG, error)
                            displayError(error)
                        })
    }

    private fun createData() {
        Log.i(TAG, "About to create data")
        booksRepository.saveAuthors(
                    Author(1, "Roy", "Parks", listOf(Book("Politics Today"), Book("Draining the Swamp"))),
                    Author(2, "Janet", "Dupree", listOf(Book("Rock Legends"), Book("Birth of the Beatlers"))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.i(TAG, "create authors completion")
                            getAuthorData()
                        },
                        {
                            val error = "create authors error:  $it"
                            Log.e(TAG, error)
                            displayError(error)
                        }
                )
    }

    private fun getAuthorData() {
            Log.i(TAG, "About to fetch data")
            textView.text = "Fetching data"
            booksRepository.getAllAuthors()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                Log.i(TAG, "onNext called with $it")
                                authors = it
                                displayAuthors()
                            },
                            {
                                val error = "onErrorCalled:  $it"
                                Log.e(TAG, error)
                                displayError(error)
                            })
    }
}
