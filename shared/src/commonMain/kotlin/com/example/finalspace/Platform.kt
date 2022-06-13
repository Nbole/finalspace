package com.example.finalspace

import com.squareup.sqldelight.db.SqlDriver

expect class Platform() {
    val platform: String
}

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}