package com.mike.room.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "authors",
        indices = [(Index(value = ["first_name", "last_name"], unique = true))])
data class AuthorEntity (@PrimaryKey(autoGenerate = true) val id: Int?, val first_name: String, val last_name: String)