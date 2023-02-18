package xyz.ivancea.todolist.dtos

import java.io.Serializable

data class DatabaseItem(
    val id: String,
    val name: String
) : Serializable
