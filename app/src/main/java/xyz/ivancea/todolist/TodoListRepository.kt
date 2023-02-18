package xyz.ivancea.todolist

import notion.api.v1.NotionClient
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.databases.query.filter.condition.CheckboxFilter
import notion.api.v1.model.pages.PageProperty
import xyz.ivancea.todolist.dtos.DatabaseItem
import javax.inject.Inject

class TodoListRepository @Inject constructor(private val storage: TodoListStorage) {
    suspend fun getItems(): List<DatabaseItem> {
        val token = storage.getToken()
        val databaseData = storage.getDatabaseData()

        return NotionClient(token).use { client ->
            val results = client.queryDatabase(
                databaseId = databaseData.databaseId,
                filter = PropertyFilter(
                    property = databaseData.booleanColumnId,
                    checkbox = CheckboxFilter(
                        equals = true
                    )
                )
            )

            results.results.map { page ->
                val titleProperty =
                    page.properties.values.first { it.id == databaseData.nameColumnId }

                DatabaseItem(
                    id = page.id,
                    name = titleProperty.title?.first()?.plainText ?: ""
                )
            }
        }
    }

    suspend fun markAsDone(item: DatabaseItem): Unit {
        val token = storage.getToken()
        val databaseData = storage.getDatabaseData()

        return NotionClient(token).use { client ->
            client.updatePage(
                pageId = item.id,
                properties = mapOf(
                    databaseData.booleanColumnId to PageProperty(checkbox = false)
                )
            )
        }
    }
}