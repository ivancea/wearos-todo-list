package xyz.ivancea.todolist.dtos

import java.io.Serializable

data class DatabaseData(
    val databaseId: String,
    val nameColumnId: String,
    val booleanColumnId: String
) : Serializable
