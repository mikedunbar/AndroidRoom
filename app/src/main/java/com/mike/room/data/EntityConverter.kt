package com.mike.room.data

import com.mike.room.Author
import com.mike.room.Book

/**
 *
 */
class EntityConverter {

    companion object {
        fun convert(authorWithBooks: AuthorWithBooksRelation): Author {
            return Author(authorWithBooks.author.id, authorWithBooks.author.first_name, authorWithBooks.author.last_name,
                        authorWithBooks.books.map { Book(it.title) })
        }

        fun convert(author: Author) : AuthorWithBooksRelation {
            val authorEntity = AuthorEntity(author.id, author.firsName, author.lastName)
            val bookEntities = author.books.map {  BookEntity(it.title, author.id!!)}
            val relation = AuthorWithBooksRelation()
            relation.author = authorEntity
            relation.books = bookEntities
            return relation
        }
    }

}