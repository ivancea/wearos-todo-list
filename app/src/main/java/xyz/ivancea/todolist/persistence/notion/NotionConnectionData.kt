package xyz.ivancea.todolist.persistence.notion

import kotlinx.serialization.Serializable

@Serializable
data class NotionConnectionData(
	val integrationToken: String,
	val databaseId: String,
	val nameColumnId: String,
	val booleanColumnId: String
) {
	companion object {
		const val TYPE = "notion"
	}
}
