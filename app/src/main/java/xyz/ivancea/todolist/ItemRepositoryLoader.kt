package xyz.ivancea.todolist

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import xyz.ivancea.todolist.persistence.api.ItemRepository
import xyz.ivancea.todolist.persistence.notion.NotionConnectionData
import xyz.ivancea.todolist.persistence.notion.NotionItemRepository
import javax.inject.Inject

class ItemRepositoryLoader @Inject constructor(
	private val storage: TodoListStorage
) {
	fun getRepository(): ItemRepository? {
		val databaseType = storage.getPersistenceType()

		if (databaseType == NotionConnectionData.TYPE) {
			return LoggerItemRepository(
				NotionItemRepository(
					Json.decodeFromString(
						storage.getDatabaseData()
					)
				)
			)
		}

		return null
	}
}